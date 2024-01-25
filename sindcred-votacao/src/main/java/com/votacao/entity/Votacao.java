package com.votacao.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "votacao")
public class Votacao {
	
	@EmbeddedId
    private VotacaoId id;
	
	@Column(name = "voto")
	private String voto;
	
	@Transient
	private Integer qtdSim;
	
	@Transient
	private Integer qtdNao;
	
	
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


	public Integer getQtdSim() {
		return qtdSim;
	}


	public void setQtdSim(Integer qtdSim) {
		this.qtdSim = qtdSim;
	}


	public Integer getQtdNao() {
		return qtdNao;
	}


	public void setQtdNao(Integer qtdNao) {
		this.qtdNao = qtdNao;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, qtdNao, qtdSim, voto);
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
		return Objects.equals(id, other.id) && Objects.equals(qtdNao, other.qtdNao)
				&& Objects.equals(qtdSim, other.qtdSim) && Objects.equals(voto, other.voto);
	}

	
	
	
}
