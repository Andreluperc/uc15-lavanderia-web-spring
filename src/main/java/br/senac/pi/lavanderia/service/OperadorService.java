package br.senac.pi.lavanderia.service;

import br.senac.pi.lavanderia.domain.Operador;
import br.senac.pi.lavanderia.repository.OperadorRepository;
import br.senac.pi.lavanderia.util.CpfUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    private final OperadorRepository repo;

    public OperadorService(OperadorRepository repo) {
        this.repo = repo;
    }

    public Operador criar(Operador o) {
        validar(o);
        o.setCpf(CpfUtils.normalizar(o.getCpf()));
        return repo.save(o);
    }

    public Operador atualizar(Long id, Operador payload) {
        validar(payload);
        Operador atual = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Operador não encontrado"));
        atual.setNome(payload.getNome());
        atual.setCpf(CpfUtils.normalizar(payload.getCpf()));
        atual.setMatricula(payload.getMatricula());
        atual.setPerfil(payload.getPerfil());
        return repo.save(atual);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }

    public List<Operador> listar() {
        return repo.findAll();
    }

    public Operador buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Operador não encontrado"));
    }

    public boolean autenticar(String cpf, String matricula) {
        String n = CpfUtils.normalizar(cpf);
        return repo.findByCpfAndMatricula(n, matricula).isPresent();
    }

    private void validar(Operador o) {
        if (o == null) throw new IllegalArgumentException("Operador inválido");
        if (o.getNome() == null || o.getNome().trim().length() < 3) throw new IllegalArgumentException("Nome deve ter ao menos 3 caracteres");
        if (!CpfUtils.isValidoBasico(o.getCpf())) throw new IllegalArgumentException("CPF inválido");
        if (o.getMatricula() == null || o.getMatricula().trim().length() < 3) throw new IllegalArgumentException("Matrícula inválida");
        if (o.getPerfil() == null || o.getPerfil().trim().isEmpty()) throw new IllegalArgumentException("Perfil é obrigatório");
    }
}
