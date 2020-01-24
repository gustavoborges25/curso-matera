package com.matera.digitalbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matera.digitalbank.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

//	Optional<Cliente> findByCpf(String cpf);

//	@Query("select c from Cliente c where c.cpf = :cpf")
//	Optional<Cliente> buscaPorCpf(@Param("cpf") String cpf);

	@Query(value = "select c.* from db_cliente c where c.cpf = :cpf", nativeQuery = true)
	Optional<Cliente> buscaPorCpf(@Param("cpf") String cpf);
}
