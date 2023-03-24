package seedu.duke.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExchangeRatesApiClient {
    private static final String BASE_URL = "https://openexchangerates.org/api/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ExchangeRatesApi getExchangeRatesApi() {
        return getClient().create(ExchangeRatesApi.class);
    }
}
