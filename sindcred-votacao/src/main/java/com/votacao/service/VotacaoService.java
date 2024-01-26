package com.votacao.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.entity.Votacao;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.VotacaoRepository;

@Service
public class VotacaoService {
	
	private Logger logger = LoggerFactory.getLogger(VotacaoService.class);
	
	@Autowired
	private VotacaoRepository repository;

	public Votacao computarVoto(Votacao voto) {
		logger.info("Cadastrando Pauta");
		
		//vadidar Cpf
		if(!verificarCpf(voto.getId().getCpf())) {
			throw new ResourceNotFoundException("CPF Invalido!");
		}
		
		//Validar voto se o cooperado já votou
		var entity = repository.buscarVoto(voto.getId().getId(),voto.getId().getCpf());
		
		if(entity!=null) {
			throw new ResourceNotFoundException("Você já votou nessa pauta!");
		}
		
		logger.info("Cadastrando Voto");
		return repository.save(voto);
				
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
