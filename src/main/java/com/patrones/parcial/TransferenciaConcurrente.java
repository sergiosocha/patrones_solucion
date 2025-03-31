package com.patrones.parcial;

import com.patrones.parcial.logica.TransferenciaService;
import org.springframework.boot.CommandLineRunner;

public class TransferenciaConcurrente implements CommandLineRunner {

    private final TransferenciaService transferenciaService;

    public TransferenciaConcurrente(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }


    @Override
    public void run(String... args) throws Exception {
        transferenciaService.ejecutarTransferencias();


    }
}
