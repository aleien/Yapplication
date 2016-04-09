package ru.aleien.yapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class OneAndOnlyActivity extends AppCompatActivity {
    final ArtistsInteractor artistsInteractor = new ArtistsInteractor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        artistsInteractor.setup(getSupportFragmentManager());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
