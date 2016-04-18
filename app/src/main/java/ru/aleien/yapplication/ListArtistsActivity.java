package ru.aleien.yapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.ui.ArtistInfoFragment;
import ru.aleien.yapplication.ui.ArtistsListFragment;
import ru.aleien.yapplication.utils.ImageLoader;

public class ListArtistsActivity extends AppCompatActivity implements MainView {
    final ArtistsInteractor artistsInteractor = new ArtistsInteractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader imageLoader = ImageLoader.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setup(R.id.fragment_container);

    }

    public void setup(int fragmentContainerId) {
        ArtistsListFragment artistsListFragment = new ArtistsListFragment();

        changeFragmentTo(fragmentContainerId, artistsListFragment, "Artists List");
        artistsInteractor.takeListView(artistsListFragment);
    }

    @Override
    public void openArtistInfo(Artist artist) {
        ArtistInfoFragment artistInfoFragment = new ArtistInfoFragment();

        changeFragmentTo(R.id.fragment_container, artistInfoFragment, "Artist Info");
        artistsInteractor.takeDetailedView(artistInfoFragment, artist);
    }

    private void changeFragmentTo(int fragmentContainerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }


}
