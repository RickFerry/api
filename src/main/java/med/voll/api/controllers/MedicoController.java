package med.voll.api.controllers;


import jakarta.validation.Valid;
import med.voll.api.entities.*;
import med.voll.api.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = new Medico(dados);
        medicoRepository.save(medico);
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        return ResponseEntity.ok(medicoRepository.findAll(page).map(DadosListagemMedico::new));
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<DadosListagemMedico>> ListarAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        return ResponseEntity.ok(medicoRepository.findAllByAtivoTrue(page).map(DadosListagemMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id){
        medicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("inativar/{id}")
    public ResponseEntity<Object> inativar(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
