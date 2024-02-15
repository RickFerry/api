package med.voll.api.services;

import med.voll.api.entities.dto.DadosAgendamentoConsulta;
import med.voll.api.entities.dto.DadosCancelamentoConsulta;
import med.voll.api.entities.dto.DadosDetalhamentoConsulta;
import med.voll.api.entities.model.Consulta;
import med.voll.api.entities.model.Medico;
import med.voll.api.entities.model.Paciente;
import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.repositories.ConsultaRepository;
import med.voll.api.repositories.MedicoRepository;
import med.voll.api.repositories.PacienteRepository;
import med.voll.api.services.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.services.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    private final List<ValidadorAgendamentoDeConsulta> validadores;
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public AgendaDeConsultaService(
            List<ValidadorAgendamentoDeConsulta> validadores, ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository, PacienteRepository pacienteRepository,
            List<ValidadorCancelamentoDeConsulta> validadoresCancelamento) {
        this.validadores = validadores;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadoresCancelamento = validadoresCancelamento;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        Medico medico = medicoRepository.findById(dados.idMedico()).orElse(escolherMedico(dados));
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new ValidacaoException("Id do paciente informado não existe."));
        validadores.forEach(v -> v.validar(dados));
        consultaRepository.save(new Consulta(null, medico, paciente, dados.data(), null));
        return new DadosDetalhamentoConsulta(new Consulta(null, medico, paciente, dados.data(), null));
    }

    public void cancelarConsulta(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatoria.");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
