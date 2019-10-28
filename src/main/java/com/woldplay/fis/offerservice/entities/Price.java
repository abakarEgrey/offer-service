package com.woldplay.fis.offerservice.entities;

import com.woldplay.fis.offerservice.Utils.Currency;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Price implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double value;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    /**
     * Constructor
     */
    public Price() {
    }

    /**
     * Constructor
     * @param value     the value
     * @param currency  the money code
     */
    public Price(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }
}
