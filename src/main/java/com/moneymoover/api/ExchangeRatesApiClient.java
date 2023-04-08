package com.moneymoover.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The ExchangeRatesApiClient class creates an instance of the Retrofit client used to make API requests.
 */
public class ExchangeRatesApiClient {
    private static final String BASE_URL = "https://openexchangerates.org/api/";
    private static Retrofit retrofit;

    /**
     * Creates a Retrofit instance for API requests.
     * If the instance is null, creates a new instance.
     *
     * @return The Retrofit instance.
     */
    public static Retrofit getClient () {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Defines the endpoints for the OpenExchangeRates API.
     *
     * @return An instance of the ExchangeRatesApi interface.
     */
    public static ExchangeRatesApi getExchangeRatesApi () {
        return getClient().create(ExchangeRatesApi.class);
    }
}
