package com.example.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> myMessageList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView messageContent;
        ImageView avatar;
        TextView time;

        public ViewHolder(View view)
        {
            super(view);
            userName = view.findViewById(R.id.messageUserName);
            messageContent = view.findViewById(R.id.messageContent);
            avatar = view.findViewById(R.id.messageAvatar);
            time = view.findViewById(R.id.messageTime);
        }
    }

    public void messageAdapter(List<Message> messageList)
    {
        myMessageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount(){
        return myMessageList.size();
    }
}
