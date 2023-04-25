package com.example.recycleris21.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleris21.R;
import com.example.recycleris21.data.User;
import com.example.recycleris21.data.UserAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private User CurrentUser;
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
        UserAdapter.OnLongUserClickListener userClickListener = view -> {
            int position = recyclerView.getChildAdapterPosition(view);
            CurrentUser = adapter.getItem(position);
            showMenu(view);
        };
        //------------------------------------------------------------------------------------------------------
        adapter = new UserAdapter(this, getUsers(), userClickListener); // обязательно передаём в конструктор созданный обработчик userClickListener
        textViewCount = findViewById(R.id.itemsCount);
        textViewCount.setText("Всего пользователей " + adapter.getItemCount());

        recyclerView = findViewById(R.id.lisItems);
        recyclerView.setAdapter(adapter);
    }

    private void recountUsers() {
        textViewCount.setText("Всего пользователей " + adapter.getItemCount());
    }

    //-------вызов всплывающего меню и обработка нажатий на пункты-------
    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu); // Здесь popup_menu - это файл ресурсов с описанием меню
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit:
                    onEditUser(view, this.CurrentUser.getName(), this.CurrentUser.getNumber());
                    Toast.makeText(MainActivity.this, "Редактировать", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    onDeleteUser();
                    Toast.makeText(MainActivity.this, "Удалить", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
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
        addDialog.findViewById(R.id.buttonAdd).setOnClickListener(view1 -> {
            users.add(new User(editTextName.getText().toString(), editTextNumber.getText().toString()));
            Toast.makeText(MainActivity.this, "Пользователь успешно добавлен!", Toast.LENGTH_SHORT).show();
            recountUsers();
            addDialog.hide();
        });

        addDialog.show();
    }

    public void onEditUser(View view, String nameOfUser, String numberOfUser) {
        Dialog addDialog = new Dialog(this, R.style.Theme_RecyclerIs21);

        addDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        addDialog.setContentView(R.layout.add_user_dialog);

        EditText editTextName = addDialog.findViewById(R.id.personName);
        EditText editTextNumber = addDialog.findViewById(R.id.personNumber);

        ((TextView) addDialog.findViewById(R.id.textView)).setText("Редактировать пользователя");

        editTextName.setText(this.CurrentUser.getName());
        editTextNumber.setText(this.CurrentUser.getNumber());

        //обработчик событий для кнопки
        addDialog.findViewById(R.id.buttonAdd).setOnClickListener(view1 -> {
            for (User user : users) {
                if(user.getNumber() == this.CurrentUser.getNumber() && user.getName() == this.CurrentUser.getName()){
                    user.setNumber(editTextNumber.getText().toString());
                    user.setName(editTextName.getText().toString());
                }
            }

            recyclerView.getAdapter().notifyDataSetChanged();
            addDialog.hide();
        });
        addDialog.show();
    }

    public void onDeleteUser() {
        users.remove(CurrentUser);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}