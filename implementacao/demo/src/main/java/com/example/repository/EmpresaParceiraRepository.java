package com.meritosystem.repository;

import com.meritosystem.model.EmpresaParceira;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {
    Optional<EmpresaParceira> findByCnpj(String cnpj);
    Optional<EmpresaParceira> findByEmail(String email);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
}