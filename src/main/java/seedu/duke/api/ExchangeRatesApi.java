package seedu.duke.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRatesApi {
    @GET("latest.json")
    Call<ExchangeRatesResponse> getLatestExchangeRates(@Query("app_id")String appId,@Query("base")String baseCurrency);
}
