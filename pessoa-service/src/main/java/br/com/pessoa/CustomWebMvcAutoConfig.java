package br.com.pessoa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CustomWebMvcAutoConfig extends WebMvcConfigurerAdapter {

	@Value("${arquivos.pastaUpload}")
	private String DIRETORIO_UPLOAD;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/arquivos/**").addResourceLocations("file:///" + DIRETORIO_UPLOAD);
	}

}
