package med.voll.api.services.validacoes.cancelamento;

import med.voll.api.entities.dto.DadosCancelamentoConsulta;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.ConsultaRepository;
import org.springframework.stereotype.Component;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{
    private final ConsultaRepository repository;

    public ValidadorHorarioAntecedencia(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = now();
        var diferencaEmHoras = between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
