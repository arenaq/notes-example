package org.kuska.bscapp.feature.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.kuska.bscapp.BaseActivity;
import org.kuska.bscapp.R;
import org.kuska.bscapp.feature.edit.EditNoteActivity;
import org.kuska.bscapp.models.Note;
import org.kuska.bscapp.networking.NotesDataManager;
import org.parceler.Parcels;

import javax.inject.Inject;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public class NoteDetailActivity extends BaseActivity implements NoteDetailView{
    private static final String ARGS_NOTE_ID = "org.kuska.bscapp.EXTRA_NOTE_ID";
    private static final String ARGS_NOTE = "org.kuska.bscapp.EXTRA_NOTE";

    private Note note;
    private String noteId;
    private TextView txtTitle;
    private ProgressBar progressBar;
    @Inject public NoteDetailPresenter noteDetailPresenter;

    public static Intent buildIntent(@NonNull final Context context, @NonNull final Note note) {
        final Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(ARGS_NOTE, Parcels.wrap(note));
        return intent;
    }

    public static Intent buildIntent(@NonNull final Context context, @NonNull final String noteId) {
        final Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(ARGS_NOTE_ID, noteId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        noteDetailPresenter.attachView(this);

        initData();

        setContentView(R.layout.activity_detail);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.note_detail_title);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(note.getTitle());

        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteDetailPresenter.detachView();
    }

    private void initData() {
        final Intent incoming = getIntent();
        if (incoming != null) {
            note = Parcels.unwrap(incoming.getParcelableExtra(ARGS_NOTE));
            noteId = incoming.getStringExtra(ARGS_NOTE_ID);
        }
        if (note == null && noteId == null) {
            throw new IllegalArgumentException("both note and noteId are null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_note:
                final Intent intent = EditNoteActivity.buildIntent(this, note);
                startActivity(intent);
                return true;
            case R.id.delete_note:
                noteDetailPresenter.deleteNote(note.getId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String appErrorMessage) {
        showNotificationDialog(getString(R.string.alert_notification_dialog_title_error), appErrorMessage);
    }

    @Override
    public void closeDetail() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
