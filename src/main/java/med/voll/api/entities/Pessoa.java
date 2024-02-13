package med.voll.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nome;
    protected String email;
    protected String telefone;
    @Embedded
    protected Endereco endereco;
    protected Boolean ativo;

    public void atualizarInformacoes(String nome, String telefone, DadosEndereco endereco) {
        if (nome != null) {
            this.nome = nome;
        }
        if (telefone != null) {
            this.telefone = telefone;
        }
        if (endereco != null) {
            this.endereco.atualizarInformacoes(endereco);
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
