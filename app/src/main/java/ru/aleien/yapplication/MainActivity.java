package ru.aleien.yapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.ButterKnife;
import ru.aleien.yapplication.database.DBContract;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.list.AboutDialogFragment;
import ru.aleien.yapplication.utils.PendingIntentBuilder;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainView {
    private final static int MUSIC_ID = 1010;
    private final static int RADIO_ID = 1020;

    private BroadcastReceiver broadcastReceiver;
    @Inject ArtistsPresenter artistsPresenter;

    @BindString(R.string.about_title) String aboutTitle;
    @BindString(R.string.about_message) String aboutMessage;
    @BindString(R.string.title_dismiss) String dismissTitle;
    @BindString(R.string.email_author) String emailTo;
    @BindString(R.string.email_title) String emailTitle;
    private final static Intent EMAIL_INTENT = new Intent(Intent.ACTION_SENDTO)
            .setType("text/plain")
            .setData(Uri.parse("mailto:"))
            .putExtra(Intent.EXTRA_EMAIL, new String[]{"technogenom@gmail.com"})
            .putExtra(Intent.EXTRA_SUBJECT, "Re: Yapplication");


    final Uri ARTISTS_URI = Uri.parse("content://ru.aleien.yapplication.provider/Artists");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  АХАХА РАБОТА С КОНТЕНТОМ К МЭИН ТРЕДЕ ОЛОЛОЛО
        String[] from = {"id", "name"};
        Cursor cursor = getContentResolver().query(ARTISTS_URI, from, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String artistName = cursor.getString(cursor.getColumnIndex(DBContract.Artists.NAME));
            Timber.d(artistName);
        }

        cursor.close();

        ((App) getApplication()).dagger().inject(this);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                showHeadphonesNotification(audioManager.isWiredHeadsetOn()
                        || audioManager.isBluetoothA2dpOn()
                        || audioManager.isBluetoothScoOn());

            }
        };

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        artistsPresenter.attachView(this);
        artistsPresenter.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override
    protected void onStop() {
        super.onStop();
        artistsPresenter.detachView();
        unregisterReceiver(broadcastReceiver);
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

    // TODO: Вынести в отдельный класс
    // TODO: При открытой странице инфо об артисте, открывать страницу артиста
    private void showHeadphonesNotification(boolean wiredHeadsetOn) {

        PendingIntent musicPendingIntent = PendingIntentBuilder.buildOpenMarketPendingIntent(MUSIC_ID, "ru.yandex.music", this);
        PendingIntent radioPendingIntent = PendingIntentBuilder.buildOpenMarketPendingIntent(RADIO_ID, "ru.yandex.radio", this);

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
        if (EMAIL_INTENT.resolveActivity(getPackageManager()) != null) {
            startActivity(EMAIL_INTENT);
        }
    }

    private void showAbout() {
        DialogFragment aboutFragment = AboutDialogFragment
                .newInstance(aboutTitle, aboutMessage);
        aboutFragment.show(getSupportFragmentManager(), "dialog");
    }
}
