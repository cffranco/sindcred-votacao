package com.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacao.entity.Pauta;
import com.votacao.entity.Votacao;
import com.votacao.service.VotacaoService;

@RestController
@RequestMapping("/votar")
public class VotacaoController {
	
	@Autowired
	private VotacaoService service;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Votacao computarVoto(@RequestBody Votacao voto) {
		return service.computarVoto(voto);
	}
	
	@GetMapping(value = "/{id}",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public Votacao contarVoto(@PathVariable(value = "id") Integer id) {
		return service.contarVoto(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Votacao> findAll() {
		return service.findAll();
	}
	
			
}
