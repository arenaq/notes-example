package org.kuska.bscapp.networking;


import android.support.annotation.NonNull;

import org.kuska.bscapp.models.Note;
import org.kuska.bscapp.models.Notes;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * The interface definitions of the notes API.
 *
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2017/06/23
 */
public interface NotesDatabaseService {

    @GET("notes")
    Observable<Notes> getNotes();

    @GET("notes/{id}")
    Observable<Note> getNote(@NonNull @Path("id") String id);

    @POST("notes")
    Observable<Notes> postNotes();

    @PUT("notes/{id}")
    Observable<Note> putNote(@NonNull @Path("id") String id, @Body Note note);

    @DELETE("notes/{id}")
    Observable<Note> deleteNote(@NonNull @Path("id") String id);
}
