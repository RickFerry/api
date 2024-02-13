package med.voll.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "pacientes")
@Entity(name = "Paciente")
public class Paciente extends Pessoa {
    private String cpf;

    public Paciente() {
        super();
    }

    public Paciente(DadosCadastroPaciente dados) {
        super();
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        super.atualizarInformacoes(dados.nome(), dados.telefone(), dados.endereco());
    }
}
