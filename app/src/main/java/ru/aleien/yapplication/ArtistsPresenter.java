package ru.aleien.yapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import ru.aleien.yapplication.base.BasePresenter;
import ru.aleien.yapplication.dataprovider.ArtistsProvider;
import ru.aleien.yapplication.dataprovider.WebArtistsProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.model.ArtistsDataSource;
import ru.aleien.yapplication.model.DBHelper;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragment;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListView;
import ru.aleien.yapplication.screens.list.ArtistsRecyclerFragment;
import ru.aleien.yapplication.screens.list.ArtistsView;
import ru.aleien.yapplication.screens.tabs.ArtistsTabsFragment;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aleien on 09.04.16.
 * Активити презентер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsPresenter extends BasePresenter<MainView> implements ArtistsRequester, ArtistClickHandler, Serializable {
    ArtistsProvider artistsProvider;
    DBHelper dbHelper;
    private final ArtistsDataSource dbSource;
    private WeakReference<ArtistsView> artistsListView;
    private WeakReference<Fragment> currentFragment;

    @Inject
    public ArtistsPresenter(Context context,
                            DBHelper dbHelper,
                            ArtistsDataSource dbSource) {
        artistsProvider = new WebArtistsProvider(this, context);
        this.dbHelper = dbHelper;
        this.dbSource = dbSource;
    }

    @Override
    public void takeListView(ArtistsView list) {
        artistsListView = new WeakReference<>(list);

        subscribe(dbSource.getAllArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::provideData,
                        throwable -> Log.e("DBError", "Error while reading cached artists")));

        artistsProvider.requestData();
    }

    @Override
    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        WeakReference<ArtistInfoView> artistInfoView = new WeakReference<>(info);
        artistInfoView.get().setInfo(artist);
    }

    @Override
    public void provideData(List<Artist> response) {
        if (artistsListView != null && artistsListView.get() != null) {
            artistsListView.get().showContent(response, this);
        }
        dbSource.clearArtists();
        for (Artist artist : response) {
            dbSource.insertArtist(artist);
        }

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
            ArtistsTabsFragment artistsListFragment = new ArtistsTabsFragment();
            takeListView(artistsListFragment);
            currentFragment = new WeakReference<>(artistsListFragment);
        }

        getView().changeFragmentTo(currentFragment.get(), currentFragment.get() instanceof ArtistInfoFragment);
    }
}
