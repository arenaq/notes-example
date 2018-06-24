package org.kuska.bscapp.feature.detail;

import android.support.annotation.NonNull;

import org.kuska.bscapp.models.Note;
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
public class NoteDetailPresenter extends RxPresenter<NoteDetailView> {
    private final NotesDataManager notesDataManager;
    private final AppSchedulers appSchedulers;

    @Inject
    public NoteDetailPresenter(final NotesDataManager notesDataManager, final AppSchedulers appSchedulers) {
        this.notesDataManager = notesDataManager;
        this.appSchedulers = appSchedulers;
    }

    public void deleteNote(@NonNull String id) {
        final NoteDetailView noteDetailView = getView();

        if (noteDetailView == null) {
            return;
        }

        noteDetailView.showProgressBar();

        addSubscription(notesDataManager.deleteNote(id)
                .subscribeOn(appSchedulers.background())
                .observeOn(appSchedulers.ui())
                .subscribe(new Subscriber<Note>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        noteDetailView.hideProgressBar();
                        noteDetailView.showError(new NetworkError(e).getAppErrorMessage());
                    }

                    @Override
                    public void onNext(Note note) {
                        noteDetailView.hideProgressBar();
                        noteDetailView.closeDetail();
                    }
                }));
    }
}
