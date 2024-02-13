package med.voll.api.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico extends Pessoa {
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    public Medico() {
        super();
    }

    public Medico(DadosCadastroMedico dados) {
        super();
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        super.atualizarInformacoes(dados.nome(), dados.telefone(), dados.endereco());
    }
}
