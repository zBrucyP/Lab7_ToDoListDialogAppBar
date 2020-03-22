package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.note_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        NoteBook noteBook = NoteBook.get(getActivity());
        List<Note> notes = noteBook.getNotes();

        if (mAdapter == null) {
            mAdapter = new NoteAdapter(notes);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    @Override
    public void onSaveInstanceState(Bundle outsState) {
        super.onSaveInstanceState(outsState);
        outsState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Note mNote;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_note, parent, false));

            itemView.setOnClickListener(this);
        }

        public void bind(Note note) {

            mNote = note;

            // set note title in layout
            TextView noteTitleTextView = itemView.findViewById(R.id.note_title_textView);
            noteTitleTextView.setText(note.getTitle());

            // set note date in layout
            TextView noteDateTextView = itemView.findViewById(R.id.note_date_textView);
            noteDateTextView.setText(note.getDate().toString());

            // set note done checkbox in layout
            CheckBox noteDoneCheckBox = itemView.findViewById(R.id.note_done_checkBox);
            noteDoneCheckBox.setChecked(note.isDone());
        }

        @Override
        public void onClick(View v) {
            Intent intent = NoteActivity.newIntent(getActivity(), mNote.getId());
            startActivity(intent);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @Override
        public NoteHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater layoutinflater = LayoutInflater.from(getActivity());

            return new NoteHolder(layoutinflater, parent); // idk if this is right
        }

        @Override
        public void onBindViewHolder (NoteHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Note note = new Note();
                NoteBook.get(getActivity()).addNote(note);
                Intent intent = NoteActivity.newIntent(getActivity(), note.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        NoteBook noteBook = NoteBook.get(getActivity());
        int noteCount = noteBook.getNotes().size();
        String subtitle = getString(R.string.subtitle_format, noteCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
}

