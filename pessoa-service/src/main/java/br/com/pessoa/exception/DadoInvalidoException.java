package br.com.pessoa.exception;

import java.util.ArrayList;
import java.util.List;

public class DadoInvalidoException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> mensagens = new ArrayList<>();
	
	public DadoInvalidoException(String msg) {
		super(msg);
	}
	
	public DadoInvalidoException(List<String> mensagens) {
		super();
		this.mensagens = mensagens;
	}
	
	public List<String> getMensagens() {
		return mensagens;
	}
	
	@Override
	public String getMessage() {
		StringBuilder erros = new StringBuilder();
		if(mensagens != null && !mensagens.isEmpty()){
			mensagens.forEach(msg -> {erros.append(msg);});
		}
		
		if(super.getMessage() != null){
			erros.append(super.getMessage());
		}
		
		return erros.toString();
	}
}
