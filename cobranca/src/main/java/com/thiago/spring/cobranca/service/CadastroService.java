package com.thiago.spring.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.thiago.spring.cobranca.model.StatusTitulo;
import com.thiago.spring.cobranca.model.Titulo;
import com.thiago.spring.cobranca.repository.Titulos;
import com.thiago.spring.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroService {

	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);

		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}

	public void excluir(Long codigo) {
		titulos.deleteById(codigo);

	}

	public String receber(Long codigo) {
		@SuppressWarnings("deprecation")
		Titulo titulo = titulos.getOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);

		return StatusTitulo.RECEBIDO.getDescricao();
	}

	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}

}
