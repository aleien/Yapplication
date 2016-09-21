package ru.aleien.yapplication;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import ru.aleien.yapplication.base.BasePresenter;
import ru.aleien.yapplication.database.DBBackend;
import ru.aleien.yapplication.database.DBHelper;
import ru.aleien.yapplication.dataprovider.ArtistsProvider;
import ru.aleien.yapplication.dataprovider.WebArtistsProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragment;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListView;
import ru.aleien.yapplication.screens.list.ArtistsRecyclerFragment;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;
import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by aleien on 09.04.16.
 * Активити презентер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsPresenter extends BasePresenter<MainView> implements ArtistsRequester, ArtistClickHandler, Serializable {
    ArtistsProvider artistsProvider;
    private final DBBackend dbSource;
    private WeakReference<ArtistsListView<RecyclerView.Adapter>> artistsListView;
    private WeakReference<Fragment> currentFragment;

    @Inject
    public ArtistsPresenter(DBHelper dbHelper,
                            DBBackend dbSource) {
        this.dbSource = dbSource;
    }

    @Override
    public void takeListView(ArtistsListView<RecyclerView.Adapter> list) {
        artistsListView = new WeakReference<>(list);

        subscribe(dbSource.getAllArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::provideData,
                        throwable -> Timber.e("DBError", "Error while reading cached artists")));

        artistsProvider.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::provideData,
                        e -> Timber.e(e, "takeListView -> requestData"));
    }

    @Override
    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        WeakReference<ArtistInfoView> artistInfoView = new WeakReference<>(info);
        artistInfoView.get().setInfo(artist);
    }

    @Override
    public void provideData(List<Artist> response) {
        artistsListView.get().setAdapter(new ArtistsRecyclerAdapter(response, this));
        dbSource.clearArtists();
        Completable.fromAction(() -> {
            Timber.e("Working on: " + Thread.currentThread().getName());
            for (Artist artist : response) {
                dbSource.insertArtist(artist);
            }
        }).subscribeOn(Schedulers.io()).subscribe();


    }

    @Override
    public void artistClicked(Artist artist) {
        ArtistInfoFragment infoFragment = new ArtistInfoFragment();
        takeDetailedView(infoFragment, artist);

        getView().changeFragmentTo(infoFragment, true);
    }

    @Override
    public void onStart() {
        if (currentFragment == null) {
            ArtistsRecyclerFragment artistsListFragment = new ArtistsRecyclerFragment();
            takeListView(artistsListFragment);
            currentFragment = new WeakReference<>(artistsListFragment);
        }

        getView().changeFragmentTo(currentFragment.get(), currentFragment.get() instanceof ArtistInfoFragment);
    }
}
