package ru.aleien.yapplication.utils;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by user on 19.07.16.
 */
public class PendingIntentBuilder {
    private static final String playStore = "market://details?id=";

    public static PendingIntent buildOpenMarketPendingIntent(int id, String pack, Context context) {
        Intent intent =  buildOpenAppOrMarketPageIntent(pack, context);
        return PendingIntent.getActivity(context, id, intent, 0);
    }

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
