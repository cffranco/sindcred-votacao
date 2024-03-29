package com.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.votacao.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {
	
	@Query("Select p from Pauta p where upper(p.pauta) = upper(:texto)")
	Pauta buscarTextoPauta(String texto);
	
	@Query("Select count(v.voto) from Votacao v where v.id = :id and upper(v.voto)  = upper(:voto)")
	Integer contarVoto(Integer id);

}
