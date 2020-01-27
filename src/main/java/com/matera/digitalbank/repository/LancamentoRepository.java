package com.matera.digitalbank.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matera.digitalbank.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findByConta_IdOrderByIdDesc(Long idConta);

	Optional<Lancamento> findByIdAndConta_Id(Long idLancamento, Long idConta);

	@Query("SELECT l " +
		   "FROM   Lancamento l " +
		   "WHERE  TRUNC(l.dataHora) BETWEEN :dataInicial AND NVL(:dataFinal, :dataInicial) " +
		   "ORDER BY l.id DESC")
	List<Lancamento> consultaLancamentosPorPeriodo(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);

}
