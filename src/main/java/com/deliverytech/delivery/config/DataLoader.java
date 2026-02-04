package com.deliverytech.delivery.config;

import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.entity.ItemPedido;
import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.entity.StatusPedido;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public DataLoader(
        ClienteRepository clienteRepository,
        RestauranteRepository restauranteRepository,
        ProdutoRepository produtoRepository,
        PedidoRepository pedidoRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() > 0) {
            return;
        }

        Cliente cliente1 = buildCliente("Joao Silva", "joao@email.com", "11999990001", "Rua A, 123");
        Cliente cliente2 = buildCliente("Maria Souza", "maria@email.com", "11999990002", "Rua B, 456");
        Cliente cliente3 = buildCliente("Carlos Lima", "carlos@email.com", "11999990003", "Rua C, 789");
        clienteRepository.saveAll(List.of(cliente1, cliente2, cliente3));

        Restaurante rest1 = buildRestaurante("Pizza Express", "Pizza", "1133330001", new BigDecimal("5.00"), 40);
        Restaurante rest2 = buildRestaurante("Sushi Place", "Japonesa", "1133330002", new BigDecimal("7.00"), 50);
        restauranteRepository.saveAll(List.of(rest1, rest2));

        Produto p1 = buildProduto("Pizza Margherita", "Pizza", new BigDecimal("45.00"), "Pizza classica", rest1);
        Produto p2 = buildProduto("Pizza Calabresa", "Pizza", new BigDecimal("49.90"), "Pizza com calabresa", rest1);
        Produto p3 = buildProduto("Refrigerante Lata", "Bebida", new BigDecimal("6.50"), "350ml gelado", rest1);
        Produto p4 = buildProduto("Sushi Combo", "Japonesa", new BigDecimal("79.90"), "Combo 20 pecas", rest2);
        Produto p5 = buildProduto("Temaki", "Japonesa", new BigDecimal("29.90"), "Temaki salmao", rest2);
        produtoRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        Pedido pedido1 = new Pedido();
        pedido1.setCliente(cliente1);
        pedido1.setRestaurante(rest1);
        pedido1.setEnderecoEntrega("Rua A, 123");
        pedido1.setStatus(StatusPedido.CONFIRMADO);
        pedido1.setTaxaEntrega(rest1.getTaxaEntrega());

        ItemPedido item1 = buildItem(p1, 1);
        ItemPedido item2 = buildItem(p3, 2);
        pedido1.addItem(item1);
        pedido1.addItem(item2);
        pedido1.setTotal(item1.getSubtotal()
            .add(item2.getSubtotal())
            .add(rest1.getTaxaEntrega()));

        Pedido pedido2 = new Pedido();
        pedido2.setCliente(cliente2);
        pedido2.setRestaurante(rest2);
        pedido2.setEnderecoEntrega("Rua B, 456");
        pedido2.setStatus(StatusPedido.PREPARANDO);
        pedido2.setTaxaEntrega(rest2.getTaxaEntrega());

        ItemPedido item3 = buildItem(p4, 1);
        ItemPedido item4 = buildItem(p5, 1);
        pedido2.addItem(item3);
        pedido2.addItem(item4);
        pedido2.setTotal(item3.getSubtotal()
            .add(item4.getSubtotal())
            .add(rest2.getTaxaEntrega()));

        pedidoRepository.saveAll(List.of(pedido1, pedido2));
    }

    private Cliente buildCliente(String nome, String email, String telefone, String endereco) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setAtivo(true);
        return cliente;
    }

    private Restaurante buildRestaurante(
        String nome,
        String categoria,
        String telefone,
        BigDecimal taxaEntrega,
        int tempoEntregaMin
    ) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(nome);
        restaurante.setCategoria(categoria);
        restaurante.setTelefone(telefone);
        restaurante.setTaxaEntrega(taxaEntrega);
        restaurante.setTempoEntregaMin(tempoEntregaMin);
        restaurante.setAvaliacao(4.5);
        restaurante.setAtivo(true);
        return restaurante;
    }

    private Produto buildProduto(
        String nome,
        String categoria,
        BigDecimal preco,
        String descricao,
        Restaurante restaurante
    ) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setPreco(preco);
        produto.setDescricao(descricao);
        produto.setDisponivel(true);
        produto.setRestaurante(restaurante);
        return produto;
    }

    private ItemPedido buildItem(Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        item.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));
        return item;
    }
}
