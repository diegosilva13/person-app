package br.com.pessoa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@ActiveProfiles("unit_test")
public class PessoaServiceApplicationTests {
	
	@Test
	public void contextLoads() {
	}
}
