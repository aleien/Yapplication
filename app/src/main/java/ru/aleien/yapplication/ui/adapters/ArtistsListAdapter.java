package ru.aleien.yapplication.ui.adapters;

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
import java.util.Locale;

import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.ImageLoader;
import ru.aleien.yapplication.utils.Utils;

/**
 * Created by aleien on 09.04.16.
 * Класс-адаптер для отображения списка исполнителей.
 */
public class ArtistsListAdapter implements ListAdapter {
    List<Artist> artists;
    ArtistClickHandler clickHandler;

    public ArtistsListAdapter(List<Artist> artists, ArtistClickHandler clickHandler) {
        this.artists = artists;
        this.clickHandler = clickHandler;
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
            item = inflater.inflate(R.layout.item_artist, parent, false);

            // Используем паттерн ViewHolder, т.к. findViewById - затратная по времени операция
            ArtistHolder artistHolder = new ArtistHolder();
            artistHolder.cover = (ImageView) item.findViewById(R.id.cover);
            artistHolder.name = (TextView) item.findViewById(R.id.name);
            artistHolder.genres = (TextView) item.findViewById(R.id.genres);
            artistHolder.musicInfo = (TextView) item.findViewById(R.id.music_info);

            item.setTag(artistHolder);
        }

        ArtistHolder holder = (ArtistHolder) item.getTag();
        Artist artist = artists.get(position);
        bindArtistsData(parent, holder, artist);

        item.setOnClickListener(l -> clickHandler.artistClicked(artist));

        return item;
    }

    private void bindArtistsData(ViewGroup parent, ArtistHolder holder, Artist artist) {
        ImageLoader.getInstance().loadImage(parent.getContext(), holder.cover, Uri.parse(artist.cover.small));
        holder.name.setText(artist.name);
        holder.genres.setText(Utils.convertToString(artist.genres, ','));
        holder.musicInfo.setText(String.format(Locale.getDefault(),
                "%d albums, %d songs",
                artist.albums,
                artist.tracks));
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
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
