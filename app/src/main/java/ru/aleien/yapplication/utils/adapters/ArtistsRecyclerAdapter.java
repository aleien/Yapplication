package ru.aleien.yapplication.utils.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.ImageLoader;
import ru.aleien.yapplication.utils.Utils;

/**
 * Created by aleien on 24.04.16.
 * Адаптер для отображения списка исполнителей.
 */
public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistHolder> {

    private final List<Artist> artists;
    private final ArtistClickHandler clickHandler;

    public ArtistsRecyclerAdapter(List<Artist> artists, ArtistClickHandler clickHandler) {
        this.artists = artists;
        this.clickHandler = clickHandler;
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        Artist artist = artists.get(position);
        ImageLoader.getInstance().loadImageCropped(holder.cover.getContext(), holder.cover, Uri.parse(artist.cover.small));
        holder.name.setText(artist.name);
        holder.genres.setText(Utils.convertToString(artist.genres, ','));
        holder.musicInfo.setText(String.format(Locale.getDefault(),
                holder.musicInfo.getResources().getString(R.string.item_music_info),
                artist.albums,
                artist.tracks));
        holder.container.setOnClickListener(l -> clickHandler.artistClicked(artist));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ArtistHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_container)
        RelativeLayout container;
        @Bind(R.id.cover)
        ImageView cover;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.genres)
        TextView genres;
        @Bind(R.id.music_info)
        TextView musicInfo;

        public ArtistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
