package ru.example.noteshw;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ui.NoteAdapter;

public class NotesArrayFragmentNew extends Fragment {
    private boolean isLandscape; //ориентация
    private Note currentNote;

    public static androidx.fragment.app.Fragment newInstance() {
        return new NotesArrayFragmentNew();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_array, container,false);
        RecyclerView recyclerView= view.findViewById(R.id.notes_rw);
        String[]data= getResources().getStringArray(R.array.names);
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data) {
        recyclerView.setHasFixedSize(true);     //для повышения производительности
        //встроенный менеджер
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
            //установка адаптера
        NoteAdapter adapter=new NoteAdapter(data);
        recyclerView.setAdapter(adapter);
    }

}
