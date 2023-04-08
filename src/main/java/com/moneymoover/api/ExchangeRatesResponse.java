package com.moneymoover.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Represents the response received from the API.
 */
public class ExchangeRatesResponse {
    @SerializedName("base")
    private String baseCurrency;

    @SerializedName("rates")
    private Map<String, Double> exchangeRates;

    /**
     * Accessor to the map of exchange rates for each currency relative to the base currency (USD).
     *
     * @return the exchange rates map
     */
    public Map<String, Double> getExchangeRates () {
        return exchangeRates;
    }
}
