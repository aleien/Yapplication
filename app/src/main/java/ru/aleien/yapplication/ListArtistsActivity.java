package ru.aleien.yapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ListArtistsActivity extends AppCompatActivity implements MainView {
    ArtistsPresenter artistsPresenter;
    // TODO: Найти лик и уничтожить!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        instantiatePresenter(savedInstanceState);

    }

    private void instantiatePresenter(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("presenterState")) {
            artistsPresenter = (ArtistsPresenter) savedInstanceState.get("presenterState");
        } else {
            artistsPresenter = new ArtistsPresenter();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        artistsPresenter.attachView(this);
        artistsPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        artistsPresenter.detachView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("presenterState", artistsPresenter);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void changeFragmentTo(Fragment fragment, boolean hideBackButton) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(hideBackButton);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
