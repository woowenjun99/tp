package seedu.duke.api;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import seedu.duke.Currency;
import seedu.duke.ui.Ui;
import seedu.duke.constants.ErrorMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRates {

    private static final String APP_ID = "1349651eb52b4f85a5ced93d579652ea";
    private static final String BASE_CURRENCY = "USD";

    private static Map<String, Double> exchangeRatesMap;
    private static HashMap<Currency, BigDecimal> savedMap;

    /**
     * Fetches exchange rates and saves it in savedMap
     */
    public ExchangeRates() {
        fetchExchangeRates(APP_ID, BASE_CURRENCY);
    }

    /**
     * Fetches the exchange rates with the chosen base currency and API token
     * @param appId        The API token
     * @param baseCurrency The base currency, always USD
     */
    private static void fetchExchangeRates(String appId, String baseCurrency) {
        Ui ui = new Ui();
        ExchangeRatesApi api = ExchangeRatesApiClient.getExchangeRatesApi();
        Call<ExchangeRatesResponse> call = api.getLatestExchangeRates(appId, baseCurrency);
        call.enqueue(new Callback<ExchangeRatesResponse>() {
            @Override
            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                if (response.isSuccessful()) {
                    ExchangeRatesResponse rates = response.body();
                    exchangeRatesMap = rates.getExchangeRates();
                    saveMap(exchangeRatesMap);
                } else {
                    ui.printMessage(ErrorMessage.RESPONSE_CODE_OUT_OF_BOUNDS);
                }
            }

            @Override
            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
                ui.printMessage(ErrorMessage.NETWORK_OR_UNEXPECTED_ERROR);
            }
        });
    }

    /**
     * Saves requested and filtered map to savedMap
     * @param exchangeRatesMap The rates map to save before type conversion
     */
    private static void saveMap(Map<String, Double> exchangeRatesMap) {
        HashMap<Currency, BigDecimal> filteredMap = filterMap(exchangeRatesMap);
        savedMap = filteredMap;
    }

    /**
     * Filters out the exchange rates for unsupported currencies.
     * Converts the String, Double map to a Currency, BigDecimal hashmap
     * for supported currencies.
     * @param exchangeRatesMap The map to filter and convert
     * @return The filtered and converted map
     */
    private static HashMap<Currency, BigDecimal> filterMap(Map<String, Double> exchangeRatesMap) {
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

    // Accessor method for saved map
    public static HashMap<Currency, BigDecimal> getExchangeRates() {
        return savedMap;
    }
}
