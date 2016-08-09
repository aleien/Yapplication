package ru.aleien.yapplication.screens.tabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.ImageLoader;

import static ru.aleien.yapplication.utils.Utils.convertToString;

@FragmentWithArgs
public class InfoDialogFragment extends DialogFragment {
    @Arg @NonNull Artist artist;
    Unbinder unbinder;

    @BindView(R.id.info_cover)
    ImageView cover;
    @BindView(R.id.info_genres)
    TextView genres;
    @BindView(R.id.info_music)
    TextView infoMusic;
    @BindView(R.id.info_bio)
    TextView bio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_artist_page, container, false);
        unbinder = ButterKnife.bind(this, fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();

    }

    private void setup() {
        ImageLoader.getInstance().loadImage(getContext(), cover, Uri.parse(artist.cover.big));
        genres.setText(convertToString(artist.genres, ','));
        infoMusic.setText(String.format(Locale.getDefault(), getResources().getString(R.string.music_info), artist.albums, artist.tracks));
        bio.setText(artist.description);

    }
}
