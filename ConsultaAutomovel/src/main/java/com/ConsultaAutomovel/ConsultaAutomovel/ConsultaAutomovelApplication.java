package com.ConsultaAutomovel.ConsultaAutomovel;

import com.ConsultaAutomovel.ConsultaAutomovel.Service.ConsumoAPI;

import com.ConsultaAutomovel.ConsultaAutomovel.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaAutomovelApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaAutomovelApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();

	}
}

