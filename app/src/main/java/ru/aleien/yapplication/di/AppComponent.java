package ru.aleien.yapplication.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.aleien.yapplication.MainActivity;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MainActivity mainActivity);

}
