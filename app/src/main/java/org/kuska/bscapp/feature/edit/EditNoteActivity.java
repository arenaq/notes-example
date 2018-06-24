package org.kuska.bscapp.feature.edit;

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
import android.widget.EditText;
import android.widget.ProgressBar;

import org.kuska.bscapp.BaseActivity;
import org.kuska.bscapp.R;
import org.kuska.bscapp.feature.detail.NoteDetailActivity;
import org.kuska.bscapp.feature.home.HomeActivity;
import org.kuska.bscapp.models.Note;
import org.parceler.Parcels;

import javax.inject.Inject;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public class EditNoteActivity extends BaseActivity implements EditNoteView {
    private static final String ARGS_NOTE_ID = "org.kuska.bscapp.EXTRA_NOTE_ID";
    private static final String ARGS_NOTE = "org.kuska.bscapp.EXTRA_NOTE";
    private static final String ARGS_CREATE = "org.kuska.bscapp.EXTRA_CREATE";

    private boolean createNew;
    private Note note;
    private String noteId;
    private EditText editTitle;
    private ProgressBar progressBar;
    @Inject public EditNotePresenter editNotePresenter;

    public static Intent buildIntent(@NonNull final Context context, @NonNull final Note note) {
        final Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(ARGS_NOTE, Parcels.wrap(note));
        return intent;
    }

    public static Intent buildIntent(@NonNull final Context context, @NonNull final Note note, boolean createNew) {
        final Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(ARGS_NOTE, Parcels.wrap(note));
        intent.putExtra(ARGS_CREATE, createNew);
        return intent;
    }

    public static Intent buildIntent(@NonNull final Context context, @NonNull final String noteId) {
        final Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(ARGS_NOTE_ID, noteId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        editNotePresenter.attachView(this);

        initData();

        setContentView(R.layout.activity_edit);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        if (createNew) {
            setTitle(R.string.create_note_title);
        } else {
            setTitle(R.string.edit_note_title);
        }

        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.setText(note.getTitle());

        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editNotePresenter.detachView();
    }

    private void initData() {
        final Intent incoming = getIntent();
        if (incoming != null) {
            note = Parcels.unwrap(incoming.getParcelableExtra(ARGS_NOTE));
            noteId = incoming.getStringExtra(ARGS_NOTE_ID);
            createNew = incoming.getBooleanExtra(ARGS_CREATE, false);
        }
        if (note == null && noteId == null) {
            throw new IllegalArgumentException("both note and noteId are null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save_note:
                note.setTitle(editTitle.getText().toString());
                editNotePresenter.editNote(note);
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
    public void showDetail(Note note) {
        // TODO if note is null, get note from BE
        if (createNew) {
            NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
        }
        NavUtils.navigateUpTo(this, NoteDetailActivity.buildIntent(this, note != null ? note : this.note));
    }
}
