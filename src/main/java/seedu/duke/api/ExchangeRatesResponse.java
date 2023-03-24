package seedu.duke.api;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class ExchangeRatesResponse {
    @SerializedName("base")
    private String baseCurrency;
    
    @SerializedName("rates")
    private Map<String, Double> exchangeRates;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }
}
