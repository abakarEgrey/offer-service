package com.woldplay.fis.offerservice.repository;

import com.woldplay.fis.offerservice.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OfferRepository extends JpaRepository<Offer, Long> {

    public List<Offer> findByTimeoutGreaterThan(@Param("timeout") Long timeout);
}
