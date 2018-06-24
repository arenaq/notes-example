package org.kuska.bscapp.networking;


import android.support.annotation.NonNull;

import org.kuska.bscapp.models.Note;
import org.kuska.bscapp.models.Notes;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * A data manager class, which handles notes related data.
 *
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2017/06/23
 */
public class NotesDataManager {
    private final NotesDatabaseService notesDatabaseService;

    public NotesDataManager(NotesDatabaseService NotesDatabaseService) {
        this.notesDatabaseService = NotesDatabaseService;
    }

    public Observable<Notes> getNotes() {
        return notesDatabaseService.getNotes()
                .subscribeOn(Schedulers.io());
    }

    public Observable<Note> getNote(@NonNull String id) {
        return notesDatabaseService.getNote(id)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Note> deleteNote(@NonNull String id) {
        return notesDatabaseService.deleteNote(id)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Note> editNote(@NonNull Note note) {
        return notesDatabaseService.putNote(note.getId(), note)
                .subscribeOn(Schedulers.io());
    }
}
