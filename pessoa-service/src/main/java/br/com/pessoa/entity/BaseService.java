package br.com.pessoa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;

import br.com.pessoa.exception.DadoInvalidoException;



public class BaseService implements Serializable{
	
	@Autowired
	SmartValidator validatorSpring;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected <E extends Serializable>void validarDados(E entity) {
		List<String> mensagens = new ArrayList<>();
		
		aplicarValidadores(entity, mensagens);
		
		if(!mensagens.isEmpty()){
			throw new DadoInvalidoException(mensagens);
		}
	 }

	private <E extends Serializable> void aplicarValidadores(E entity, List<String> mensagens) {
		DataBinder dataBinder = new DataBinder(entity);
		dataBinder.setValidator(validatorSpring);
		dataBinder.validate();
		BindingResult bindingResult = dataBinder.getBindingResult();
		bindingResult.getAllErrors().forEach(erro ->{
			mensagens.add(erro.getDefaultMessage());
		});
	}
}
