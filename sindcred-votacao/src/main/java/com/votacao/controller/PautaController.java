package com.votacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacao.entity.Pauta;
import com.votacao.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {
	
	@Autowired
	private PautaService service;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Pauta create(@RequestBody Pauta pauta) {
		return service.create(pauta);
	}
	

	@GetMapping(value = "/{id}",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public Pauta findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Pauta> findAll() {
		return service.findAll();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "resultado/{id}",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public Pauta contarVoto(@PathVariable(value = "id") Integer id) {
		return service.contarVoto(id);
	}
	

}
