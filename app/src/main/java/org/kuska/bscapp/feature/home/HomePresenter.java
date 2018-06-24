package org.kuska.bscapp.feature.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.kuska.bscapp.feature.edit.EditNoteActivity;
import org.kuska.bscapp.models.Note;
import org.kuska.bscapp.models.Notes;
import org.kuska.bscapp.networking.NetworkError;
import org.kuska.bscapp.networking.NotesDataManager;
import org.kuska.bscapp.util.rx.AppSchedulers;
import org.kuska.bscapp.util.rx.RxPresenter;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public class HomePresenter extends RxPresenter<HomeView> {
    private final NotesDataManager notesDataManager;
    private final AppSchedulers appSchedulers;

    @Inject
    public HomePresenter(final NotesDataManager notesDataManager, final AppSchedulers appSchedulers) {
        this.notesDataManager = notesDataManager;
        this.appSchedulers = appSchedulers;
    }

    public void getNotes() {
        final HomeView homeView = getView();
        if (homeView == null) {
            return;
        }

        homeView.showProgressBar();

        addSubscription(notesDataManager.getNotes()
        .subscribeOn(appSchedulers.background())
        .observeOn(appSchedulers.ui())
        .subscribe(new Subscriber<Notes>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                homeView.hideProgressBar();
                homeView.showError(new NetworkError(e).getAppErrorMessage());
            }

            @Override
            public void onNext(Notes notes) {
                homeView.hideProgressBar();
                homeView.setData(notes);
            }
        }));
    }

    public void createNote(@NonNull Context context, @NonNull Note note) {
        final Intent intent = EditNoteActivity.buildIntent(context, note, true);
        context.startActivity(intent);
    }

}
