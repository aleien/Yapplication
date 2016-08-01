package ru.aleien.yapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import ru.aleien.yapplication.di.AppComponent;
import ru.aleien.yapplication.di.AppModule;
import ru.aleien.yapplication.di.DaggerAppComponent;
import ru.aleien.yapplication.utils.IntentBuilder;
import ru.aleien.yapplication.utils.Utils;

public class ListArtistsActivity extends AppCompatActivity implements MainView {
    @Inject ArtistsPresenter artistsPresenter;
    @Inject SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        setContentView(R.layout.activity_main);
        setupToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        artistsPresenter.attachView(this);
        artistsPresenter.onStart();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                showHeadphonesNotification(audioManager.isWiredHeadsetOn()
                        || audioManager.isBluetoothA2dpOn()
                        || audioManager.isBluetoothScoOn());

            }
        }, new IntentFilter(Intent.ACTION_HEADSET_PLUG));

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
                .addToBackStack("info")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    // TODO: Вынести в отдельный класс
    // TODO: При открытой странице инфо об артисте, открывать страницу артиста
    private void showHeadphonesNotification(boolean wiredHeadsetOn) {
        Intent musicIntent = IntentBuilder.buildOpenAppOrMarketPageIntent("ru.yandex.music", this);
        Intent radioIntent = IntentBuilder.buildOpenAppOrMarketPageIntent("ru.yandex.radio", this);

        PendingIntent musicPendingIntent = PendingIntent.getActivity(this, 1010, musicIntent, 0);
        PendingIntent radioPendingIntent = PendingIntent.getActivity(this, 1020, radioIntent, 0);

        int musicNotificationId = 001;

        if (wiredHeadsetOn) {
            NotificationCompat.Builder musicNotificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_stat_hardware_headset)
                            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                            .setContentTitle("Headphones plugged in")
                            .addAction(R.drawable.ic_stat_yamusic, "Ya.Music", musicPendingIntent)
                            .addAction(R.drawable.ic_stat_hardware_headset, "Ya.Radio", radioPendingIntent)
                            .setContentText("Open in:");

            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            mNotifyMgr.notify(musicNotificationId, musicNotificationBuilder.build());
        } else {
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            mNotifyMgr.cancel(musicNotificationId);
        }

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
