package org.kuska.bscapp.feature.edit;

import org.junit.Before;
import org.junit.Test;
import org.kuska.bscapp.models.Note;
import org.kuska.bscapp.networking.NotesDataManager;
import org.kuska.bscapp.util.rx.TestAppSchedulers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/24
 */
public class EditNotePresenterTest {
    @Mock NotesDataManager notesDataManager;
    @Mock EditNoteView editNoteView;

    private EditNotePresenter editNotePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        editNotePresenter = new EditNotePresenter(notesDataManager, new TestAppSchedulers());
        editNotePresenter.attachView(editNoteView);
    }

    @Test
    public void getNotes_NetworkError_showError() {
        Note note = new Note();
        when(notesDataManager.editNote(note)).thenReturn(Observable.<Note>error(new Exception()));

        editNotePresenter.editNote(note);

        verify(editNoteView, times(1)).showProgressBar();
        verify(editNoteView, times(1)).hideProgressBar();
        verify(editNoteView, times(1)).showError(any(String.class));
        verify(editNoteView, never()).showDetail(note);
    }

    @Test
    public void getNotes_ReceiveNotes_setData() {
        Note note1 = new Note();
        Note note2 = new Note();
        when(notesDataManager.editNote(note1)).thenReturn(Observable.just(note2));

        editNotePresenter.editNote(note1);

        verify(editNoteView, times(1)).showProgressBar();
        verify(editNoteView, times(1)).hideProgressBar();
        verify(editNoteView, times(1)).showDetail(note2);
        verify(editNoteView, never()).showDetail(note1);
        verify(editNoteView, never()).showError(any(String.class));
    }
}