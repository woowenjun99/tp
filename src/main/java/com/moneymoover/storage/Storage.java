package com.moneymoover.storage;


public class Storage {
    private final String currency;
    private final long value;

    public Storage (String currency, long value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency () {
        return currency;
    }

    public long getValue () {
        return value;
    }
}
