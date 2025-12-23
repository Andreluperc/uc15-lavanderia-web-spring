package br.senac.pi.lavanderia.web;

import br.senac.pi.lavanderia.domain.Operador;
import br.senac.pi.lavanderia.service.OperadorService;
import br.senac.pi.lavanderia.web.dto.LoginRequest;
import br.senac.pi.lavanderia.web.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operadores")
@CrossOrigin
public class OperadorController {

    private final OperadorService service;

    public OperadorController(OperadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Operador> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Operador buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Operador o) {
        try {
            return ResponseEntity.ok(service.criar(o));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Operador o) {
        try {
            return ResponseEntity.ok(service.atualizar(id, o));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        boolean ok = service.autenticar(req.getCpf(), req.getMatricula());
        return ResponseEntity.ok(new LoginResponse(ok, ok ? "OK" : "CPF ou matrícula inválidos"));
    }
}
