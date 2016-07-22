package ru.aleien.yapplication.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.aleien.yapplication.ListArtistsActivity;

/**
 * Created by aleien on 22.07.16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(ListArtistsActivity listArtistsActivity);
}
