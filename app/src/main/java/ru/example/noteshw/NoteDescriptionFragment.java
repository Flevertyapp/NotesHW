package ru.example.noteshw;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteDescriptionFragment extends Fragment {

    static final String ARG_INDEX = "index";  //key
    private int index;                        //value

    public NoteDescriptionFragment() {
        // Required empty public constructor
    }


    public static NoteDescriptionFragment newInstance(int index) {
        NoteDescriptionFragment fragment = new NoteDescriptionFragment(); //создание
        Bundle args = new Bundle();       // передача данных
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);  //сюда передаем ключ из NotesArrayFragment
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();  //а так корректно вытаскивать данные из массива?
        TextView tvName = view.findViewById(R.id.name_view);
        //String stringName = res.getStringArray(R.array.names)[index];
        tvName.setText(getResources().getStringArray(R.array.names)[index]);  //не показывает
        TextView tvDescription = view.findViewById(R.id.description_view);
        String stringDescription = res.getStringArray(R.array.description)[index];
        tvDescription.setText(stringDescription);
        TextView tvDate = view.findViewById(R.id.date_view);
        String stringDate = res.getStringArray(R.array.dates)[index];
        tvDate.setText(stringDate);
    }
}