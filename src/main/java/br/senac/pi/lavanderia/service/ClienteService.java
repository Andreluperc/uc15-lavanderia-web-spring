package br.senac.pi.lavanderia.service;

import br.senac.pi.lavanderia.domain.Cliente;
import br.senac.pi.lavanderia.repository.ClienteRepository;
import br.senac.pi.lavanderia.util.CpfUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente criar(Cliente c) {
        validar(c);
        c.setCpf(CpfUtils.normalizar(c.getCpf()));
        return repo.save(c);
    }

    public Cliente atualizar(Long id, Cliente payload) {
        validar(payload);
        Cliente atual = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        atual.setNome(payload.getNome());
        atual.setCpf(CpfUtils.normalizar(payload.getCpf()));
        atual.setTelefone(payload.getTelefone());
        return repo.save(atual);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    }

    private void validar(Cliente c) {
        if (c == null) throw new IllegalArgumentException("Cliente inválido");
        if (c.getNome() == null || c.getNome().trim().length() < 3) throw new IllegalArgumentException("Nome deve ter ao menos 3 caracteres");
        if (!CpfUtils.isValidoBasico(c.getCpf())) throw new IllegalArgumentException("CPF inválido");
    }
}
