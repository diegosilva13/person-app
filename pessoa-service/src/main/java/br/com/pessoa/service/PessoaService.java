package br.com.pessoa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pessoa.dto.PessoaDTO;
import br.com.pessoa.entity.BaseService;
import br.com.pessoa.entity.Pessoa;
import br.com.pessoa.exception.DadoInvalidoException;
import br.com.pessoa.repository.PessoaRepository;
import br.com.pessoa.util.ArquivoUtil;

@Service
public class PessoaService extends BaseService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private ArquivoUtil arquivoUtil;
	
	public PessoaDTO criar(PessoaDTO pessoaDTO){
		Pessoa pessoa = pessoaDTO.pessoa();
		
		super.validarDados(pessoa);
		
		if(repository.findByCpf(pessoa.getCpf()).isPresent()){
			throw new DadoInvalidoException("Esse CPF já se encontra cadastrado no sistema.");
		}

		pessoa = repository.save(pessoa);
		
		pessoa.setFoto(arquivoUtil.copiarParaPastaDefinitiva(pessoaDTO.getFoto()));
		
		return new PessoaDTO(repository.save(pessoa));
	}
	
	public PessoaDTO atualizar(Integer id, PessoaDTO pessoaDTO){
		Pessoa pessoa = repository.findOne(id);
		pessoa.setCpf(pessoaDTO.getCpf());
		pessoa.setEmail(pessoaDTO.getEmail());
		pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
		pessoa.setNome(pessoaDTO.getNome());
		
		super.validarDados(pessoa);
		
		pessoa = repository.save(pessoa);
		
		if(pessoaDTO.getFoto() != null && !pessoa.getFoto().equals(pessoaDTO.getFoto())){
			arquivoUtil.apagar(pessoa.getFoto());
			pessoa.setFoto(arquivoUtil.copiarParaPastaDefinitiva(pessoaDTO.getFoto()));
		}
		
		return new PessoaDTO(repository.save(pessoa));
	}
	
	public void validaTamanhoFotoPerfil(byte[] conteudoArquivo){
		if(arquivoUtil.tamanhoArquivoEmMB(conteudoArquivo) > 1){
			throw new DadoInvalidoException("O tamanho máximo permitido para a imagem do perfil é de 1MB.");
		}
	}
	
	public void validaFormatoFotoPerfil(String nomeArquivo){
		if(!nomeArquivo.matches(".*\\.(jpg|png|gif|jpeg)")){
			throw new DadoInvalidoException("Formato de imagem inválido. (Formatos permitidos jpg, png, gif e jpeg)");
		}
	}
	
	public List<PessoaDTO> todos(){
		return new PessoaDTO(repository.findAll()).lista();
	}

	public void excluir(Integer id) {
		repository.delete(id);
	}

	public PessoaDTO buscarPorId(Integer id) {
		Pessoa pessoa = repository.findOne(id);
		if(pessoa != null){
			return new PessoaDTO(pessoa);	
		}
		
		return null;
	}
}
