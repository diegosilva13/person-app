package br.com.pessoa.web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController("/pessoas")
public class PessoaResource {
	
	@Autowired 
	PessoaService service;
	
	@Autowired
	ArquivoUtil arquivoUtil;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespostaDTO criar(PessoaDTO pessoaDTO){
		RespostaDTO dto = new RespostaDTO();
        try {
            dto.comObjetoDeResposta((service.criar(pessoaDTO)));
            dto.comAMensagem("Sucesso.").comCodigo200OK();
        }catch (DadoInvalidoException e) {
            e.printStackTrace();
            dto.comAMensagem(e.getMessage()).comCodigo400();
        }catch (Exception e){
            e.printStackTrace();
            dto.comAMensagem(e.getMessage()).comCodigo500();
        }
        return dto;
	}
	
	@RequestMapping(value = "/upload", 
					headers = "Content-Type= multipart/form-data",
					method = RequestMethod.POST, 
					consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
					produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public RespostaDTO uploadFile(@RequestParam("file") MultipartFile arquivo) {
	  RespostaDTO dto = new RespostaDTO();
        try {
            dto.comObjetoDeResposta(arquivoUtil.salvar(arquivo.getOriginalFilename(), arquivo.getBytes(), TipoDiretorio.TEMPORARIO));
            dto.comAMensagem("Sucesso.").comCodigo200OK();
        }catch (DadoInvalidoException e) {
            e.printStackTrace();
            dto.comAMensagem(e.getMessage()).comCodigo400();
        }catch (Exception e){
            e.printStackTrace();
            dto.comAMensagem(e.getMessage()).comCodigo500();
        }
        return dto;
	}
}
