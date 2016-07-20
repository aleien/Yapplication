package ru.aleien.yapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.Response;
import ru.aleien.yapplication.model.Artist;


public class Utils {
    public static String convertToString(List list, Character separator) {
        if (list == null) return "";

        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            resultString.append(list.get(i).toString());
            if (i < list.size() - 1) {
                resultString.append(separator).append(' ');
            }
        }
        return resultString.toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && (activeNetworkInfo.isConnected());
    }

    @NonNull
    public static Interceptor createInterceptor(Context context) {
        return chain -> {
            Response originalResponse = chain.proceed(chain.request());
            if (Utils.isNetworkAvailable(context)) {
                int maxAge = 60;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };
    }

    public static Cache getCache(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }

    public static List<Artist> decodeResponse(Response response) {
        List<Artist> resultList = null;
        Type listType = new TypeToken<List<Artist>>() {
        }.getType();
        try {
            resultList = new Gson().fromJson(response.body().string(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static String getAppVersion(Context context) {
        String versionCode = "1.0";
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static boolean checkPackageExists(Context context, String targetPackage) {
        try {
            context.getPackageManager().getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }


}
