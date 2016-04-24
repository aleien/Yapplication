package ru.aleien.yapplication.screens.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.utils.adapters.ArtistsRecyclerAdapter;

/**
 * Created by aleien on 24.04.16.
 * Фрагмент для отображения списка музыкантов.
 */
public class ArtistsRecyclerFragment extends Fragment implements ArtistsListView<RecyclerView.Adapter> {
    @Bind(R.id.artists_list)
    RecyclerView artistsRecycler;

    private ArtistsRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artists_recycler, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistsRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        artistsRecycler.setAdapter(adapter);
        artistsRecycler.setHasFixedSize(true);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof ArtistsRecyclerAdapter) {
            this.adapter = (ArtistsRecyclerAdapter) adapter;

            if (this.isAdded()) {
                artistsRecycler.setAdapter(adapter);
            }
        }

    }
}
