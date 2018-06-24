package org.kuska.bscapp.models;

import org.parceler.Parcel;

/**
 * Model class representing a note.
 *
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2017/06/23
 */
@Parcel
public class Note {
    public String id;

    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
