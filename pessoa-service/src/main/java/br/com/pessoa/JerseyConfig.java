package br.com.pessoa;

import br.com.pessoa.web.resources.PessoaResource;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerResources();
    }

    private void registerResources() {
        register(PessoaResource.class);
    }
}
