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
    private final OnLongUserClickListener onClickListener;
    public UserAdapter(Context context, List<User> users, OnLongUserClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.users = users;
        this.onClickListener = onClickListener;
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClickListener.onLongUserClick(view);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public User getItem(int position) {
        return users.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName, textViewNumber;
        public ViewHolder(@NonNull View view) {
            super(view);
            textViewName = view.findViewById(R.id.itemName);
            textViewNumber = view.findViewById(R.id.itemNumber);
        }
    }

    public interface OnLongUserClickListener{
        void onLongUserClick(View view);
    }
}
