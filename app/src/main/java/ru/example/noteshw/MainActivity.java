package ru.example.noteshw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import ru.example.noteshw.NotesArrayFragmentNew;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addFragment(NotesArrayFragmentNew.newInstance());
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, //следит за открытием и закрытием дравера
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); //для красивого открывания/закрывания иконки

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
                return false;
        });
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_add:
                Toast.makeText(MainActivity.this, "Add button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_view:
                Toast.makeText(MainActivity.this, "View button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_backup:
                Toast.makeText(MainActivity.this, "Backup button pressed", Toast.LENGTH_SHORT).show();
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "Settings button pressed", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //обработка нажатий уехала отсюда в navigateFragment
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
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