package ru.aleien.yapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import ru.aleien.yapplication.base.BasePresenter;
import ru.aleien.yapplication.dataprovider.ArtistsProvider;
import ru.aleien.yapplication.dataprovider.WebArtistsProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragment;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListView;
import ru.aleien.yapplication.screens.list.ArtistsRecyclerFragment;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;

/**
 * Created by aleien on 09.04.16.
 * Активити презентер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsPresenter extends BasePresenter<MainView> implements ArtistsRequester, ArtistClickHandler, Serializable {
    private final ArtistsProvider artistsProvider;
    private WeakReference<ArtistsListView<RecyclerView.Adapter>> artistsListView;
    private WeakReference<Fragment> currentFragment;

    public ArtistsPresenter(Context context) {
        artistsProvider = new WebArtistsProvider(this, context);
    }

    @Override
    public void takeListView(ArtistsListView<RecyclerView.Adapter> list) {
        artistsListView = new WeakReference<>(list);
        artistsProvider.requestData();
    }

    @Override
    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        WeakReference<ArtistInfoView> artistInfoView = new WeakReference<>(info);
        artistInfoView.get().setInfo(artist);
    }

    @Override
    public void provideData(List<Artist> response) {
        artistsListView.get().setAdapter(new ArtistsRecyclerAdapter(response, this));
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
