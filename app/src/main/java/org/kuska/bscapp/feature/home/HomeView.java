package org.kuska.bscapp.feature.home;

import org.kuska.bscapp.models.Note;

import java.util.List;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public interface HomeView {
    void showProgressBar();

    void hideProgressBar();

    void showError(String errorMessage);

    void setData(List<Note> notes);
}
