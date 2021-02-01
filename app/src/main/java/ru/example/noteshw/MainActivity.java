package ru.example.noteshw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:Toast.makeText(MainActivity.this, "Add button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_view:Toast.makeText(MainActivity.this, "View button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_backup :Toast.makeText(MainActivity.this, "Backup button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_settings :Toast.makeText(MainActivity.this, "Settings button pressed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // аналог onCreateView фрагмента
    public boolean onCreateOptionsMenu(Menu menu) { //создание меню, строка поиска всегда выглядит так, делает операционка
        getMenuInflater().inflate(R.menu.main, menu); //надуваем меню
        MenuItem search = menu.findItem(R.id.action_search);    //ищем поиск
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // вешаем листенер на поле поиска
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) { //нажали "искать"
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {  // реагирует на нажатие каждой буквы или символа
                Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show(); //тост для себя
                return true;
            }
        });

        return true;
    }

}