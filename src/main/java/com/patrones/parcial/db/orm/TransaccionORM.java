package com.patrones.parcial.db.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "transaccion")
@Entity
@Data
@NoArgsConstructor
public class TransaccionORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;


    @ManyToOne
    @JoinColumn(name = "origen", referencedColumnName = "id_cuenta", nullable = false)
    private CuentaORM cuentaOrigen;


    @ManyToOne
    @JoinColumn(name = "destino", referencedColumnName = "id_cuenta", nullable = false)
    private CuentaORM cuentaDestino;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
