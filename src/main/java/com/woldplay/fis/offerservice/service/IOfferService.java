package com.woldplay.fis.offerservice.service;

import com.woldplay.fis.offerservice.entities.Offer;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service to allow to merchant to create, get, modify and delete a offer .
 */
public interface IOfferService {

    /**
     * Get all offers registered on data base
     * @return  all offers registered on data base
     */
    public List<Offer> findAll();

    /**
     * Register a new offer
     * @param offer     the offer to register
     * @return          return response entity after registration
     */
    public ResponseEntity save(Offer offer);

    /**
     * Update an existing offer
     * @param offerId       the offer id
     * @param offer         the new offer
     * @return              the updated offer response
     */
    public ResponseEntity update(Long offerId, Offer offer);

    /**
     * Delete an offer
     * @param offerId       the offer id
     * @return              the response entity
     */
    public ResponseEntity delete(Long offerId);

    /**
     * Cancel the timeout
     * @param offerId       the offer id
     * @return              200 if successfully canceled
     */
    public ResponseEntity cancelTimeout(Long offerId);

    /**
     * Find an offer by id
     * @param offerId       the offer Id
     * @return              the offer found
     */
    public Offer findById(Long offerId);
}
