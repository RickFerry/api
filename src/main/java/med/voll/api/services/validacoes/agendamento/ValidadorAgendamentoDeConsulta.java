package med.voll.api.services.validacoes.agendamento;


import med.voll.api.entities.dto.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);

}
