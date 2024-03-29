package med.voll.api.services.validacoes.agendamento;

import med.voll.api.entities.dto.DadosAgendamentoConsulta;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {
    private final PacienteRepository repository;

    public ValidadorPacienteAtivo(PacienteRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());

        if (Boolean.FALSE.equals(pacienteEstaAtivo)) {
            throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
        }
    }
}
