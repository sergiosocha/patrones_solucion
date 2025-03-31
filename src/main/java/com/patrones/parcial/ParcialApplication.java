package com.patrones.parcial;

import com.patrones.parcial.logica.CuentaService;
import com.patrones.parcial.logica.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ParcialApplication implements CommandLineRunner {

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private final TransferenciaService transferenciaService;

	public ParcialApplication(CuentaService cuentaService, TransferenciaService transferenciaService) {
		this.cuentaService = cuentaService;
		this.transferenciaService = transferenciaService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ParcialApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		cuentaService.crearCuentasIniciales();

		transferenciaService.ejecutarTransferencias();


	}
}