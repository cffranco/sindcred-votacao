package com.votacao.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.votacao.entity.Pauta;
import com.votacao.entity.Votacao;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.VotacaoRepository;
import com.votacao.response.ValidaCpf;

@Service
public class VotacaoService {
	
	private Logger logger = LoggerFactory.getLogger(VotacaoService.class);
	
	@Autowired
	private VotacaoRepository repository;

	public Votacao computarVoto(Votacao voto, Pauta pauta) {
		logger.info("Cadastrando Pauta");
		
		if(!voto.getVoto().equals("Sim") && !voto.getVoto().equals("Nao")) {
			throw new ResourceNotFoundException("Voto inválido digite (Sim ou Nao)");
		}
		
		HashMap<String, String> params = new HashMap<>();
		params.put("cpf",voto.getId().getCpf());
		
		var validaCpf = new RestTemplate().getForEntity("http://localhost:8100/valida/{cpf}", ValidaCpf.class, params);
		
		//vadidar Cpf
		if(validaCpf.getBody().getCpfValido().equals("nao")) {
			throw new ResourceNotFoundException("CPF Invalido!");
		}
		
		//*Verificar se Votação aberta
		String msg = verificarVotacao(pauta);
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
	
	String verificarVotacao(Pauta pauta) {
		Calendar agora = Calendar.getInstance();
		
		//verificar se votação esta fechada
		if(agora.getTime().before((pauta.getDataVotacao()))) {
			return "NaoIniciada";
		}
		
		//verificar se votação esta encerrada
		
		if(agora.getTime().after(acrecentarMinuto(pauta.getDataVotacao()))) {
			return "Encerrada";
		}
		
		return "";
		
	}
	
	private Date acrecentarMinuto(Date data) {
		Calendar tempoVotacao = Calendar.getInstance();
		
		tempoVotacao.setTime(data);
	     
	    // adicionar 1 minutos a esta data
		tempoVotacao.add(Calendar.MINUTE, 1);
	 
	    return tempoVotacao.getTime();
	}
	
	public List<Votacao> findAll() {
		logger.info("Listar todas as votos");
		return repository.findAll();
	}
	
	public Integer contarVoto(Integer id, String voto) {
		return repository.contarVoto(id,voto);
	}
	
	public Integer procuraVoto(Integer id) {
		return repository.procuraVoto(id);
	}
	
}

