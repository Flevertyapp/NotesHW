package ru.example.noteshw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();       //если повернули в альбомную, эта активити закрывается, для ланд другая активити
            return;
        }

        if (savedInstanceState == null) {  //проверка открывается первый раз или нет
            //NotesArrayFragment details = new NotesArrayFragment();     //здесь можно не тот фрагмент вызвать
            NoteDescriptionFragment details = new NoteDescriptionFragment(); //что я и сделал(((
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, details).commit();
        }
    }
}