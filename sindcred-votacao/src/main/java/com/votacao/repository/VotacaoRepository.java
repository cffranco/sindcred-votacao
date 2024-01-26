package com.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.votacao.entity.Votacao;
import com.votacao.entity.VotacaoId;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, VotacaoId> {
	
	@Query("Select v from Votacao v where v.id.id = :id and v.id.cpf = :cpf")
	Votacao buscarVoto(Integer id, String cpf);
	
	@Query("Select count(v.voto) from Votacao v where v.id.id = :id and upper(v.voto) = upper(:voto)")
	Integer contarVoto(Integer id, String voto);

}
