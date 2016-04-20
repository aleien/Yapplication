package ru.aleien.yapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.ui.ArtistInfoFragment;
import ru.aleien.yapplication.ui.ArtistsListFragment;

public class ListArtistsActivity extends AppCompatActivity implements MainView {
    final ArtistsPresenter artistsPresenter = new ArtistsPresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setup(R.id.fragment_container);
    }


    public void setup(int fragmentContainerId) {
        ArtistsListFragment artistsListFragment = new ArtistsListFragment();

        changeFragmentTo(fragmentContainerId, artistsListFragment, "Artists List");
        artistsPresenter.takeMainView(this);
        artistsPresenter.takeListView(artistsListFragment);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public ArtistInfoFragment openArtistInfo(Artist artist) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArtistInfoFragment artistInfoFragment = new ArtistInfoFragment();
        changeFragmentTo(R.id.fragment_container, artistInfoFragment, "Artist Info");
        return artistInfoFragment;
    }

    private void changeFragmentTo(int fragmentContainerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainerId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }


}
