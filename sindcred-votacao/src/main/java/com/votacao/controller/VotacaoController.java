package com.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacao.entity.Pauta;
import com.votacao.entity.Votacao;
import com.votacao.service.PautaService;
import com.votacao.service.VotacaoService;

@RestController
@RequestMapping("/votar")
public class VotacaoController {
	
	@Autowired
	private VotacaoService service;
	
	@Autowired
	private PautaService pautaService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Votacao computarVoto(@RequestBody Votacao voto) {
		Pauta pauta = pautaService.findById(voto.getId().getId());
		return service.computarVoto(voto, pauta);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Votacao> findAll() {
		return service.findAll();
	}
	
	
			
}
