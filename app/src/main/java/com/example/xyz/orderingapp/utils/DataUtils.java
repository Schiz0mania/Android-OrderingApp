package com.example.xyz.orderingapp.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by xyz on 2022/3/30.
 */

public class DataUtils {
    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T[]> cls) {
        T[] arr = new Gson().fromJson(gsonString, cls);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = new Gson().fromJson(gsonString, cls);
        return t;
    }

    public static String getJsontoString(Context context,String filename) {
        InputStream input = null;
        try {
            input = context.getAssets().open(filename);
            String json = convertStreamToString(input);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * input 流转换为字符串
     *
     * @param is
     * @return
     */
    private static String convertStreamToString(InputStream is) {
        String s = null;
        try {
            //格式转换
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                s = scanner.next();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
