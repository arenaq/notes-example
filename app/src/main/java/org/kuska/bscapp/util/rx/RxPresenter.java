package org.kuska.bscapp.util.rx;

import android.support.annotation.Nullable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/24
 */
public abstract class RxPresenter<V> {
    private CompositeSubscription compositeSubscription;
    private V view;

    public void addSubscription(final Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }

    public void attachView(final V mvpView) {
        compositeSubscription = new CompositeSubscription();
        this.view = mvpView;
    }

    public void detachView() {
        if (!isViewAttached()) {
            return;
        }

        unsubscribe();
        this.view = null;
    }

    @Nullable
    public V getView() {
        return view;
    }

    /**
     * @return {@code True} if the view is attached to presenter. {@code False} otherwise.
     */
    private boolean isViewAttached() {
        return view != null;
    }
}
