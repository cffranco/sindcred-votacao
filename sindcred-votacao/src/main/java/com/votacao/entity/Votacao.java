package com.votacao.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "votacao")
public class Votacao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private VotacaoId id;
	
	@Column(name = "voto")
	private String voto;
	
	public Votacao() {
		
	}

	public VotacaoId getId() {
		return id;
	}

	public void setId(VotacaoId id) {
		this.id = id;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, voto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Votacao other = (Votacao) obj;
		return Objects.equals(id, other.id) && Objects.equals(voto, other.voto);
	}


	
	
}
