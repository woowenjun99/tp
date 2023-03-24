package seedu.duke.api;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import seedu.duke.Currency;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRates {

    private static final String APP_ID = "1349651eb52b4f85a5ced93d579652ea";
    private static final String BASE_CURRENCY = "USD";

    private static Map<String, Double> exchangeRatesMap;
    private static HashMap<Currency, BigDecimal> savedMap;

    public ExchangeRates() {
        fetchExchangeRates(APP_ID, BASE_CURRENCY);
    }

    private static void fetchExchangeRates(String appId, String baseCurrency) {
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
                    System.out.println("Failure");
                }
            }

            @Override
            public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
                System.out.println("Failure");
            }
        });
    }

    private static void saveMap(Map<String, Double> exchangeRatesMap) {
        HashMap<Currency, BigDecimal> filteredMap = filterMap(exchangeRatesMap);
        savedMap = filteredMap;
    }

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
        return convertedMap;
    }

    public static HashMap<Currency, BigDecimal> getExchangeRates() {
        return savedMap;
    }
}
