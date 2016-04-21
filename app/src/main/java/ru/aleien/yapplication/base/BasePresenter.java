package ru.aleien.yapplication.base;

import java.lang.ref.WeakReference;

/**
 * Created by aleien on 21.04.16.
 */
public abstract class BasePresenter<V> {
    private WeakReference<V> view;

    public abstract void onStart();

    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    public void detachView() {
        view.clear();
        view = null;
    }

    public V getView() {
        return view.get();
    }


}
