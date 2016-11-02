package io.infectnet.server.controller.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.infectnet.server.controller.error.ErrorConvertibleException;

public final class WebSocketUtils {
    private static final Gson gson = new Gson();

    private WebSocketUtils(){
        /*
         * Cannot be instantiated.
         */
    }

    public static String convertError(ErrorConvertibleException exception){
        JsonObject obj = new JsonObject();
        obj.addProperty("action",Action.ERROR.toString());
        obj.add("arguments", gson.toJsonTree(exception.toError()));
        return obj.toString();
    }
}
