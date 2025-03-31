package com.patrones.parcial.db.jpa;

import com.patrones.parcial.db.orm.CuentaORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaJPA extends JpaRepository<CuentaORM, Long> {
}
