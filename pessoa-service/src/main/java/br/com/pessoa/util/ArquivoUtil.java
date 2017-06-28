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
			String estruturaDePastas = criarCriarEstruturaPastas(tipoDiretorio);
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

	public String copiarParaDefinitivo(String nomeArquivo) {
		TipoDiretorio temporario = TipoDiretorio.TEMPORARIO;
		TipoDiretorio definitivo = TipoDiretorio.DEFINITIVO;

		String arquivoAntigo = DIRETORIO_UPLOAD + nomeArquivo;
		String novoArquivo = arquivoAntigo.replace(temporario.toString().toLowerCase(),
													definitivo.toString().toLowerCase());

		Path origem = Paths.get(arquivoAntigo);
		Path destino = Paths.get(novoArquivo);

		try {
			Files.copy(origem, destino);
			return novoArquivo.replace(DIRETORIO_UPLOAD, "");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private String criarNomeArquivo(String nome) {
		StringBuilder nomeArquivo = new StringBuilder(dataEmTexto().replace("/", ""));
		nomeArquivo.append(System.currentTimeMillis());
		nomeArquivo.append(nome);
		return nomeArquivo.toString();
	}

	private String criarCriarEstruturaPastas(TipoDiretorio tipoArquivo) throws IOException {
		StringBuilder pastas = new StringBuilder(DIRETORIO_UPLOAD);
		pastas.append(tipoArquivo.toString().toLowerCase());
		pastas.append("/");
		pastas.append(dataEmTexto());

		new File(pastas.toString()).mkdirs();
		return pastas.toString();
	}

	private String dataEmTexto() {
		Integer dia = LocalDate.now().getDayOfMonth();
		Integer mes = LocalDate.now().getMonthValue();
		Integer ano = LocalDate.now().getYear();
		StringBuilder data = new StringBuilder();
		data.append(ano).append("/").append(mes).append("/").append(dia).append("/");

		return data.toString();
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
}
