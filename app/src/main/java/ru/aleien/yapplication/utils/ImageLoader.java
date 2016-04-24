package ru.aleien.yapplication.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ru.aleien.yapplication.R;

/**
 * Created by aleien on 09.04.16.
 * Класс-помощник загрузки картинок. Для скорости используем glide, потому что картинки с ним грузятся
 * плавнее, чем с пискассой (благодаря тому, что не нужно их ресайзить перед загрузкой во вьюху), а
 * разница в размере библиотеки не так уж заметна.
 */

public class ImageLoader {
    private static volatile ImageLoader instance;

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void loadImageCropped(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);
    }

    public void loadImage(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }
}
