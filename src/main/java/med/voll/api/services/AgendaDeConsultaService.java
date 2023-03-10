package med.voll.api.services;

import med.voll.api.entities.*;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.ConsultaRepository;
import med.voll.api.repositories.MedicoRepository;
import med.voll.api.repositories.PacienteRepository;
import med.voll.api.services.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        Medico medico = medicoRepository.findById(dados.idMedico()).orElse(escolherMedico(dados));
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new ValidacaoException("Id do paciente informado não existe."));
        validadores.forEach(v -> v.validar(dados));
        consultaRepository.save(new Consulta(null, medico, paciente, dados.data(), null));
        return  new DadosDetalhamentoConsulta(new Consulta(null, medico, paciente, dados.data(), null));
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatoria.");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
