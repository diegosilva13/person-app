package br.com.pessoa.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	ArquivoUtil arquivoUtil;
	
	public PessoaDTO criar(PessoaDTO pessoaDTO){
		Pessoa pessoa = pessoaDTO.pessoa();
		
		super.validarDados(pessoa);
		
		if(repository.findByCpf(pessoa.getCpf()).isPresent()){
			throw new DadoInvalidoException("Esse CPF já se encontra cadastrado no sistema.");
		}
		
		salvarFotoPerfil(pessoaDTO);
		pessoa.setFoto(pessoaDTO.getFoto());
		
		pessoaDTO = new PessoaDTO(repository.save(pessoa));
		
		
		return pessoaDTO;
	}
	
	public PessoaDTO atualizar(Integer id, PessoaDTO pessoaDTO){
		Pessoa pessoa = repository.findOne(id);
		pessoa.setCpf(pessoaDTO.getCpf());
		pessoa.setEmail(pessoaDTO.getEmail());
		pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
		pessoa.setFoto(pessoaDTO.getFoto());
		pessoa.setNome(pessoaDTO.getNome());
		
		super.validarDados(pessoa);
		
		salvarFotoPerfil(pessoaDTO);
		pessoa.setFoto(pessoaDTO.getFoto());
		
		pessoaDTO = new PessoaDTO(repository.save(pessoa));
		
		return pessoaDTO;
	}
	
	private void salvarFotoPerfil(PessoaDTO pessoaDTO){
		MultipartFile fotoPerfil = pessoaDTO.getArquivoFoto();
		
		if(fotoPerfil == null){
			return;
		}
		
		if(!fotoPerfil.getName().matches(".*\\.(jpg|png|gif|jpeg)")){
			throw new DadoInvalidoException("Formato de imagem inválido. (Formatos permitidos jpg, png, gif e jpeg)");
		}
		
		byte[] conteudoArquivo = null;
		
		try {
			conteudoArquivo = fotoPerfil.getBytes();
			float tamanhoEmKB = conteudoArquivo.length / 1024;
			float tamanhoEmMB = tamanhoEmKB / 1024;
			if(tamanhoEmMB > 1){
				throw new DadoInvalidoException("O tamanho máximo permitido da imagem é de 1MB.");
			}
			String nomeArquivo = pessoaDTO.getCpf()+fotoPerfil.getName();
			
			String arquivoAntigo = pessoaDTO.getFoto();
			
			pessoaDTO.setFoto(arquivoUtil.save(nomeArquivo, conteudoArquivo));
			
			arquivoUtil.apagar(arquivoAntigo);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DadoInvalidoException("Ocorreu ao salvar a imagem informada. Verifique se o arquivo possui um formato válido ou se a imagem não está corro");
		}
	}
}
