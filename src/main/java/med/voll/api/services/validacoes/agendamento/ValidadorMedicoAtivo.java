package med.voll.api.services.validacoes.agendamento;

import med.voll.api.entities.dto.DadosAgendamentoConsulta;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    private final MedicoRepository repository;

    public ValidadorMedicoAtivo(MedicoRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do medico opcional
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }

}
