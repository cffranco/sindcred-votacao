package com.votacao.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.entity.Pauta;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.PautaRepository;

@Service
public class PautaService {
	
	private Logger logger = LoggerFactory.getLogger(PautaService.class);
	
	@Autowired
	PautaRepository repository;
	
	@Autowired
	VotacaoService votacaoService;
	
	public Pauta create(Pauta pauta) {		
		var entity = repository.buscarTextoPauta(pauta.getPauta());
	
		if(entity!=null) {
			throw new ResourceNotFoundException("Essa pauta já foi cadastrada");
		}
		
		Calendar agora = Calendar.getInstance();
		agora.setTime(pauta.getDataVotacao());
		agora.add(Calendar.MINUTE, 180);
		
		pauta.setDataVotacao(agora.getTime());
		
		logger.info("Cadastrando Pauta");
		return repository.save(pauta);
		
	}
	
	public Pauta  findById(Integer id) {
		logger.info("Procurando pauta por id");			
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
	}
	
	public List<Pauta> findAll() {
		logger.info("Listar todas as pautas");
		return repository.findAll();
	}
	
	public void delete(Integer id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		//Verificar se tem algum voto
		logger.info("Excluindo pauta por id");
		repository.delete(entity);
	}
	
	public Pauta contarVoto(Integer id) {		
		Pauta pauta = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum registro encontrado"));
		logger.info("Fazendo a contagem de votos");
		Integer votoSim = votacaoService.contarVoto(id, "Sim");
		Integer votoNao = votacaoService.contarVoto(id, "Nao");
		
		pauta.setQtdNao(votoNao);
		pauta.setQtdSim(votoSim);
		pauta.setNumeroVotos(votoSim+votoNao);
		
		return pauta;		
	}

}
