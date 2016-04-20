package ru.aleien.yapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.aleien.yapplication.ArtistsListView;
import ru.aleien.yapplication.R;

/**
 * Created by aleien on 09.04.16.
 * Фрагмент для отображения списка музыкантов.
 */
public class ArtistsListFragment extends Fragment implements ArtistsListView {
    @Bind(R.id.artists_list)
    ListView artistsList;

    ListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artists, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artistsList.setAdapter(adapter);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        this.adapter = adapter;
    }
}
