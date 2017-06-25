package br.com.pessoa.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

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
	
	private MultipartFile arquivoFoto;
	
	private String email;
	
	private Date dataNascimento;
	
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
	
	public MultipartFile getArquivoFoto() {
		return arquivoFoto;
	}
	
	public void setArquivoFoto(MultipartFile arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}
}
