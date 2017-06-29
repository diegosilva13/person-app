package br.com.pessoa.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.pessoa.entity.Pessoa;


public class PessoaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String nome;
	
	private String cpf;
	
	private String foto;
	
	private String email;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
	private List<Pessoa> pessoas;
	
	public PessoaDTO() {
		
	}
	
	public PessoaDTO(Pessoa pessoa) {
		this();
		this.cpf = pessoa.getCpf();
		this.dataNascimento = pessoa.getDataNascimento();
		this.email = pessoa.getEmail();
		this.foto = pessoa.getFoto();
		this.nome = pessoa.getNome();
		this.id = pessoa.getId();
	}
	
	public PessoaDTO(List<Pessoa> todos) {
		this.pessoas = todos;
	}

	public Pessoa pessoa(){
		Pessoa pessoa = new Pessoa();
		pessoa.setCpf(cpf);
		pessoa.setDataNascimento(dataNascimento);
		pessoa.setEmail(email);
		pessoa.setFoto(foto);
		pessoa.setNome(nome);
		pessoa.setId(id);
		return pessoa;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public List<PessoaDTO> lista(){
		List<PessoaDTO> lista = new ArrayList<>();
		if(pessoas != null && !pessoas.isEmpty()){
			pessoas.forEach(pessoa ->{
				lista.add(new PessoaDTO(pessoa));
			});
		}
		
		return lista;
	}
}
