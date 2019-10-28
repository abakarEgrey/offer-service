package com.woldplay.fis.offerservice.service;

import com.woldplay.fis.offerservice.OfferServiceApplication;
import com.woldplay.fis.offerservice.Utils.Currency;
import com.woldplay.fis.offerservice.entities.Offer;
import com.woldplay.fis.offerservice.entities.Price;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OfferServiceApplication.class})
@WebAppConfiguration
public class OfferServiceTest {

    private final static String baseURL = "http://localhost:8080";
    private final static String offerURI= "/offers";

    @Autowired
    private IOfferService offerService;

    @Before
    public void setUp() throws Exception {
        // create Price
        Price price = new Price(130.0, Currency.GBP);
        Price price1 = new Price(200.0, Currency.EUR);
        Price price2 = new Price(300.0, Currency.USD);

        // create offer
        Offer offer = new Offer(3330L, price);

        Offer offer1 = new Offer(2200L, price1);

        Offer offer2 = new Offer(3200L, price2);

        offerService.save(offer);
        offerService.save(offer1);
        offerService.save(offer2);
    }

    @Test
    public void testFindAll() {
        List<Offer> offers = offerService.findAll();

        assertNotNull(offers);
        assertTrue(offers.size() > 0);
    }

    @Test
    public void testSave() {
        List<Offer> offers = offerService.findAll();
        Price price = new Price(720.0, Currency.GBP);
        Offer offer = new Offer(500L, price);

        ResponseEntity responseEntity = offerService.save(offer);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdate() {
        Price price = new Price(450.0, Currency.JPY);
        Offer offer = new Offer(340L, price);

        ResponseEntity responseEntity = offerService.update(2L, offer);

        Offer registeredOffer = offerService.findById(2L);
        Price registeredPrice = registeredOffer.getPrice();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotEquals(registeredOffer.getTimeout(), offer.getTimeout());
        assertNotNull(registeredPrice);
        assertNotEquals(registeredPrice.getCurrency(), price.getCurrency());
    }

    @Test
    public void testDelete() {
        List<Offer> offers = offerService.findAll();

        ResponseEntity responseEntity = offerService.delete(3L);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(offers.size() -  1L, offerService.findAll().size());
    }

    @Test
    public void testTimeout() throws InterruptedException {
        Price price = new Price(150.0, Currency.GBP);
        Offer offer = new Offer(30L, price);
        offerService.save(offer);
        List<Offer> offers = offerService.findAll();
        // the first offer have a timeout = 30 seconds
        Thread.sleep(31000);

        List<Offer> offerList = offerService.findAll();
        assertEquals(offerList.size(), offers.size() - 1);
    }

    @Test
    public void testCancelTimeout() throws InterruptedException {
        Price price = new Price(692.0, Currency.USD);
        Offer offer = new Offer(15L, price);
        offerService.save(offer);
        List<Offer> offers = offerService.findAll();

        // the first offer have a timeout = 10 seconds
        Thread.sleep(10000);
        List<Offer> offerList = offerService.findAll();
        // cancel the timeout
        ResponseEntity responseEntity = offerService.cancelTimeout(offers.size() * 1L);
        long registeredTimeout = offerService.findById(offers.size() * 1L).getTimeout();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(offerList.size(), offers.size());
        assertEquals(registeredTimeout, Long.MAX_VALUE);
    }
}