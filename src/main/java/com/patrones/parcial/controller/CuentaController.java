package com.patrones.parcial.controller;

import com.patrones.parcial.db.orm.CuentaORM;
import com.patrones.parcial.logica.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public List<CuentaORM> obtenerTodasLasCuentas() {
        return cuentaService.obtenerTodasLasCuentas();
    }

    @GetMapping("/{id}")
    public double obtenerSaldo(@PathVariable Long id) {
        return cuentaService.obtenerSaldo(id);
    }


}