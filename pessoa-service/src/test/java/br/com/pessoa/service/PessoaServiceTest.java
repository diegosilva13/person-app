package br.com.pessoa.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import br.com.pessoa.PessoaServiceApplicationTests;
import br.com.pessoa.dto.PessoaDTO;
import br.com.pessoa.exception.DadoInvalidoException;


public class PessoaServiceTest extends PessoaServiceApplicationTests{
	
	@Autowired
	PessoaService service;
	
	MultipartFile multipartFile;
	
	@Before
	public void init() throws URISyntaxException, IOException{
		multipartFile = Mockito.mock(MultipartFile.class);
		
		Path path = Paths.get(this.getClass().getClassLoader().getResource("img_test/perfil_1_05Mb.jpg").toURI());
		byte[] arquivo = Files.readAllBytes(path);
		
		Mockito.when(multipartFile.getBytes()).thenReturn(arquivo);
	}
	
	@Test(expected = DadoInvalidoException.class)
	public void tentarCriarPessoaSemDadosTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		service.criar(pessoaDTO);
	}
	
	@Test
	public void tentarCriarPessoaSemNomeTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome(null);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Nome - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComNomeInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("12312");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O nome informado é inválido."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComNomeMaiorQue150Test(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("It is important to be able to perform some integration testing without requiring deployment to your application server or connecting to other enterprise infrastructure.");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Nome - O tamanho máximo permitido de caracteres é 150."));
		}
	}
	
	@Test
	public void tentarCriarPessoaSemCpfTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf(null);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("CPF - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComCpfInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("04653183122");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O CPF informado é inválido."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComCpfDuplicadoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("04653183120");
		pessoaDTO.setEmail("etste@email.com");
		pessoaDTO.setDataNascimento(new Date());
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMessage().equalsIgnoreCase("Esse CPF já se encontra cadastrado no sistema."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComFormatoDeImagemDePerfilInvalidaTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("04653183120");
		pessoaDTO.setEmail("etste@email.com");
		pessoaDTO.setDataNascimento(new Date());
		pessoaDTO.setFoto("c/d/teste.jpeg");
		
		Mockito.when(multipartFile.getName()).thenReturn("perfil_1_05Mb.pdf");
		
		pessoaDTO.setArquivoFoto(multipartFile);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMessage().equalsIgnoreCase("Formato de imagem inválido. (Formatos permitidos jpg, png, gif e jpeg)"));
		}
	}
	
	@Test
	public void tentarCriarPessoaComFotoDePerfilMaiorQue1MbTest(){
		try {
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome("Testador da Silva");
			pessoaDTO.setCpf("04653183120");
			pessoaDTO.setEmail("etste@email.com");
			pessoaDTO.setDataNascimento(new Date());
			pessoaDTO.setFoto("c/d/teste.jpeg");
			
			Mockito.when(multipartFile.getName()).thenReturn("perfil_1_05Mb.jpg");
			
			pessoaDTO.setArquivoFoto(multipartFile);
			
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMessage().equalsIgnoreCase("O tamanho máximo permitido da imagem é de 1MB."));
		}
	}
	
	@Test
	public void tentarCriarPessoaSemEmailTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail(null);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("E-mail - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComEmailInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail("teste.test.com");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O e-mail informado é inválido."));
		}
	}
	
	@Test
	public void tentarCriarPessoaComEmailMaiorQue400Test(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setEmail("AsJurgenHollermentionedinhispostannouncingthereleaseofSpring31M2theSpringTestContextFrameworkhasbeenoverhauledtoprovidefirstclasstestingsupportfor@ConfigurationclassesandenvironmentprofilesInthispostI’llfirstwalkyouthroughsomeexamplesthatdemonstratethesenewtestingfeatures.IllthencoversomeofthenewextensionpointsintheTestContextframeworkthatmakethesenewfeaturespossibleadasdaasd.basdasdasdasdasdasdasa");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("E-mail - O tamanho máximo permitido de caracteres é 400."));
		}
	}
	
	@Test
	public void tentarCriarPessoaSemDataDeNascimentoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail("teste@email.com");
		pessoaDTO.setDataNascimento(null);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Data de Nascimento - Preenchimento Obrigatório."));
		}
	}
	
	
	@Test
	public void tentarAtualizarPessoaSemNomeTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome(null);
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Nome - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComNomeInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("12312");
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O nome informado é inválido."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComNomeMaiorQue150Test(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("It is important to be able to perform some integration testing without requiring deployment to your application server or connecting to other enterprise infrastructure.");
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Nome - O tamanho máximo permitido de caracteres é 150."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaSemCpfTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf(null);
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("CPF - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComCpfInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("04653183122");
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O CPF informado é inválido."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComFormatoDeImagemDePerfilInvalidaTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("04653183120");
		pessoaDTO.setEmail("etste@email.com");
		pessoaDTO.setDataNascimento(new Date());
		pessoaDTO.setFoto("c/d/teste.jpeg");
		
		Mockito.when(multipartFile.getName()).thenReturn("perfil_1_05Mb.pdf");
		
		pessoaDTO.setArquivoFoto(multipartFile);
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMessage().equalsIgnoreCase("Formato de imagem inválido. (Formatos permitidos jpg, png, gif e jpeg)"));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComFotoDePerfilMaiorQue1MbTest(){
		try {
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome("Testador da Silva");
			pessoaDTO.setCpf("04653183120");
			pessoaDTO.setEmail("etste@email.com");
			pessoaDTO.setDataNascimento(new Date());
			pessoaDTO.setFoto("c/d/teste.jpeg");
			
			Mockito.when(multipartFile.getName()).thenReturn("perfil_1_05Mb.jpg");
			
			pessoaDTO.setArquivoFoto(multipartFile);
			
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMessage().equalsIgnoreCase("O tamanho máximo permitido da imagem é de 1MB."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaSemEmailTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail(null);
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("E-mail - Preenchimento Obrigatório."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComEmailInvalidoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail("teste.test.com");
		try {
			service.criar(pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("O e-mail informado é inválido."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaComEmailMaiorQue400Test(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setEmail("AsJurgenHollermentionedinhispostannouncingthereleaseofSpring31M2theSpringTestContextFrameworkhasbeenoverhauledtoprovidefirstclasstestingsupportfor@ConfigurationclassesandenvironmentprofilesInthispostI’llfirstwalkyouthroughsomeexamplesthatdemonstratethesenewtestingfeatures.IllthencoversomeofthenewextensionpointsintheTestContextframeworkthatmakethesenewfeaturespossibleadasdaasd.basdasdasdasdasdasdasa");
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("E-mail - O tamanho máximo permitido de caracteres é 400."));
		}
	}
	
	@Test
	public void tentarAtualizarPessoaSemDataDeNascimentoTest(){
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome("Testador da Silva");
		pessoaDTO.setCpf("046.531.831-20");
		pessoaDTO.setEmail("teste@email.com");
		pessoaDTO.setDataNascimento(null);
		try {
			service.atualizar(1, pessoaDTO);
		} catch (DadoInvalidoException e) {
			Assert.assertEquals(true, e.getMensagens().contains("Data de Nascimento - Preenchimento Obrigatório."));
		}
	}
}
