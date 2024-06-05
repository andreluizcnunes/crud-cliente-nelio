package br.com.api.cadastroCliente.services;

import br.com.api.cadastroCliente.dto.ClienteDTO;
import br.com.api.cadastroCliente.entities.Cliente;
import br.com.api.cadastroCliente.repositories.ClienteRepository;
import br.com.api.cadastroCliente.services.exceptions.DatabaseException;
import br.com.api.cadastroCliente.services.exceptions.ResourceNotFoundExcepetion;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        Optional<Cliente> result = Optional.ofNullable(clienteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundExcepetion("Recurso nao encotrado")
        ));
        Cliente cliente = result.get();
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getName(),
                cliente.getCpf(),
                cliente.getIncome(),
                cliente.getBirthDate(),
                cliente.getChildren()
        );

        return clienteDTO;
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> getAllClientes(Pageable pageable) {
        Page<Cliente> result = clienteRepository.findAll(pageable);
        return result.map(x -> new ClienteDTO(x));
    }

    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        copyDTOToEntity(clienteDTO, cliente);
        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(id);
            copyDTOToEntity(clienteDTO, cliente);
            cliente = clienteRepository.save(cliente);
            return new ClienteDTO(cliente);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExcepetion("Recurso nao encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCliente(Long id) {
        if(!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundExcepetion("Recurso nao encontrado");
        }
        try {
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDTOToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setName(dto.getName());
        cliente.setCpf(dto.getCpf());
        cliente.setIncome(dto.getIncome());
        cliente.setBirthDate(dto.getBirthDate());
        cliente.setChildren(dto.getChildren());
    }

}
