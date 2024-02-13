package med.voll.api.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.entities.*;
import med.voll.api.repositories.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> cadastro(
            @RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        return ResponseEntity.ok(pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new));
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<DadosListagemPaciente>> ListarAtivos(@PageableDefault(sort = {"nome"}) Pageable page){
        return ResponseEntity.ok(pacienteRepository.findAllByAtivoTrue(page).map(DadosListagemPaciente::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = this.pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("inativar/{id}")
    public ResponseEntity<Object> inativar(@PathVariable Long id){
        Paciente paciente = this.pacienteRepository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = this.pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
