package ru.aleien.yapplication.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by user on 19.07.16.
 */
public class IntentBuilder {
    private static final String playStore = "market://details?id=";

    public static Intent buildOpenAppOrMarketPageIntent(String pack, Context context) {

        try {
            if (Utils.checkPackageExists(context, pack)) {
                PackageManager pm = context.getPackageManager();
                return pm.getLaunchIntentForPackage(pack);
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(playStore + pack));
                return intent;
            }
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(playStore + pack));
            return intent;
        }

    }

}
