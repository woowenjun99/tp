package com.moneymoover.storage;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import com.moneymoover.constants.DateConstants;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    /**
     * Convert a LocalDateTime object to Json using our specific format.
     *
     * @param src       the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context   context for serialization
     * @return JsonElement, the converted Json object.
     */
    @Override
    public JsonElement serialize (LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(DateConstants.OUTPUT_DATE_TIME_FORMATTER));
    }

    @Override
    public LocalDateTime deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), DateConstants.OUTPUT_DATE_TIME_FORMATTER);
    }

}
