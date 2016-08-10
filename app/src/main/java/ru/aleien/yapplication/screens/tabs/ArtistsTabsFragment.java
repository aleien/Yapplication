package ru.aleien.yapplication.screens.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnPageChange;
import butterknife.Optional;
import icepick.Icepick;
import icepick.State;
import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.base.BaseFragment;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.list.ArtistsView;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;
import timber.log.Timber;

public class ArtistsTabsFragment extends BaseFragment implements ArtistsView, ArtistClickHandler {
    @Nullable
    @BindView(R.id.tabs_viewpager)
    ViewPager viewPager;
    @Nullable
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @BindView(R.id.tabs_recycler)
    RecyclerView recycler;
    @Nullable ArtistsRecyclerAdapter listAdapter;
    @Nullable
    @BindView(R.id.tabs_container)
    FrameLayout container;

    List<Artist> cached = new ArrayList<>();
    @State Artist currentArtist;
    @State int currentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_tabs, container, false);
    }

    // Чтобы это заработало, надо поправить архитектурную ошибку - презентер в onStart руками пересоздает фрагмент
    // Это неправильно.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        if (cached.size() != 0)
            showContent(new ArrayList<>(cached), null);

        if (recycler != null) {
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            if (listAdapter == null)
                listAdapter = new ArtistsRecyclerAdapter(cached, this);
            recycler.setAdapter(listAdapter);

            if (currentArtist != null)
                setFragment(new ArtistTabFragmentBuilder(currentArtist).build());
        }

        if (viewPager != null && tabs != null) {
            // тут нужно происзвести какую-то магию, потому что сейчас те страницы, которые были в памяти до
            // открытия детализированной информации, не пересоздаются и не переоткрыватся
            // Почему?
            viewPager.setAdapter(new ArtistsTabsAdapter(getFragmentManager(), cached, this));
            tabs.setupWithViewPager(viewPager);
            tabs.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(currentPage, false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void showContent(List<Artist> artists, ArtistClickHandler handler) {
        cached.clear();
        cached.addAll(artists);

        if (currentArtist == null) currentArtist = cached.get(0);
        if (viewPager != null && tabs != null) {
            viewPager.setAdapter(new ArtistsTabsAdapter(getFragmentManager(), artists, handler));
            tabs.setupWithViewPager(viewPager);
            tabs.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(currentPage, false);
        }

        // Проверки в коде на наличие вьюх? Получается один фрагмент отвечает и за планшетную, и за
        // портретную ориентацию. Лучше сделать отдельный фрагмент для того и другого, мне кажется
        if (recycler != null) {
            // Это тоже как-то неправильно. Мне кажется, адаптер должен в одном месте задаваться
            if (listAdapter == null) listAdapter = new ArtistsRecyclerAdapter(cached, this);
            recycler.setAdapter(listAdapter);

            setFragment(new ArtistTabFragmentBuilder(currentArtist).build());
        }
    }

    @Optional
    @OnPageChange(value = R.id.tabs_viewpager,
            callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageChanged(int page) {
        Timber.d("Selected page: %d", page);
        currentPage = page;
    }

    @Override
    public void artistClicked(Artist artist) {
        currentArtist = artist;
        setFragment(new ArtistTabFragmentBuilder(artist).build());
    }

    private void setFragment(Fragment fragment) {
        if (container != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.tabs_container, fragment)
                    .commit();
        }
    }
}
