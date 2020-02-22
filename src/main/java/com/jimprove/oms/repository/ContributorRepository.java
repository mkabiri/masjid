package com.jimprove.oms.repository;

import com.jimprove.oms.domain.Contributor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Contributor entity.
 */
@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    @Query(value = "select distinct contributor from Contributor contributor left join fetch contributor.memberships",
        countQuery = "select count(distinct contributor) from Contributor contributor")
    Page<Contributor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct contributor from Contributor contributor left join fetch contributor.memberships")
    List<Contributor> findAllWithEagerRelationships();

    @Query("select contributor from Contributor contributor left join fetch contributor.memberships where contributor.id =:id")
    Optional<Contributor> findOneWithEagerRelationships(@Param("id") Long id);

}
