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

    public double obtenerSaldo(Long idCuenta) {
        return cuentaJPA.findById(idCuenta)
                .map(CuentaORM::getMonto)
                .orElse(0.0);
    }

    public List<CuentaORM> obtenerTodasLasCuentas() {
        return cuentaJPA.findAll();
    }
    /*
    public CuentaService(CuentaJPA cuentaRepository) {
        this.cuentaJPA = cuentaRepository;
    }

    public List<CuentaORM> obtenerTodas() {
        return cuentaJPA.findAll();
    }

    public CuentaORM obtenerCuentaPorId(Long cuentaId) throws Exception {
        return cuentaJPA.findById(cuentaId)
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));
    }

    public void actualizarCuenta(CuentaORM cuenta) {
        cuentaJPA.save(cuenta);
    }

    */


    //Solucion 1
    /*private final CuentaJPA cuentajpa;
    private final TransaccionJPA transaccionjpa;

    public CuentaService(CuentaJPA cuentajpa, TransaccionJPA transaccionjpa) {
        this.cuentajpa = cuentajpa;
        this.transaccionjpa = transaccionjpa;
    }

    @Transactional
    public void crearCuentasIniciales() {

        if (cuentajpa.count() == 0) {
            CuentaORM cuentaABC = new CuentaORM();
            cuentaABC.setMonto(10000.0);
            cuentajpa.save(cuentaABC);


            CuentaORM cuentaCBD = new CuentaORM();
            cuentaCBD.setMonto(10000.0);
            cuentajpa.save(cuentaCBD);

            System.out.println("Cuentas iniciales creadas con éxito");
        } else {
            System.out.println("Cuentas ya existen");
        }
    }

    @Transactional
    public void transferir(Long origenId, Long destinoId, double cantidad) {

        CuentaORM cuentaOrigen = cuentajpa.findById(origenId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada"));

        CuentaORM cuentaDestino = cuentajpa.findById(destinoId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de destino no encontrada"));

        if (cuentaOrigen.getMonto() < cantidad) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta de origen");
        }


        cuentaOrigen.setMonto(cuentaOrigen.getMonto() - cantidad);
        cuentaDestino.setMonto(cuentaDestino.getMonto() + cantidad);


        cuentajpa.save(cuentaOrigen);
        cuentajpa.save(cuentaDestino);


        TransaccionORM transaccion = new TransaccionORM();
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);
        transaccion.setMonto(cantidad);
        transaccion.setTimestamp(LocalDateTime.now());

        transaccionjpa.save(transaccion);
    }

    @Transactional
    public CuentaORM obtenerCuentaPorId(Long idCuenta) {
        return cuentajpa.findById(idCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    }
       */


}
