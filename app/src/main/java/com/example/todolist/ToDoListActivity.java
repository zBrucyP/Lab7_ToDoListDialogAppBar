package com.example.todolist;

import androidx.fragment.app.Fragment;

public class ToDoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ToDoListFragment();
    }
}
