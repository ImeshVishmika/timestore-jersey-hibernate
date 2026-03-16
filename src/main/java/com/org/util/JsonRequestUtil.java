package com.org.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class JsonRequestUtil {

    private JsonRequestUtil() {
    }

    public static JsonObject parseBody(String requestBody) {
        if (requestBody == null || requestBody.isBlank()) {
            return new JsonObject();
        }

        try {
            JsonElement root = JsonParser.parseString(requestBody);
            if (root != null && root.isJsonObject()) {
                return root.getAsJsonObject();
            }
        } catch (Exception ignored) {
        }

        return new JsonObject();
    }

    public static String getString(JsonObject body, String key) {
        return getString(body, key, null);
    }

    public static String getString(JsonObject body, String key, String defaultValue) {
        if (body == null || key == null || !body.has(key) || body.get(key).isJsonNull()) {
            return defaultValue;
        }
        return body.get(key).getAsString();
    }

    public static Integer getInteger(JsonObject body, String key, Integer defaultValue) {
        if (body == null || key == null || !body.has(key) || body.get(key).isJsonNull()) {
            return defaultValue;
        }

        try {
            return body.get(key).getAsInt();
        } catch (Exception ignored) {
            return defaultValue;
        }
    }
}