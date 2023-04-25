package com.example.recycleris21.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleris21.R;
import com.example.recycleris21.data.User;
import com.example.recycleris21.data.UserAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> users = new ArrayList<>();
    RecyclerView recyclerView;
    TextView textViewCount;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------ШАГ 4------------------------------------------------------------------------------------------
        // Инициализация обработчика событий длительного нажатия на элемент списка
        UserAdapter.OnLongUserClickListener userClickListener = new UserAdapter.OnLongUserClickListener() {
            @Override
            public void onLongUserClick( View view) {
                showMenu(view);
            }
        };
        //------------------------------------------------------------------------------------------------------
        adapter = new UserAdapter(this, getUsers(), userClickListener); // обязательно передаём в конструктор созданный обработчик userClickListener
        textViewCount = findViewById(R.id.itemsCount);
        textViewCount.setText("Всего пользователей " + adapter.getItemCount());

        recyclerView = findViewById(R.id.lisItems);
        recyclerView.setAdapter(adapter);
    }

    //-------вызов всплывающего меню и обработка нажатий на пункты-------
    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu); // Здесь popup_menu - это файл ресурсов с описанием меню

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        Toast.makeText(MainActivity.this, "Тут редактирование", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.delete:
                        Toast.makeText(MainActivity.this, "Тут удаление", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
    //---------------------------------------------------------------------
    private ArrayList<User> getUsers(){
        users.add(new User("Иван", "+79594446622"));
        users.add(new User("Михаил", "+79594446622"));
        users.add(new User("Максим", "+79594446622"));
        return users;
    }


    //метод вызывающий диалоговое окно доюавления записи
    public void onAddItem(View view) {
        Dialog addDialog = new Dialog(this, R.style.Theme_RecyclerIs21);
        addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        addDialog.setContentView(R.layout.add_user_dialog);

        EditText editTextName = addDialog.findViewById(R.id.personName);
        EditText editTextNumber = addDialog.findViewById(R.id.personNumber);

        //обработчик событий для кнопки
        addDialog.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.add(new User(editTextName.getText().toString(), editTextNumber.getText().toString()));
                Toast.makeText(MainActivity.this, "Пользователь успешно добавлен!", Toast.LENGTH_SHORT).show();
                addDialog.hide();
            }
        });

        addDialog.show();
    }
}