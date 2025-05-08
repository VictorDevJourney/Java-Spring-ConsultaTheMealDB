package br.com.DesafioSpring.Receitas;

import br.com.DesafioSpring.Receitas.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReceitasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReceitasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
