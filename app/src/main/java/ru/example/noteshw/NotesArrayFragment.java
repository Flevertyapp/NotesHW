package ru.example.noteshw;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NotesArrayFragment extends Fragment {
    private static final String CURRENT_NOTE = "CurrentNote"; //конста для запоминания выбранной заметки
    private int currentNote = 0;  //текущая заметка
    private boolean isLandscape; //ориентация

    public NotesArrayFragment() {
        // Required empty public constructor
    }


    public static NotesArrayFragment newInstance(String param1, String param2) {
        NotesArrayFragment fragment = new NotesArrayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //проверка ориентации, активити уже создана
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getInt(CURRENT_NOTE, 0);
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
    }

    private void initList(View view) {          //создаем список заметок
        LinearLayout layoutView = (LinearLayout) view;  //создание текствью
        Context context = getContext(); //получаем контекст для проверки если активити уже закрылась
        String[] names = getResources().getStringArray(R.array.names);
        for (int i = 0; i < names.length; i++) {        //заполнение значениями из массива
            if (context != null) {        //проверка чтоб не поймать НПО
                String name = names[i];
                TextView tv = new TextView(getContext());
                tv.setText(name);
                tv.setTextSize(30);
                layoutView.addView(tv);//вывод вью на экран
                final int fi = i;      //вешаем листенер на элемент списка и узнаем индекс массива
                tv.setOnClickListener(v -> {
                    currentNote = fi;
                    showNote(currentNote);
                });
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) { //сохраняем текущую позицию перед выходом из фрагмента
        outState.putInt(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void showNote(int index) { //вызываем метод в зависимости от ориентации
        if (isLandscape) {
            showLandNoteDescription(index);
        } else {
            showPortNoteDescription(index);
        }
    }


    private void showPortNoteDescription(int index) { //портретная
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, NotesActivity.class);
            intent.putExtra(NoteDescriptionFragment.ARG_INDEX, index); //передаем в нее фрагмент с описанием заметки
            startActivity(intent);
        }
    }

    private void showLandNoteDescription(int index) { //ландшафтная
        NoteDescriptionFragment detail = NoteDescriptionFragment.newInstance(index); //создаем фрагмент
        //транзакция по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_descr_view, detail); //замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

}