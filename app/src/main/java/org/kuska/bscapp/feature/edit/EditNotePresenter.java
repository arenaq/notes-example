package org.kuska.bscapp.feature.edit;

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
public class EditNotePresenter extends RxPresenter<EditNoteView> {
    private final NotesDataManager notesDataManager;
    private final AppSchedulers appSchedulers;

    @Inject public EditNotePresenter(final NotesDataManager notesDataManager, final AppSchedulers appSchedulers) {
        this.notesDataManager = notesDataManager;
        this.appSchedulers = appSchedulers;
    }

    public void editNote(@NonNull Note note) {
        final EditNoteView editNoteView = getView();

        if (editNoteView == null) {
            return;
        }

        editNoteView.showProgressBar();

        addSubscription(notesDataManager.editNote(note)
                .subscribeOn(appSchedulers.background())
                .observeOn(appSchedulers.ui())
                .subscribe(new Subscriber<Note>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        editNoteView.hideProgressBar();
                        editNoteView.showError(new NetworkError(e).getAppErrorMessage());
                    }

                    @Override
                    public void onNext(Note note) {
                        editNoteView.hideProgressBar();
                        editNoteView.showDetail(note);
                    }
                }));
    }
}
