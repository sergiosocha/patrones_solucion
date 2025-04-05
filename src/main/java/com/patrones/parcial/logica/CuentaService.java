package com.patrones.parcial.logica;

import com.patrones.parcial.db.jpa.CuentaJPA;
import com.patrones.parcial.db.jpa.TransaccionJPA;
import com.patrones.parcial.db.orm.CuentaORM;
import com.patrones.parcial.db.orm.TransaccionORM;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaJPA cuentaJPA;

    @PostConstruct
    @Transactional
    public void inicializarCuentas() {
        if (cuentaJPA.count() == 0) {
            CuentaORM cuentaABC = new CuentaORM();
            cuentaABC.setMonto(10000.0);
            cuentaJPA.save(cuentaABC);

            CuentaORM cuentaCBD = new CuentaORM();
            cuentaCBD.setMonto(10000.0);
            cuentaJPA.save(cuentaCBD);
        }
    }

    @Transactional
    public double obtenerSaldo(Long idCuenta) {
        return cuentaJPA.findById(idCuenta)
                .map(CuentaORM::getMonto)
                .orElse(0.0);
    }

    @Transactional
    public List<CuentaORM> obtenerTodasLasCuentas() {
        return cuentaJPA.findAll();
    }
    


}
