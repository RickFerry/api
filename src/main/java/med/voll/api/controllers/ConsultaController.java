package med.voll.api.controllers;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.entities.dto.DadosAgendamentoConsulta;
import med.voll.api.entities.dto.DadosCancelamentoConsulta;
import med.voll.api.entities.dto.DadosDetalhamentoConsulta;
import med.voll.api.services.AgendaDeConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    private final AgendaDeConsultaService agendaDeConsultaService;

    public ConsultaController(AgendaDeConsultaService agendaDeConsultaService) {
        this.agendaDeConsultaService = agendaDeConsultaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        return ResponseEntity.ok(agendaDeConsultaService.agendar(dados));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agendaDeConsultaService.cancelarConsulta(dados);
        return ResponseEntity.noContent().build();
    }
}
