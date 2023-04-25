package com.example.recycleris21.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleris21.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<User> users;

    private final OnLongUserClickListener onClickListener; // ШАГ 2 - обязательно определяем даный объект типа нашего интерфейса
    public UserAdapter(Context context, List<User> users, OnLongUserClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.onClickListener = onClickListener; //продолжение ШАГА 2 - принимаем в конструкторе созданный в MainActivity
                                                // метод и назначаем его нашему полю onClickListener
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewNumber.setText(user.getNumber());

        //---- ШАГ 3 --- привязываем созданый метод к каждому элементу RecyclerView в момент генерации
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickListener.onLongUserClick(view); //тут вызывается 4-й шаг, а именно showMenu(view);
                //тут что-то....
                return false;
            }
        });
        //--------------------------------------------------------------------------------------------
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName, textViewNumber;
        public ViewHolder(@NonNull View view) {
            super(view);
            textViewName = view.findViewById(R.id.itemName);
            textViewNumber = view.findViewById(R.id.itemNumber);
        }
    }

    //------ШАГ 1 -------------------------------------------------------------------------------------------
    //добавлен интерфейс для метода, который будет срабатывать при нажатии на конкретный элемент нашего списка
    public interface OnLongUserClickListener{
        void onLongUserClick(View view);
    }
    //---------------------------------------------------------------------------------------------------------
}
