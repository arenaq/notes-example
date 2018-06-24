package org.kuska.bscapp.injection;


import org.kuska.bscapp.feature.detail.NoteDetailActivity;
import org.kuska.bscapp.feature.detail.NoteDetailPresenter;
import org.kuska.bscapp.feature.edit.EditNoteActivity;
import org.kuska.bscapp.feature.edit.EditNotePresenter;
import org.kuska.bscapp.feature.home.HomeActivity;
import org.kuska.bscapp.networking.NotesDataManager;
import org.kuska.bscapp.util.rx.AppSchedulers;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
@Singleton
@Component(modules = {
        NetworkModule.class,
        AppModule.class,})
public interface AppComponent {
    void inject(HomeActivity homeActivity);

    void inject(NoteDetailActivity noteDetailActivity);

    void inject(EditNoteActivity editNoteActivity);

    void inject(NotesDataManager notesDataManager);

    void inject(NoteDetailPresenter noteDetailPresenter);

    void inject(EditNotePresenter editNotePresenter);

    void inject(AppSchedulers appSchedulers);
}
