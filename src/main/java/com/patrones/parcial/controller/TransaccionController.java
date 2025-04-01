package com.patrones.parcial.controller;

import com.patrones.parcial.logica.TransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransferenciaService transferenciaService;

    @PostMapping("/transferir")
    public synchronized String transferir(@RequestParam Long origen, @RequestParam Long destino, @RequestParam double monto) {
        boolean success = transferenciaService.transferir(origen, destino, monto);
        return success ? "Transferencia exitosa" : "Saldo insuficiente";
    }
}