package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.ClienteDTO;
import com.deliverytech.delivery.dto.ClienteResponseDTO;
import com.deliverytech.delivery.entity.Cliente;
import com.deliverytech.delivery.exception.ConflictException;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.ClienteRepository;
import com.deliverytech.delivery.service.ClienteService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponseDTO cadastrarCliente(ClienteDTO dto) {
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new ConflictException("Email ja cadastrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setAtivo(true);

        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Override
    public ClienteResponseDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado."));
        return toResponse(cliente);
    }

    @Override
    public ClienteResponseDTO buscarClientePorEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado."));
        return toResponse(cliente);
    }

    @Override
    public ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado."));

        if (!cliente.getEmail().equalsIgnoreCase(dto.email())
            && clienteRepository.existsByEmail(dto.email())) {
            throw new ConflictException("Email ja cadastrado.");
        }

        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());

        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Override
    public ClienteResponseDTO ativarDesativarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cliente nao encontrado."));

        cliente.setAtivo(!cliente.getAtivo());
        Cliente salvo = clienteRepository.save(cliente);
        return toResponse(salvo);
    }

    @Override
    public List<ClienteResponseDTO> listarClientesAtivos() {
        return clienteRepository.findByAtivoTrue()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone(),
            cliente.getEndereco(),
            cliente.getAtivo(),
            cliente.getDataCadastro()
        );
    }
}
