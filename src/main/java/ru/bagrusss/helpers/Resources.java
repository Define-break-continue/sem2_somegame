package ru.bagrusss.helpers;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by vladislav on 18.12.15.
 */


@SuppressWarnings("OverlyBroadThrowsClause")
public class Resources {

    public static final Gson GSON = new Gson();

    public static <T> T readResourses(String path, Class<T> cl) throws IOException {
        try (FileReader file = new FileReader(path);
             BufferedReader reader = new BufferedReader(file)) {
            return GSON.fromJson(reader, cl);
        }
    }
}