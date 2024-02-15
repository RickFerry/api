package med.voll.api.services.validacoes.cancelamento;

import med.voll.api.entities.dto.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta dados);
}
