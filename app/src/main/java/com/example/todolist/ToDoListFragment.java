package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.note_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
}

