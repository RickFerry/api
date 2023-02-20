package med.voll.api.entities;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade,
                                  DadosEnreco endereco) {
}
