package ru.aleien.yapplication.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.aleien.yapplication.ListArtistsActivity;
import ru.aleien.yapplication.database.DBBackend;

/**
 * Created by aleien on 22.07.16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(ListArtistsActivity listArtistsActivity);

    void inject(DBBackend DBBackend);
}
