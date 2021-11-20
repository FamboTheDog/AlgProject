package com.company.communication;

import com.google.gson.Gson;

public class GsonUtil {

    private static Gson parser = new Gson();

    public static <T> T parse(String toParse, Class<T> classType) {
        return parser.fromJson(toParse, classType);
    }

}
