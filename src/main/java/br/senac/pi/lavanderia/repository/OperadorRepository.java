package br.senac.pi.lavanderia.repository;

import br.senac.pi.lavanderia.domain.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperadorRepository extends JpaRepository<Operador, Long> {
    Optional<Operador> findByCpfAndMatricula(String cpf, String matricula);
    Optional<Operador> findByMatricula(String matricula);
}
