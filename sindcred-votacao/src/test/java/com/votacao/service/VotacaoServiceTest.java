package com.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.votacao.entity.Pauta;
import com.votacao.entity.Votacao;
import com.votacao.entity.VotacaoId;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.VotacaoRepository;
import com.votacao.response.ValidaCpf;

public class VotacaoServiceTest {


    @Mock
    private VotacaoRepository votacaoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VotacaoService votacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testComputarVoto() {
        Votacao voto = new Votacao();
        VotacaoId votoId = new VotacaoId();
        votoId.setId(1);
        votoId.setCpf("12345678900");
        voto.setId(votoId);

        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");
        pauta.setDataVotacao(new Date());
   
        ResponseEntity<ValidaCpf> validaCpfResponseEntity = new ResponseEntity<>(new ValidaCpf("sim"), HttpStatus.OK);
        when(restTemplate.exchange(
                eq("http://localhost:8100/valida/{cpf}"),
                eq(HttpMethod.GET),
                eq(null),
                eq(ValidaCpf.class),
                anyMap()
            ))
            .thenReturn(validaCpfResponseEntity);
        
        when(votacaoRepository.buscarVoto(1, "12345678900")).thenReturn(null);

        Votacao savedVoto = new Votacao();
        when(votacaoRepository.save(voto)).thenReturn(savedVoto);
        
        verify(votacaoRepository,times(1)).save(any());
    }

    @Test
    void testComputarVotoInvalidCpf() {
        Votacao voto = new Votacao();
        VotacaoId votoId = new VotacaoId();
        votoId.setId(1);
        votoId.setCpf("12345678900");
        voto.setId(votoId);

        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");
        pauta.setDataVotacao(new Date());

        ResponseEntity<ValidaCpf> validaCpfResponseEntity = new ResponseEntity<>(new ValidaCpf("sim"), HttpStatus.OK);
        when(restTemplate.exchange(
                eq("http://localhost:8100/valida/{cpf}"),
                eq(HttpMethod.GET),
                eq(null),
                eq(ValidaCpf.class),
                anyMap()
            ))
            .thenReturn(validaCpfResponseEntity);
        
        assertThrows(ResourceNotFoundException.class, () -> votacaoService.computarVoto(voto, pauta));
    }

    @Test
    void testComputarVotoVotacaoEncerrada() {
        Votacao voto = new Votacao();
        VotacaoId votoId = new VotacaoId();
        votoId.setId(1);
        votoId.setCpf("12345678900");
        voto.setId(votoId);

        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1); 
        pauta.setDataVotacao(calendar.getTime());

        assertThrows(ResourceNotFoundException.class, () -> votacaoService.computarVoto(voto, pauta));
    }

    @Test
    void testComputarVotoVotacaoNaoIniciada() {
        Votacao voto = new Votacao();
        VotacaoId votoId = new VotacaoId();
        votoId.setId(1);
        votoId.setCpf("12345678900");
        voto.setId(votoId);

        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1); 
        pauta.setDataVotacao(calendar.getTime());

        assertThrows(ResourceNotFoundException.class, () -> votacaoService.computarVoto(voto, pauta));
    }

    @Test
    void testVerificarVotacaoEncerrada() {
        Pauta pauta = new Pauta();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1); 
        pauta.setDataVotacao(calendar.getTime());

        String result = votacaoService.verificarVotacao(pauta);

        assertEquals("Encerrada", result);
    }

    @Test
    void testVerificarVotacaoNaoIniciada() {
        Pauta pauta = new Pauta();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1); 
        pauta.setDataVotacao(calendar.getTime());

        String result = votacaoService.verificarVotacao(pauta);

        assertEquals("NaoIniciada", result);
    }

    @Test
    void testVerificarVotacaoAberta() {
        Pauta pauta = new Pauta();
        pauta.setDataVotacao(new Date());

        String result = votacaoService.verificarVotacao(pauta);

        assertEquals("", result);
    }

    @Test
    void testFindAll() {
        List<Votacao> votacaoList = List.of(new Votacao(), new Votacao());

        when(votacaoRepository.findAll()).thenReturn(votacaoList);

        List<Votacao> foundVotacoes = votacaoService.findAll();

        assertNotNull(foundVotacoes);
        assertEquals(votacaoList.size(), foundVotacoes.size());
        assertTrue(foundVotacoes.containsAll(votacaoList));
    }

    @Test
    void testContarVoto() {
        when(votacaoRepository.contarVoto(1, "Sim")).thenReturn(5);

        Integer count = votacaoService.contarVoto(1, "Sim");

        assertEquals(5, count);
    }
}
