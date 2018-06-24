package org.kuska.bscapp.feature.home;

import org.junit.Before;
import org.junit.Test;
import org.kuska.bscapp.models.Notes;
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
public class HomePresenterImplTest {
    @Mock NotesDataManager notesDataManager;
    @Mock HomeView homeView;

    private HomePresenter homePresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        homePresenter = new HomePresenter(notesDataManager, new TestAppSchedulers());
        homePresenter.attachView(homeView);
    }

    @Test
    public void getNotes_NetworkError_showError() {
        when(notesDataManager.getNotes()).thenReturn(Observable.<Notes>error(new Exception()));

        homePresenter.getNotes();

        verify(homeView, times(1)).showProgressBar();
        verify(homeView, times(1)).hideProgressBar();
        verify(homeView, times(1)).showError(any(String.class));
        verify(homeView, never()).setData(any(Notes.class));
    }

    @Test
    public void getNotes_ReceiveNotes_setData() {
        Notes notes = new Notes();
        when(notesDataManager.getNotes()).thenReturn(Observable.just(notes));

        homePresenter.getNotes();

        verify(homeView, times(1)).showProgressBar();
        verify(homeView, times(1)).hideProgressBar();
        verify(homeView, times(1)).setData(notes);
        verify(homeView, never()).showError(any(String.class));
    }
}