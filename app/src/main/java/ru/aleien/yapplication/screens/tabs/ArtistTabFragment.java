package ru.aleien.yapplication.screens.tabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import ru.aleien.yapplication.R;
import ru.aleien.yapplication.base.BaseFragment;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragmentBuilder;
import ru.aleien.yapplication.utils.ImageLoader;

@FragmentWithArgs
public class ArtistTabFragment extends BaseFragment {
    @BindView(R.id.tab_image)
    ImageView artistImage;

    @Arg
    @NonNull
    Artist artist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageLoader.getInstance().loadImageCropped(getContext(), artistImage, artist.cover.big);
    }

    @OnClick(R.id.tab_more_button)
    public void onMoreButtonClicked() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new InfoDialogFragmentBuilder(artist).build())
                .addToBackStack(null)
                .commit();
//        newFragment.show(getFragmentManager(), "dialog");
//        if (clickHandler != null) {
//            clickHandler.artistClicked(artist);
//        }
    }
}
