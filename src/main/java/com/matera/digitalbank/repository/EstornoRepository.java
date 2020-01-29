package com.matera.digitalbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matera.digitalbank.entity.Estorno;

public interface EstornoRepository extends JpaRepository<Estorno, Long> {

	Optional<Estorno> findByLancamentoOriginal_Id(Long idLancamentoOriginal);

	Optional<Estorno> findByLancamentoEstorno_Id(Long idLancamento);

}
