package ru.aleien.yapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by aleien on 09.04.16.
 * Класс-помощник загрузки картинок.
 */

public class ImageLoader {
    // TODO: синглтон в андроиде не является безопасным, т.к. может быть собран GC. Реализовать как-нибудь по-другому (в контексте приложения?)
    private static ImageLoader instance;

    private ImageLoader() {
    }

    public static synchronized ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;

    }

    public void loadImage(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .into(imageView);
    }
}
