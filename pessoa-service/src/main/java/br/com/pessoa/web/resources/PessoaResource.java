package br.com.pessoa.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.pessoa.dto.PessoaDTO;
import br.com.pessoa.dto.RespostaDTO;
import br.com.pessoa.exception.DadoInvalidoException;
import br.com.pessoa.service.PessoaService;

@Path("/pessoas")
@Produces("application/json")
@Consumes("application/json")
public class PessoaResource {
	
	@Autowired 
	PessoaService service;
	
	
	@POST
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
	
}
