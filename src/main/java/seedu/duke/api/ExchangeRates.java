package seedu.duke.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRates {

    private static final String APP_ID = "1349651eb52b4f85a5ced93d579652ea";
    private static final String BASE_CURRENCY = "USD";

    private static Map<String, Double> exchangeRatesMap;

    public ExchangeRates() {
        fetchExchangeRates(APP_ID, BASE_CURRENCY);
    }

    public static void fetchExchangeRates(String appId, String baseCurrency) {
        ExchangeRatesApi api = ExchangeRatesApiClient.getExchangeRatesApi();
        Call<ExchangeRatesResponse> call = api.getLatestExchangeRates(appId, baseCurrency);
        call.enqueue(new Callback<ExchangeRatesResponse>() {
            @Override
            public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                if (response.isSuccessful()) {
                    ExchangeRatesResponse rates = response.body();
                    exchangeRatesMap = rates.getExchangeRates();
                    printMap();
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

    public static void printMap() {
        for (String currency : exchangeRatesMap.keySet()) {
            System.out.println(currency + ": " + exchangeRatesMap.get(currency));
        }
    }
}
