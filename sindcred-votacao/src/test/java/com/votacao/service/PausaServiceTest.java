package com.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.votacao.entity.Pauta;
import com.votacao.exceptions.ResourceNotFoundException;
import com.votacao.repository.PautaRepository;

public class PausaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotacaoService votacaoService;

    @InjectMocks
    private PautaService pautaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePauta() {
        Pauta existingPauta = new Pauta();
        existingPauta.setId(1);
        existingPauta.setPauta("Existing Pauta");
        
        when(pautaRepository.buscarTextoPauta(existingPauta.getPauta())).thenReturn(existingPauta);

        Pauta newPauta = new Pauta();
        newPauta.setPauta("New Pauta");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 180);
        newPauta.setDataVotacao(now.getTime());

        when(pautaRepository.save(any(Pauta.class))).thenReturn(newPauta);

        assertThrows(ResourceNotFoundException.class, () -> pautaService.create(existingPauta));

        Pauta savedPauta = pautaService.create(newPauta);

        assertNotNull(savedPauta);
        assertEquals(newPauta.getPauta(), savedPauta.getPauta());
        assertEquals(now.getTime(), savedPauta.getDataVotacao());
    }

    @Test
    void testFindById() {
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");

        when(pautaRepository.findById(1)).thenReturn(Optional.of(pauta));

        Pauta foundPauta = pautaService.findById(1);

        assertNotNull(foundPauta);
        assertEquals(pauta, foundPauta);
    }

    @Test
    void testFindAll() {
        Pauta pauta1 = new Pauta();
        pauta1.setId(1);
        pauta1.setPauta("Pauta 1");

        Pauta pauta2 = new Pauta();
        pauta2.setId(2);
        pauta2.setPauta("Pauta 2");

        List<Pauta> pautaList = Arrays.asList(pauta1, pauta2);

        when(pautaRepository.findAll()).thenReturn(pautaList);

        List<Pauta> foundPautas = pautaService.findAll();

        assertNotNull(foundPautas);
        assertEquals(pautaList.size(), foundPautas.size());
        assertTrue(foundPautas.containsAll(pautaList));
    }

    @Test
    void testDelete() {
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");

        when(pautaRepository.findById(1)).thenReturn(Optional.of(pauta));

        pautaService.delete(1);

        verify(pautaRepository, times(1)).delete(pauta);
    }

    @Test
    void testContarVoto() {
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setPauta("Test Pauta");

        when(pautaRepository.findById(1)).thenReturn(Optional.of(pauta));
        when(votacaoService.contarVoto(1, "Sim")).thenReturn(5);
        when(votacaoService.contarVoto(1, "Nao")).thenReturn(3);

        Pauta countedPauta = pautaService.contarVoto(1);

        assertNotNull(countedPauta);
        assertEquals(5, countedPauta.getQtdSim());
        assertEquals(3, countedPauta.getQtdNao());
        assertEquals(8, countedPauta.getNumeroVotos());
    }
}
