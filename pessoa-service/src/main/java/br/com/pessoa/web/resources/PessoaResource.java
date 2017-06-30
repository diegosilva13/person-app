package br.com.pessoa.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pessoa.dto.PessoaDTO;
import br.com.pessoa.dto.RespostaDTO;
import br.com.pessoa.exception.DadoInvalidoException;
import br.com.pessoa.service.PessoaService;
import br.com.pessoa.util.ArquivoUtil;
import br.com.pessoa.util.ArquivoUtil.TipoDiretorio;

@RestController
public class PessoaResource {

	@Autowired
	private PessoaService service;

	@Autowired
	private ArquivoUtil arquivoUtil;

	@RequestMapping(value = "/pessoas", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO criar(@RequestBody PessoaDTO pessoaDTO) {
		RespostaDTO dto = new RespostaDTO();
		try {
			dto.comObjetoDeResposta((service.criar(pessoaDTO)));
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (DadoInvalidoException e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo400();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}
	
	@RequestMapping(value = "/pessoas/{id}", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO atualizar(@RequestBody PessoaDTO pessoaDTO, @PathVariable Integer id) {
		RespostaDTO dto = new RespostaDTO();
		try {
			dto.comObjetoDeResposta((service.atualizar(id, pessoaDTO)));
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (DadoInvalidoException e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo400();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}

	@RequestMapping(value = "/pessoas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO todos() {
		RespostaDTO dto = new RespostaDTO();
		try {
			dto.comObjetoDeResposta((service.todos()));
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (DadoInvalidoException e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo400();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}
	
	@RequestMapping(value = "/pessoas/excluir", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO excluir(@RequestParam("id") Integer id) {
		RespostaDTO dto = new RespostaDTO();
		try {
			service.excluir(id);
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}

	@RequestMapping(value = "pessoas/upload", 
			headers = "Content-Type= multipart/form-data", 
			method = RequestMethod.POST, 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO upload(@RequestParam("arquivo") MultipartFile arquivo) {
		RespostaDTO dto = new RespostaDTO();
		try {
			service.validaFormatoFotoPerfil(arquivo.getOriginalFilename());
			service.validaTamanhoFotoPerfil(arquivo.getBytes());
			dto.comObjetoDeResposta(
					arquivoUtil.salvar(arquivo.getOriginalFilename(), arquivo.getBytes(), TipoDiretorio.TEMPORARIO));
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (DadoInvalidoException e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo400();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}
	
	@RequestMapping(value = "pessoas/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RespostaDTO buscarPorId(@PathVariable Integer id) {
		RespostaDTO dto = new RespostaDTO();
		try {
			dto.comObjetoDeResposta(service.buscarPorId(id));
			dto.comAMensagem("Sucesso.").comCodigo200OK();
		} catch (DadoInvalidoException e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo400();
		} catch (Exception e) {
			e.printStackTrace();
			dto.comAMensagem(e.getMessage()).comCodigo500();
		}
		return dto;
	}
}
