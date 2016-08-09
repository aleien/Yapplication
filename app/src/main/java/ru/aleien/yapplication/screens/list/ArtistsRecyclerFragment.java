package ru.aleien.yapplication.screens.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;

/**
 * Created by aleien on 24.04.16.
 * Фрагмент для отображения списка музыкантов.
 */
public class ArtistsRecyclerFragment extends Fragment implements ArtistsView {
    @BindView(R.id.artists_list)
    RecyclerView artistsRecycler;

    private ArtistsRecyclerAdapter adapter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artists_recycler, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        artistsRecycler.setAdapter(adapter);
        artistsRecycler.setHasFixedSize(true);
    }


    @Override
    public void showContent(List<Artist> artists, ArtistClickHandler handler) {
        adapter = new ArtistsRecyclerAdapter(artists, handler);
        if (isAdded()) artistsRecycler.setAdapter(adapter);
    }
}
