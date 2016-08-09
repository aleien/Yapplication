package ru.aleien.yapplication.screens.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.base.BaseFragment;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.list.ArtistsView;

/**
 * Created by aleien on 09.08.16.
 */

public class ArtistsTabsFragment extends BaseFragment implements ArtistsView {
    @BindView(R.id.tabs_viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;

    private List<Artist> cached = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (cached.size() != 0 )
            showContent(new ArrayList<>(cached), null);

    }

    @Override
    public void showContent(List<Artist> artists, ArtistClickHandler handler) {
        cached.clear();
        cached.addAll(artists);
        viewPager.setAdapter(new ArtistsTabsAdapter(getFragmentManager(), artists, handler));
        tabs.setupWithViewPager(viewPager);
    }

}
