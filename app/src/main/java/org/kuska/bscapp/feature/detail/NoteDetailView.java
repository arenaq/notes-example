package org.kuska.bscapp.feature.detail;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public interface NoteDetailView {
    void showProgressBar();

    void hideProgressBar();

    void showError(String errorMessage);

    void closeDetail();
}
