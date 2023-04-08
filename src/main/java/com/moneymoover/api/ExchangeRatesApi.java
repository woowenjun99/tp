package com.moneymoover.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface defines a method for making requests to the Open Exchange Rates API
 */
public interface ExchangeRatesApi {

    /**
     * Makes a GET request to the API to retrieve the latest exchange rates.
     * For info on latest.json, see https://docs.openexchangerates.org/reference/latest-json
     * Returns a Call object containing the response.
     *
     * @param appId        the API token used to access the API
     * @param baseCurrency the base currency, always USD
     * @return a Call object containing the response
     */
    @GET("latest.json")
    Call<ExchangeRatesResponse> getLatestExchangeRates (@Query(
            "app_id") String appId,
                                                        @Query("base") String baseCurrency
    );
}
