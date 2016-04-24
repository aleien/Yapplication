package ru.aleien.yapplication.screens.detailedinfo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.ImageLoader;

import static ru.aleien.yapplication.utils.Utils.convertToString;

/**
 * Created by aleien on 09.04.16.
 * Фрагмент для отображения информации о музыканте.
 */
public class ArtistInfoFragment extends Fragment implements ArtistInfoView {
    @Bind(R.id.info_cover)
    ImageView cover;
    @Bind(R.id.info_genres)
    TextView genres;
    @Bind(R.id.info_music)
    TextView infoMusic;
    @Bind(R.id.info_bio)
    TextView bio;

    private Artist artist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_artist_page, container, false);
        ButterKnife.bind(this, fragment);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }

    // TODO: форматирование строки в зависимости от количества песен/альбомов
    private void setup() {
        if (artist != null) {
            ImageLoader.getInstance().loadImage(getContext(), cover, Uri.parse(artist.cover.big));
            genres.setText(convertToString(artist.genres, ','));
            infoMusic.setText(String.format(Locale.getDefault(), getResources().getString(R.string.music_info), artist.albums, artist.tracks));
            bio.setText(artist.description);
        }
    }

    @Override
    public void setInfo(Artist artist) {
        this.artist = artist;
    }


}
