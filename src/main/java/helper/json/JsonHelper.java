package helper.json;

import com.google.gson.*;
public class JsonHelper {
    private static final Gson gson = new GsonBuilder().create();

    public static <T> String toJson(T object){ return gson.toJson(object);}

    public static <T> T fromJson(final String json, Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(json, clazz);
    }
}
