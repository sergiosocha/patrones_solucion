package com.patrones.parcial.controller;

import com.patrones.parcial.logica.TransferenciaConcurrenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concurrencia")
@RequiredArgsConstructor
public class TransferenciaConcurrenteController {

    private final TransferenciaConcurrenteService transferenciaConcurrenteService;

    @PostMapping("/iniciar")
    public synchronized String iniciarTransferencias() {
        transferenciaConcurrenteService.ejecutarTransferenciasConcurrentes();
        return "Transferencias concurrentes iniciadas";
    }
}