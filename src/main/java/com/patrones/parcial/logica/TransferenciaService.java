package com.patrones.parcial.logica;




import com.patrones.parcial.db.orm.CuentaORM;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class TransferenciaService {

    private final CuentaService cuentaService;

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
    }

}







