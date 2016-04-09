package ru.aleien.yapplication.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.ImageLoader;

/**
 * Created by aleien on 09.04.16.
 * Класс-адаптер для отображения списка исполнителей.
 */
public class ArtistsListAdapter implements ListAdapter {
    List<Artist> artists;

    public ArtistsListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Object getItem(int position) {
        return artists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = convertView;

        if (item == null) {
            item = inflater.inflate(R.layout.item_artist, parent);

            // Используем паттерн ViewHolder, т.к. findViewById - затратнаяпо времени операция
            ArtistHolder artistHolder = new ArtistHolder();
            artistHolder.cover = (ImageView) item.findViewById(R.id.cover);
            artistHolder.name = (TextView) item.findViewById(R.id.name);
            artistHolder.genres = (TextView) item.findViewById(R.id.genres);
            artistHolder.musicInfo = (TextView) item.findViewById(R.id.music_info);

            item.setTag(artistHolder);
        }

        ArtistHolder holder = (ArtistHolder) item.getTag();
        ImageLoader.loadImage(parent.getContext(), holder.cover, Uri.parse(artists.get(position).cover.small));

        return item;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return artists.isEmpty();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class ArtistHolder {
        public ImageView cover;
        public TextView name;
        public TextView genres;
        public TextView musicInfo;
    }

}
