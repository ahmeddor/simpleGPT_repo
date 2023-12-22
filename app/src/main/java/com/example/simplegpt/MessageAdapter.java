package com.example.simplegpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    List<Message> messageList;
    // constructor
    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder viewHolder = new MyViewHolder(chatView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        // if message is sent by me hides bot box and show mines and vice versa
        if(message.getSentBy().equals(Message.SENT_BY_ME)){
            holder.lChatview.setVisibility(View.GONE);
            holder.rChatview.setVisibility(View.VISIBLE);
            holder.rTextview.setText(message.getMessage());
        }else{
            holder.rChatview.setVisibility(View.GONE);
            holder.lChatview.setVisibility(View.VISIBLE);
            holder.lTextview.setText(message.getMessage());
        }
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout lChatview,rChatview;
        TextView lTextview,rTextview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lChatview = itemView.findViewById(R.id.l_chat_view);
            rChatview = itemView.findViewById(R.id.r_chat_view);
            lTextview = itemView.findViewById(R.id.l_text_view);
            rTextview = itemView.findViewById(R.id.r_text_view);
        }
    }
}
