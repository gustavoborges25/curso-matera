package com.matera.digitalbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matera.digitalbank.entity.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByNumeroAgenciaAndNumeroConta(Integer numeroAgencia, Long numeroConta);

	Optional<Conta> findByNumeroConta(Long numeroConta);

	Optional<Conta> findByCliente_Id(Long idCliente);

}
