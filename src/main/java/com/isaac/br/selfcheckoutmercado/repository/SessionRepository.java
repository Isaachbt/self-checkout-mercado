package com.isaac.br.selfcheckoutmercado.repository;

import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<CheckoutSession,Integer> {
}
