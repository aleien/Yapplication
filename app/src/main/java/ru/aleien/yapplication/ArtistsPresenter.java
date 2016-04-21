package ru.aleien.yapplication;

import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import ru.aleien.yapplication.base.BasePresenter;
import ru.aleien.yapplication.dataprovider.ArtistsProvider;
import ru.aleien.yapplication.dataprovider.FakeDataProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragment;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListFragment;
import ru.aleien.yapplication.screens.list.ArtistsListView;
import ru.aleien.yapplication.utils.adapters.ArtistsListAdapter;

/**
 * Created by aleien on 09.04.16.
 * Активити презентер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsPresenter extends BasePresenter<MainView> implements ArtistsRequester, ArtistClickHandler, Serializable {
    final ArtistsProvider artistsProvider = new FakeDataProvider(this);
    WeakReference<ArtistsListView> artistsListView;
    WeakReference<ArtistInfoView> artistInfoView;
    WeakReference<Fragment> currentFragment;

    List<Artist> artists;

    public ArtistsPresenter() {

    }

    @Override
    public void takeListView(ArtistsListView list) {
        artistsListView = new WeakReference<>(list);
        artistsProvider.requestData();
    }

    @Override
    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        artistInfoView = new WeakReference<>(info);
        artistInfoView.get().setInfo(artist);
    }

    @Override
    public void provideData(List<Artist> response) {
        artists = response;
        artistsListView.get().setAdapter(new ArtistsListAdapter(artists, this));
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
            ArtistsListFragment artistsListFragment = new ArtistsListFragment();
            takeListView(artistsListFragment);
            currentFragment = new WeakReference<>(artistsListFragment);
        }

        getView().changeFragmentTo(currentFragment.get(), currentFragment.get() instanceof ArtistInfoFragment);
    }
}
