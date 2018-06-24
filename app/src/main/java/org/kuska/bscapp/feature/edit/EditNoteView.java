package org.kuska.bscapp.feature.edit;

import org.kuska.bscapp.models.Note;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public interface EditNoteView {
    void showProgressBar();

    void hideProgressBar();

    void showError(String errorMessage);

    void showDetail(Note note);
}
