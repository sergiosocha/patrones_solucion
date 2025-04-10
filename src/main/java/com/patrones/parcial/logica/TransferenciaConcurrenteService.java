package com.patrones.parcial.logica;


import com.newrelic.api.agent.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class TransferenciaConcurrenteService {

    private final TransferenciaService transferenciaService;
    private final CuentaService cuentaService;

    @Transactional
    @Trace(dispatcher = true)
    public synchronized void ejecutarTransferenciasConcurrentes() {
        ExecutorService executor = Executors.newFixedThreadPool(30);

        Long idCuentaABC = 1L;
        Long idCuentaCBD = 2L;
        double montoTransferencia = 5.0;

        while (cuentaService.obtenerSaldo(idCuentaABC) > 0) {
            executor.submit(() -> transferenciaService.transferir(idCuentaABC, idCuentaCBD, montoTransferencia));
        }

        executor.shutdown();
    }
}
