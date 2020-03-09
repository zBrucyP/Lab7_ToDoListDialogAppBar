package com.example.todolist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

import java.util.UUID;


public class NoteFragment extends Fragment {

    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mDoneCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID noteId = (UUID) getActivity().getIntent()
                .getSerializableExtra(NoteActivity.EXTRA_NOTE_ID);
        mNote = NoteBook.get(getActivity()).getNote(noteId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_note, container, false);

        mTitleField = (EditText) v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.setTitle((s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById((R.id.note_date));
        mDateButton.setText(mNote.getDate().toString());
        mDateButton.setEnabled(false);

        mDoneCheckBox = (CheckBox) v.findViewById(R.id.job_done);
        mDoneCheckBox.setChecked(mNote.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.setDone(isChecked);
            }
        });

        return v;
    }

}
