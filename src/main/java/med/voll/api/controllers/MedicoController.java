package med.voll.api.controllers;

import jakarta.validation.Valid;
import med.voll.api.entities.DadosAtualizacaoMedico;
import med.voll.api.entities.DadosCadastroMedico;
import med.voll.api.entities.DadosListagemMedico;
import med.voll.api.entities.Medico;
import med.voll.api.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastro(@RequestBody @Valid DadosCadastroMedico dados) {
        medicoRepository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedico> Listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        return medicoRepository.findAll(page).map(DadosListagemMedico::new);
    }

    @GetMapping("/ativos")
    public Page<DadosListagemMedico> ListarAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable page){
        return medicoRepository.findAllByAtivoTrue(page).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        medicoRepository.deleteById(id);
    }

    @Transactional
    @DeleteMapping("inativar/{id}")
    public void inativar(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
