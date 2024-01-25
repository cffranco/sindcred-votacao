package com.votacao.service;

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
	
	public Pauta create(Pauta pauta) {
		
		var entity = repository.buscarTextoPauta(pauta.getPauta());
	
		if(entity!=null) {
			throw new ResourceNotFoundException("Essa pauta já foi cadastrada");
		}
		
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
		
		repository.delete(entity);
	}

}
