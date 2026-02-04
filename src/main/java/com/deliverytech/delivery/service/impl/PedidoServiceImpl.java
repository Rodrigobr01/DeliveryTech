package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.ItemPedidoDTO;
import com.deliverytech.delivery.dto.ItemPedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.ItemPedido;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.entity.StatusPedido;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.PedidoService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private static final Map<StatusPedido, Set<StatusPedido>> STATUS_TRANSITIONS = buildStatusTransitions();

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoServiceImpl(
        PedidoRepository pedidoRepository,
        ClienteRepository clienteRepository,
        RestauranteRepository restauranteRepository,
        ProdutoRepository produtoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    @Transactional
    public PedidoResponseDTO criarPedido(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado."));
        if (!cliente.getAtivo()) {
            throw new BusinessException("Cliente inativo.");
        }

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));
        if (!restaurante.getAtivo()) {
            throw new BusinessException("Restaurante inativo.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setEnderecoEntrega(dto.enderecoEntrega());
        pedido.setStatus(StatusPedido.CRIADO);
        pedido.setTaxaEntrega(restaurante.getTaxaEntrega());

        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto nao encontrado."));

            if (!produto.getDisponivel()) {
                throw new BusinessException("Produto indisponivel.");
            }
            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new BusinessException("Produto nao pertence ao restaurante informado.");
            }

            BigDecimal itemSubtotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(itemDto.quantidade()));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(itemSubtotal);
            pedido.addItem(item);

            subtotal = subtotal.add(itemSubtotal);
        }

        BigDecimal total = subtotal.add(restaurante.getTaxaEntrega());
        pedido.setTotal(total);

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponse(salvo);
    }

    @Override
    @Transactional
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pedido nao encontrado."));
        pedido.getItens().size();
        return toResponse(pedido);
    }

    @Override
    public List<PedidoResumoDTO> buscarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
            .stream()
            .map(this::toResumo)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pedido nao encontrado."));

        if (!isTransitionAllowed(pedido.getStatus(), status)) {
            throw new BusinessException("Transicao de status nao permitida.");
        }

        pedido.setStatus(status);
        Pedido salvo = pedidoRepository.save(pedido);
        return toResponse(salvo);
    }

    @Override
    public BigDecimal calcularTotalPedido(Long restauranteId, List<ItemPedidoDTO> itens) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));

        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemPedidoDTO itemDto : itens) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto nao encontrado."));

            if (!produto.getDisponivel()) {
                throw new BusinessException("Produto indisponivel.");
            }
            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new BusinessException("Produto nao pertence ao restaurante informado.");
            }

            BigDecimal itemSubtotal = produto.getPreco()
                .multiply(BigDecimal.valueOf(itemDto.quantidade()));
            subtotal = subtotal.add(itemSubtotal);
        }

        return subtotal.add(restaurante.getTaxaEntrega());
    }

    @Override
    @Transactional
    public PedidoResponseDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pedido nao encontrado."));

        if (!canCancel(pedido.getStatus())) {
            throw new BusinessException("Nao e possivel cancelar o pedido neste status.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        Pedido salvo = pedidoRepository.save(pedido);
        return toResponse(salvo);
    }

    private PedidoResponseDTO toResponse(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = pedido.getItens()
            .stream()
            .map(item -> new ItemPedidoResponseDTO(
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
            ))
            .collect(Collectors.toList());

        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente().getId(),
            pedido.getRestaurante().getId(),
            pedido.getEnderecoEntrega(),
            pedido.getStatus(),
            pedido.getDataPedido(),
            pedido.getTaxaEntrega(),
            pedido.getTotal(),
            itens
        );
    }

    private PedidoResumoDTO toResumo(Pedido pedido) {
        return new PedidoResumoDTO(
            pedido.getId(),
            pedido.getStatus(),
            pedido.getDataPedido(),
            pedido.getTotal()
        );
    }

    private static Map<StatusPedido, Set<StatusPedido>> buildStatusTransitions() {
        Map<StatusPedido, Set<StatusPedido>> transitions = new EnumMap<>(StatusPedido.class);
        transitions.put(StatusPedido.CRIADO, Set.of(StatusPedido.CONFIRMADO, StatusPedido.CANCELADO));
        transitions.put(StatusPedido.CONFIRMADO, Set.of(StatusPedido.PREPARANDO, StatusPedido.CANCELADO));
        transitions.put(StatusPedido.PREPARANDO, Set.of(StatusPedido.ENVIADO, StatusPedido.CANCELADO));
        transitions.put(StatusPedido.ENVIADO, Set.of(StatusPedido.ENTREGUE));
        transitions.put(StatusPedido.ENTREGUE, Set.of());
        transitions.put(StatusPedido.CANCELADO, Set.of());
        return transitions;
    }

    private boolean isTransitionAllowed(StatusPedido from, StatusPedido to) {
        return STATUS_TRANSITIONS.getOrDefault(from, Set.of()).contains(to);
    }

    private boolean canCancel(StatusPedido status) {
        return status == StatusPedido.CRIADO
            || status == StatusPedido.CONFIRMADO
            || status == StatusPedido.PREPARANDO;
    }
}
