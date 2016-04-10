package ru.aleien.yapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ru.aleien.yapplication.utils.ImageLoader;

public class ListArtistsActivity extends AppCompatActivity implements UIPresenter {
    final ArtistsInteractor artistsInteractor = new ArtistsInteractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader imageLoader = ImageLoader.getInstance();
        artistsInteractor.setup(getSupportFragmentManager(), R.id.fragment_container);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void changeFragment(Fragment fragment) {

    }

    @Override
    public void popBackStack() {

    }
}
