package com.woldplay.fis.offerservice.service;

import com.woldplay.fis.offerservice.entities.Offer;
import com.woldplay.fis.offerservice.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Service
public class OfferServiceImpl implements IOfferService {

    /**
     * Long max value is used to cancel an offer
     */
        private static final long LONG_MAX_VALUE = Long.MAX_VALUE;

    @Autowired
    private OfferRepository offerRepository;

    /**
     * Get all offers registered on data base
     *
     * @return all offers registered on data base
     */
    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    @Override
    public List<Offer> findAll() {
        long currentTimestamp = System.currentTimeMillis();
        return offerRepository.findByTimeoutGreaterThan(currentTimestamp);
    }

    /**
     * Register a new offer
     *
     * @param offer the offer to register
     * @return return response entity after registration
     */
    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    @Override
    public ResponseEntity save(@RequestBody Offer offer) {
        long timeout = offer.getTimeout() * 1000 + System.currentTimeMillis();
        offer.setTimeout(timeout);
        try {
            offerRepository.save(offer);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Update an existing offer
     *
     * @param offerId the offer id
     * @param offer   the new offer
     * @return the updated offer response
     */
    @RequestMapping(value = "/offers/{offerId}", method = {RequestMethod.GET, RequestMethod.PUT})
    @Override
    public ResponseEntity update(@PathVariable Long offerId, @RequestBody Offer offer) {
        Offer registeredOffer = null;
        try {
            registeredOffer = offerRepository.findById(offerId).get();
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        registeredOffer.setId(offerId);

        if (offer.getTimeout() != null) {
            registeredOffer.setTimeout(offer.getTimeout() * 1000 + System.currentTimeMillis());
        }

        if (offer.getPrice() != null) {
            registeredOffer.setPrice(offer.getPrice());
        }

        try {
            offerRepository.save(registeredOffer);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Delete an offer
     *
     * @param offerId the offer id
     * @return the response entity
     */
    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.DELETE)
    @Override
    public ResponseEntity delete(@PathVariable Long offerId) {
        try {
            offerRepository.deleteById(offerId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Cancel the timeout
     *
     * @param offerId the offer id
     * @return true if the timeout is successfully canceled
     */
    @RequestMapping(value = "/offers/cancel/{offerId}", method = {RequestMethod.GET, RequestMethod.PUT})
    @Override
    public ResponseEntity cancelTimeout(@PathVariable Long offerId) {
        Offer registeredOffer = null;
        try {
            registeredOffer = offerRepository.findById(offerId).get();
            registeredOffer.setTimeout(LONG_MAX_VALUE);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try {
            offerRepository.save(registeredOffer);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Find an offer by id
     *
     * @param offerId the offer Id
     * @return the offer found
     */
    @RequestMapping(value = "/offers/{offerId}", method = RequestMethod.GET)
    @Override
    public Offer findById(@PathVariable Long offerId) {
        Offer registeredOffer = null;
        try {
            registeredOffer = offerRepository.findById(offerId).get();
        } catch (Exception e) {
            return registeredOffer;
        }
        return registeredOffer;
    }
}
