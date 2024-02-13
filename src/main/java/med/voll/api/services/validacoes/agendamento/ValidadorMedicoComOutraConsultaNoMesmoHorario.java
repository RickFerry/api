package med.voll.api.services.validacoes.agendamento;

import med.voll.api.entities.DadosAgendamentoConsulta;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    private final ConsultaRepository repository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }

}
