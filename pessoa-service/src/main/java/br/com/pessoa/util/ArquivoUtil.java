package br.com.pessoa.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ArquivoUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Value("${arquivos.pastaUpload}")
	private String DIRETORIO_UPLOAD;
	
	/**
	 * Salva o arquivo recebido no diretório expecificado pela propriedade ${arquivos.pastaUpload}
	 * configurada no application.yml
	 * 
	 * @param nome
	 * @param bytes
	 * @return 
	 * @throws IOException 
	 */
	public String save(String nome, byte[] bytes){
		try {
			String estruturaDePastas = criarCriarEstruturaPastas();
			Path path = Paths.get(estruturaDePastas+nome);
			path.toFile().createNewFile();
			Files.write(path, bytes);
			return path.toAbsolutePath().toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private String criarCriarEstruturaPastas() throws IOException {
		Integer dia = LocalDate.now().getDayOfMonth();
		Integer mes = LocalDate.now().getMonthValue();
		Integer ano = LocalDate.now().getYear();
		String pastas = ano.toString()+"/"+mes.toString()+"/"+dia.toString()+"/";
		pastas = DIRETORIO_UPLOAD + pastas;
		new File(pastas).mkdirs();
		return pastas;
	}

	public void apagar(String arquivoAntigo) {
		File arquivo = new File(arquivoAntigo);
		if(arquivo.exists()){
			arquivo.delete();
		}
	}
}
