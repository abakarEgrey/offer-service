package com.woldplay.fis.offerservice;

import com.woldplay.fis.offerservice.Utils.Currency;
import com.woldplay.fis.offerservice.entities.Offer;
import com.woldplay.fis.offerservice.entities.Price;
import com.woldplay.fis.offerservice.repository.OfferRepository;
import com.woldplay.fis.offerservice.service.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OfferServiceApplication implements CommandLineRunner {

	@Autowired
	private OfferRepository offerRepository;

	private IOfferService offerService;

	public static void main(String[] args) {
		SpringApplication.run(OfferServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// create Price
		Price price = new Price(130.0, Currency.GBP);
		Price price1 = new Price(200.0, Currency.EUR);
		Price price2 = new Price(300.0, Currency.USD);

		// create offer
		Offer offer = new Offer(30L, price);
		long timestamp = offer.getTimeout() * 1000 + System.currentTimeMillis();
		offer.setTimeout(timestamp);

		Offer offer1 = new Offer(2200L, price1);
		long timestamp1 = offer1.getTimeout() * 1000 + System.currentTimeMillis();
		offer1.setTimeout(timestamp1);

		Offer offer2 = new Offer(3200L, price2);
		long timestamp2 = offer2.getTimeout() * 1000 + System.currentTimeMillis();
		offer2.setTimeout(timestamp2);

		// save all data
		offerRepository.save(offer);
		offerRepository.save(offer1);
		offerRepository.save(offer2);
	}
}
