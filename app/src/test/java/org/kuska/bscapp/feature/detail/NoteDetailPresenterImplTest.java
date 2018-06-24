package org.kuska.bscapp.feature.detail;

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
public class NoteDetailPresenterImplTest {
    @Mock NotesDataManager notesDataManager;
    @Mock NoteDetailView noteDetailView;

    private NoteDetailPresenter noteDetailPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteDetailPresenter = new NoteDetailPresenter(notesDataManager, new TestAppSchedulers());
        noteDetailPresenter.attachView(noteDetailView);
    }

    @Test
    public void deleteNote_NetworkError_showError() {
        String id = "id";
        when(notesDataManager.deleteNote(id)).thenReturn(Observable.<Note>error(new Exception()));

        noteDetailPresenter.deleteNote(id);

        verify(noteDetailView, times(1)).showProgressBar();
        verify(noteDetailView, times(1)).hideProgressBar();
        verify(noteDetailView, times(1)).showError(any(String.class));
        verify(noteDetailView, never()).closeDetail();
    }

    @Test
    public void deleteNote_CorrectResponse_closeDetail() {
        String id = "id";
        when(notesDataManager.deleteNote(id)).thenReturn(Observable.just(new Note()));

        noteDetailPresenter.deleteNote(id);

        verify(noteDetailView, times(1)).showProgressBar();
        verify(noteDetailView, times(1)).hideProgressBar();
        verify(noteDetailView, times(1)).closeDetail();
        verify(noteDetailView, never()).showError(any(String.class));
    }
}