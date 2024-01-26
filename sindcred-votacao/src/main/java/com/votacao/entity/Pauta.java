package com.votacao.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "pauta")
public class Pauta implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "descricao_pauta", nullable = false, length = 255)
	private String pauta;
	
	@Column(name = "data_votacao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVotacao;
	
	@Transient
	private Integer numeroVotos;
	
	@Transient
	private Integer qtdSim;

	@Transient
	private Integer qtdNao;
	
	public Pauta() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPauta() {
		return pauta;
	}

	public void setPauta(String pauta) {
		this.pauta = pauta;
	}

	public Date getDataVotacao() {
		return dataVotacao;
	}

	public void setDataVotacao(Date dataVotacao) {
		this.dataVotacao = dataVotacao;
	}

	public Integer getNumeroVotos() {
		return numeroVotos;
	}

	public void setNumeroVotos(Integer numeroVotos) {
		this.numeroVotos = numeroVotos;
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
		return Objects.hash(dataVotacao, id, numeroVotos, pauta, qtdNao, qtdSim);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pauta other = (Pauta) obj;
		return Objects.equals(dataVotacao, other.dataVotacao) && Objects.equals(id, other.id)
				&& Objects.equals(numeroVotos, other.numeroVotos) && Objects.equals(pauta, other.pauta)
				&& Objects.equals(qtdNao, other.qtdNao) && Objects.equals(qtdSim, other.qtdSim);
	}

	
	
	
	

}
