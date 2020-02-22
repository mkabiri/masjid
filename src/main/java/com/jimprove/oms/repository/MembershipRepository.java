package com.jimprove.oms.repository;

import com.jimprove.oms.domain.Membership;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Membership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
