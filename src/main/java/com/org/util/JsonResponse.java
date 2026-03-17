package com.org.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonResponse {
    private static final Gson gson = new Gson();

    public static String response(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        responseJson.addProperty("error", state ? "" : message);

        return gson.toJson(responseJson);
    }
}
