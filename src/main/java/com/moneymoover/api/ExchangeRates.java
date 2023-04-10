package com.moneymoover.api;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moneymoover.Currency;
import com.moneymoover.constants.ErrorMessage;
import com.moneymoover.constants.Message;
import com.moneymoover.ui.Ui;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRates {

    private static final String APP_ID = "38226e471c1740d084a22b5473111529";
    private static final String BASE_CURRENCY = "USD";
    private static final Logger logger = Logger.getLogger("logger");

    private static Map<String, Double> exchangeRatesMap;
    private static HashMap<Currency, BigDecimal> savedMap;

    /**
     * Fetches exchange rates and saves it in savedMap
     */
    public ExchangeRates () {
        fetchExchangeRates(APP_ID, BASE_CURRENCY);
    }

    /**
     * Fetches the exchange rates with the chosen base currency and API token
     *
     * @param appId        The API token
     * @param baseCurrency The base currency, always USD
     */
    private static void fetchExchangeRates (String appId, String baseCurrency) {
        Ui ui = new Ui();
        ExchangeRatesApi api = ExchangeRatesApiClient.getExchangeRatesApi();
        Call<ExchangeRatesResponse> call = api.getLatestExchangeRates(appId, baseCurrency);
        call.enqueue(new Callback<ExchangeRatesResponse>() {
            @Override
            public void onResponse (Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                if (response.isSuccessful()) {
                    ExchangeRatesResponse rates = response.body();
                    exchangeRatesMap = rates.getExchangeRates();
                    assert exchangeRatesMap != null;
                    saveMap(exchangeRatesMap);
                    ui.printMessage(Message.API_INITIALIZED.getMessage());
                    logger.log(Level.FINE, "Live exchange rates initialized");
                } else {
                    ui.printMessage(ErrorMessage.RESPONSE_CODE_OUT_OF_BOUNDS);
                    populateRates(ui);
                    logger.log(Level.SEVERE, "API call unsuccessful, initializing saved rates");
                }
            }

            @Override
            public void onFailure (Call<ExchangeRatesResponse> call, Throwable t) {
                ui.printMessage(ErrorMessage.NETWORK_OR_UNEXPECTED_ERROR);
                populateRates(ui);
                logger.log(Level.SEVERE, "API call unsuccessful, initializing saved rates");
            }
        });
    }

    /**
     * Saves requested and filtered map to savedMap
     *
     * @param exchangeRatesMap The rates map to save before type conversion
     */
    private static void saveMap (Map<String, Double> exchangeRatesMap) {
        HashMap<Currency, BigDecimal> filteredMap = filterMap(exchangeRatesMap);
        savedMap = filteredMap;
    }

    /**
     * Filters out the exchange rates for unsupported currencies.
     * Converts the String, Double map to a Currency, BigDecimal hashmap
     * for supported currencies.
     *
     * @param exchangeRatesMap The map to filter and convert
     * @return The filtered and converted map
     */
    private static HashMap<Currency, BigDecimal> filterMap (Map<String, Double> exchangeRatesMap) {
        HashMap<Currency, BigDecimal> convertedMap = new HashMap<>();
        for (Currency currency : Currency.values()) {
            String currencyString = currency.toString();
            if (exchangeRatesMap.containsKey(currencyString)) {
                Double exchangeRate = exchangeRatesMap.get(currencyString);
                BigDecimal exchangeRateDecimal = BigDecimal.valueOf(exchangeRate);
                convertedMap.put(currency, exchangeRateDecimal);
            }
        }
        assert convertedMap.size() == Currency.values().length;
        return convertedMap;
    }

    /*
     * If API doesn't load, manually initializes slightly outdated exchange rates.
     * correct as of 2023-03-15 18:15 SGT according to
     * <a href="https://www.xe.com/currencyconverter/convert">https://www.xe.com/currencyconverter/convert</a>
     */
    public static void populateRates (Ui ui) {
        savedMap = new HashMap<Currency, BigDecimal>();
        ui.printMessage(ErrorMessage.OUTDATED_RATES);
        savedMap.put(Currency.SGD, BigDecimal.valueOf(1.000f));
        savedMap.put(Currency.MYR, BigDecimal.valueOf(3.3295727f));
        savedMap.put(Currency.USD, BigDecimal.valueOf(0.74265183f));
        savedMap.put(Currency.IDR, BigDecimal.valueOf(11442.776f));
        savedMap.put(Currency.JPY, BigDecimal.valueOf(99.414735f));
        savedMap.put(Currency.THB, BigDecimal.valueOf(25.689173f));
        savedMap.put(Currency.CNY, BigDecimal.valueOf(5.1222378f));
        savedMap.put(Currency.GBP, BigDecimal.valueOf(0.61293137f));
        savedMap.put(Currency.EUR, BigDecimal.valueOf(0.69567172f));
        savedMap.put(Currency.KRW, BigDecimal.valueOf(974.90478f));
        savedMap.put(Currency.VND, BigDecimal.valueOf(17490.625f));
    }

    // Accessor method for saved map
    public static HashMap<Currency, BigDecimal> getExchangeRates () {
        return savedMap;
    }
}
