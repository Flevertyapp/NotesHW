package ru.example.noteshw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class NotesArrayFragment extends Fragment {
    private static final String CURRENT_NOTE = "CurrentNote"; //конста для запоминания выбранной заметки
    //private int currentNote = 0;  //текущая заметка
    private boolean isLandscape; //ориентация
    private Note currentNote;
    private ArrayList<Note> notes;

    public NotesArrayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //проверка ориентации, активити уже создана
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        }
        if (isLandscape) {
            showLandNoteDescription(currentNote);
        }
    }

    @Override  //здесь указывается макет фрагмента при создании
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes_array, container, false);

    }

    @Override   //здесь инициализируется список через initList
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        //initPopupMenu(view); //инициализация popup menu //падаем(((
    }

    private void initPopupMenu(View view) { //создание  popup menu
        TextView text = view.findViewById(R.id.notes_arr_view); //падаем, здесь, надо как-то динамически вытягивать ID
        text.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v); //если импортировать не androidX, то ругается
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu()); //надуваем
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { //вешаем листенер
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.action_delete:
                            Toast.makeText(getContext(), "Delete button pressed", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.action_settings:
                            Toast.makeText(getContext(), "Settings button pressed", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.action_backup:
                            Toast.makeText(getContext(), "Backup button pressed", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return true;
                }
            });
            popupMenu.show();
        });
    }

    private void initList(View view) {          //создаем список заметок
        LinearLayout layoutView = (LinearLayout) view;  //создание текствью
        String[] names = getResources().getStringArray(R.array.names);
        String[] descr = getResources().getStringArray(R.array.description);
        String[] dates = getResources().getStringArray(R.array.dates);

        LayoutInflater inflater= getLayoutInflater();

        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Note tempNote = new Note(names[i], descr[i], dates[i]);
            notes.add(tempNote);
            View item = inflater.inflate(R.layout.item, layoutView,false); //переделываем список через итемы
            TextView tv = item.findViewById(R.id.item);
            tv.setText(notes.get(i).getName());
            layoutView.addView(item);
            tv.setOnClickListener(v -> {
                currentNote = tempNote;
                showNote(currentNote);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) { //сохраняем текущую позицию перед выходом из фрагмента
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void showNote(Note currentNote) { //вызываем метод в зависимости от ориентации
        if (isLandscape) {
            showLandNoteDescription(currentNote);
        } else {
            showPortNoteDescription(currentNote);
        }
    }

    private void showPortNoteDescription(Note currentNote) { //портретная
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, NotesActivity.class);
            intent.putExtra(NoteDescriptionFragment.ARG_INDEX, currentNote); //передаем в нее фрагмент с описанием заметки
            startActivity(intent);
        }
    }

    private void showLandNoteDescription(Note currentNote) { //ландшафтная
        NoteDescriptionFragment detail = NoteDescriptionFragment.newInstance(currentNote); //создаем фрагмент
        //транзакция по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_descr_view, detail); //замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}

