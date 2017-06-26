package br.com.pessoa.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class RespostaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer codigoResposta;
	
	private String mensagem;
	
	private Object objetoResposta;
	
	public RespostaDTO(){}
	
	public RespostaDTO comCodigo200OK(){
		this.codigoResposta = HttpStatus.OK.value();
		return this;
	}
	
	public RespostaDTO comCodigo500(){
		this.codigoResposta = HttpStatus.INTERNAL_SERVER_ERROR.value();
		return this;
	}
	
	public RespostaDTO comCodigo400(){
		this.codigoResposta = HttpStatus.BAD_REQUEST.value();
		return this;
	}
	
	public RespostaDTO comAMensagem(String msg){
		this.mensagem = msg;
		return this;
	}
	
	public RespostaDTO comObjetoDeResposta(Object obj){
		this.objetoResposta = obj;
		return this;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public Integer getCodigoResposta() {
		return codigoResposta;
	}
	
	public Object getObjetoResposta() {
		return objetoResposta;
	}

	public HttpStatus getStatus() {
		return HttpStatus.valueOf(getCodigoResposta());
	}
}
