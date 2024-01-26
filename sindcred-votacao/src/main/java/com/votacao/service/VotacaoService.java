package com.votacao.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.entity.Pauta;
import com.votacao.entity.Votacao;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.VotacaoRepository;

@Service
public class VotacaoService {
	
	private Logger logger = LoggerFactory.getLogger(VotacaoService.class);
	
	@Autowired
	private VotacaoRepository repository;

	public Votacao computarVoto(Votacao voto, Pauta pauta) {
		logger.info("Cadastrando Pauta");
		
		//vadidar Cpf
		if(!verificarCpf(voto.getId().getCpf())) {
			throw new ResourceNotFoundException("CPF Invalido!");
		}
		
		//*Verificar se Votação aberta
		String msg = verificarVotaca(pauta);
		if(msg.equals("Encerrada")) {
			throw new ResourceNotFoundException("Votação encerrada.");
		}else if (msg.equals("NaoIniciada")) {
			throw new ResourceNotFoundException("A Votação ainda não foi iniciada.");
		}
		
		//Validar voto se o cooperado já votou
		var entity = repository.buscarVoto(voto.getId().getId(),voto.getId().getCpf());
		
		if(entity!=null) {
			throw new ResourceNotFoundException("Você já votou nessa pauta!");
		}
		
		logger.info("Cadastrando Voto");
		return repository.save(voto);
				
	}
	
	private String verificarVotaca(Pauta pauta) {
		Calendar agora = Calendar.getInstance();
		
		
		//verificar se votação esta fechada
		if(agora.getTime().before((pauta.getDataVotacao()))) {
			return "NaoIniciada";
		}
		
		//verificar se votação esta encerrada
		
		if(agora.getTime().after(pauta.getDataVotacao())) {
			return "Encerrada";
		}
		
		return "";
		
	}
	
	private Date acrecentarMinuto(Date data) {
		Calendar agora = Calendar.getInstance();
		
		agora.setTime(data);
	     
	    // vamos adicionar 60 minutos a esta data
	    agora.add(Calendar.MINUTE, 60);
	 
	    return agora.getTime();
	}
	
	private boolean verificarCpf(String cpf) {
		
		//Validar cpf
		
		return true;
		
	}
	
	public List<Votacao> findAll() {
		logger.info("Listar todas as votos");
		return repository.findAll();
	}
	
	public Integer contarVoto(Integer id, String voto) {
		return repository.contarVoto(id,voto);
	}
}
