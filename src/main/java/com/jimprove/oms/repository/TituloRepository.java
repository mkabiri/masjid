package com.jimprove.oms.repository;

import com.jimprove.oms.domain.Titulo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Titulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long>, JpaSpecificationExecutor<Titulo> {

}
