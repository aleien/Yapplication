package ru.aleien.yapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.aleien.yapplication.utils.ImageLoader;

public class OneAndOnlyActivity extends AppCompatActivity {
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
}
