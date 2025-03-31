package com.patrones.parcial.db.jpa;


import com.patrones.parcial.db.orm.TransaccionORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionJPA extends JpaRepository<TransaccionORM, Long> {
}
