package com.example.todolist;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteBook {

    private static NoteBook sNoteBook;
    private Context mAppContext;
    private List<Note> mNotes;

    public static NoteBook get(Context context) {
        if (sNoteBook == null) {
            sNoteBook = new NoteBook(context.getApplicationContext());
        }
        return sNoteBook;
    }

    private NoteBook (Context context) {
        mAppContext = context;
        mNotes = new ArrayList<Note>();

        // populate with test data
        for (int i = 0; i < 100; i++) {
            Note note = new Note();
            note.setTitle("Note #" + i);
            note.setDone(i % 2 == 0);
            mNotes.add(note);
        }
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    public Note getNote(UUID id) {
        for (Note note : mNotes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }

}
