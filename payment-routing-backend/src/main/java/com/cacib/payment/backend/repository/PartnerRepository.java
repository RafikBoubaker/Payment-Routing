package com.cacib.payment.backend.repository;

import com.cacib.payment.backend.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {


}