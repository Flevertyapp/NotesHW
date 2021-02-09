package ru.example.noteshw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class NoteDescriptionFragment extends Fragment {

    static final String ARG_INDEX = "index";  //key
    private int index;                        //value
    private Note note;

    public NoteDescriptionFragment() {
        // Required empty public constructor
    }

    public static NoteDescriptionFragment newInstance(Note note) {
        NoteDescriptionFragment fragment = new NoteDescriptionFragment(); //создание
        Bundle args = new Bundle();       // передача данных
        args.putParcelable(ARG_INDEX, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_INDEX);  //сюда передаем ключ из NotesArrayFragment
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_description, container, false);
        setHasOptionsMenu(true);

        TextView tvName = view.findViewById(R.id.name_view);
        tvName.setText(note.getName());

        TextView tvDescription = view.findViewById(R.id.description_view);
        tvDescription.setText(note.getDescription());

        TextView tvDate = view.findViewById(R.id.date_view);
        tvDate.setText(note.getDate());
        return view;
    }

}