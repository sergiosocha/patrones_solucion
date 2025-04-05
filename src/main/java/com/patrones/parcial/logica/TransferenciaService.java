package com.patrones.parcial.logica;





import com.patrones.parcial.db.jpa.CuentaJPA;
import com.patrones.parcial.db.jpa.TransaccionJPA;
import com.patrones.parcial.db.orm.CuentaORM;
import com.patrones.parcial.db.orm.TransaccionORM;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import com.newrelic.api.agent.Trace;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final CuentaJPA cuentaJPA;
    private final TransaccionJPA transaccionJPA;

    private static final Logger log = LoggerFactory.getLogger(TransferenciaService.class);


    @Transactional
    @Trace(dispatcher = true)
    public synchronized boolean transferir(Long idOrigen, Long idDestino, double monto) {
        log.info("Intentando transferir {} de cuenta {} a cuenta {}", monto, idOrigen, idDestino);

        CuentaORM cuentaOrigen = cuentaJPA.findById(idOrigen).orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));
        CuentaORM cuentaDestino = cuentaJPA.findById(idDestino).orElseThrow(() -> new RuntimeException("Cuenta destino no encontrada"));

        if (cuentaOrigen.getMonto() < monto) {
            return false;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        cuentaOrigen.setMonto(cuentaOrigen.getMonto() - monto);
        cuentaDestino.setMonto(cuentaDestino.getMonto() + monto);

        cuentaJPA.save(cuentaOrigen);
        cuentaJPA.save(cuentaDestino);


        TransaccionORM transaccion = new TransaccionORM();
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);
        transaccion.setMonto(monto);
        transaccion.setTimestamp(LocalDateTime.now());

        transaccionJPA.save(transaccion);
        log.info("Transferencia exitosa: {} → {} monto: {}", idOrigen, idDestino, monto);
        return true;
    }

}







