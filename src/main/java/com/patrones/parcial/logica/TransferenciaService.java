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

    /*private final CuentaService cuentaService;

    public TransferenciaService(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    public void ejecutarTransferencias() {


        Long cuentaABCId = 1L;
        Long cuentaCBDId = 2L;
        double montoTransferencia = 5.0;
        int totalHilos = 30;


        for (int i = 0; i < totalHilos; i++) {
            new Thread(() -> {
                while (true) {
                    try {

                        cuentaService.transferir(cuentaABCId, cuentaCBDId, montoTransferencia);


                        CuentaORM cuentaABC = cuentaService.obtenerCuentaPorId(cuentaABCId);
                        CuentaORM cuentaCBD = cuentaService.obtenerCuentaPorId(cuentaCBDId);

                        System.out.println("Transferencia realizada:");
                        System.out.println("Cuenta ABC (ID: " + cuentaABCId + ") - Saldo: " + cuentaABC.getMonto());
                        System.out.println("Cuenta CBD (ID: " + cuentaCBDId + ") - Saldo: " + cuentaCBD.getMonto());
                        System.out.println("------------------------");


                        if (cuentaABC.getMonto() <= 0) {
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println("Error en la transferencia: " + e.getMessage());
                        break;
                    }
                }
            }).start();
        }
    }*/

}







