package br.com.pessoa.web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.pessoa.service.PessoaService;

@Path("/pessoas")
@Produces("application/json")
@Consumes("application/json")
public class PessoaResource {
	
	@Autowired 
	PessoaService service;
	
	
}
