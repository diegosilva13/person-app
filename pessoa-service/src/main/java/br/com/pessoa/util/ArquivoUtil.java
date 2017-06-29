package br.com.pessoa.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ArquivoUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum TipoDiretorio {
		TEMPORARIO, DEFINITIVO;
	}

	@Value("${arquivos.pastaUpload}")
	private String DIRETORIO_UPLOAD;

	/**
	 * Salva o arquivo recebido no diretório expecificado pela propriedade
	 * ${arquivos.pastaUpload} configurada no application.yml
	 * 
	 * @param nome
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public String salvar(String nome, byte[] bytes, TipoDiretorio tipoDiretorio) {
		try {
			String estruturaDePastas = criarEstruturaDePastas(tipoDiretorio, LocalDate.now());
			String nomeArquivo = criarNomeArquivo(nome); 
			String caminhoArquivo = estruturaDePastas + nomeArquivo;
			Path path = Paths.get(caminhoArquivo);
			path.toFile().createNewFile();
			Files.write(path, bytes);
			return caminhoArquivo.replace(DIRETORIO_UPLOAD, "");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public String copiarParaPastaDefinitiva(String nomeArquivo) {
		TipoDiretorio temporario = TipoDiretorio.TEMPORARIO;
		TipoDiretorio definitivo = TipoDiretorio.DEFINITIVO;

		String arquivoAntigo = DIRETORIO_UPLOAD + nomeArquivo;
		String novoArquivo = arquivoAntigo.replace(temporario.toString().toLowerCase(),
													definitivo.toString().toLowerCase());

		Path origem = Paths.get(arquivoAntigo);
		Path destino = Paths.get(novoArquivo);

		try {
			destino.toFile().mkdirs();
			
			if(!destino.toFile().exists()){
				destino.toFile().createNewFile();
				Files.copy(origem, destino);
			} 
			return novoArquivo.replace(DIRETORIO_UPLOAD, "");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private String criarNomeArquivo(String nome) {
		StringBuilder nomeArquivo = new StringBuilder(dataEmString(LocalDate.now()).replace("/", ""));
		nomeArquivo.append(System.currentTimeMillis());
		nomeArquivo.append("_"+nome);
		return nomeArquivo.toString();
	}

	private String criarEstruturaDePastas(TipoDiretorio tipoArquivo, LocalDate data) throws IOException {
		StringBuilder pastas = new StringBuilder(DIRETORIO_UPLOAD);
		pastas.append(tipoArquivo.toString().toLowerCase());
		pastas.append("/");
		pastas.append(dataEmString(data));

		new File(pastas.toString()).mkdirs();
		return pastas.toString();
	}

	private String dataEmString(LocalDate data) {
		Integer dia = data.getDayOfMonth();
		Integer mes = data.getMonthValue();
		Integer ano = data.getYear();
		
		StringBuilder dataString = new StringBuilder();
		dataString.append(ano)
			.append("/")
			.append(mes)
			.append("/")
			.append(dia)
			.append("/");

		return dataString.toString();
	}

	public void apagar(String arquivoAntigo) {
		if (arquivoAntigo == null) {
			return;
		}
		File arquivo = new File(arquivoAntigo);
		if (arquivo.exists()) {
			arquivo.delete();
		}
	}
	
	public void apagarTemporariosParaAData(LocalDate data){
		try {
			String pastas = criarEstruturaDePastas(TipoDiretorio.TEMPORARIO, data);
			FileUtils.deleteDirectory(Paths.get(pastas).toFile());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Falha ao apagar arquivos temporários.");
		}
		
	}
	
	public float tamanhoArquivoEmKB(byte[] conteudoArquivo){
		return conteudoArquivo.length / 1024;
	}
	
	public float tamanhoArquivoEmMB(byte[] conteudoArquivo){
		return tamanhoArquivoEmKB(conteudoArquivo) / 1024;
	}
}
