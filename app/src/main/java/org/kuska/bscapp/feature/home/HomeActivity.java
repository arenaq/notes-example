package org.kuska.bscapp.feature.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.kuska.bscapp.BaseActivity;
import org.kuska.bscapp.util.LocaleManager;
import org.kuska.bscapp.R;
import org.kuska.bscapp.feature.detail.NoteDetailActivity;
import org.kuska.bscapp.models.Note;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public class HomeActivity extends BaseActivity implements HomeView {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Note> notes;
    @Inject public HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);

        initLayout();

        homePresenter.attachView(this);
        homePresenter.getNotes();
    }

    private void initLayout() {
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                Note note = new Note();
                // TODO let BE decide which ID assign to a new note
                String id = getNewId(notes);
                note.setId(id);
                homePresenter.createNote(this, note);
                return true;
            case R.id.settings:
                showLanguagePicker();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getNewId(List<Note> notes) {
        Integer id = 0;
        if (notes != null && notes.size() > 0) {
            for (int i = 0; i < notes.size(); i++) {
                if (String.valueOf(id).equals(notes.get(i).getId())) {
                    id++;
                    i = -1;
                }
            }
        }
        return String.valueOf(id);
    }

    protected void showLanguagePicker() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_language_title)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                setNewLocale(LocaleManager.LANGUAGE_ENGLISH);
                                break;
                            case 1:
                                setNewLocale(LocaleManager.LANGUAGE_CZECH);
                                break;
                        }
                        dialog.dismiss();
                    }})
                .show();
    }

    private boolean setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        return true;
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
    public void setData(List<Note> notes) {
        this.notes = notes;
        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), notes);
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                final Intent intent = NoteDetailActivity.buildIntent(HomeActivity.this, note);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
