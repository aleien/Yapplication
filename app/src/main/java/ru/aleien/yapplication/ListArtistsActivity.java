package ru.aleien.yapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ru.aleien.yapplication.utils.Utils;

public class ListArtistsActivity extends AppCompatActivity implements MainView {
    private ArtistsPresenter artistsPresenter;

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
            artistsPresenter = new ArtistsPresenter(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().popBackStack();
                break;
            case R.id.menu_about:
                showAbout();
                break;
            case R.id.menu_contact:
                composeEmail();
                break;
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

    private void composeEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"technogenom@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Re: Yapplication");
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }

    }

    private void showAbout() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.about_title))
                .setMessage(getResources().getString(R.string.about_message) + Utils.getAppVersion(this))
                .setNegativeButton(getResources().getString(R.string.title_dismiss), null)
                .show();
    }
}
