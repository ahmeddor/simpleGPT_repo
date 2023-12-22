package com.example.simplegpt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {


    List<Message> messageList;
    // constructor
    public ImageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,null);
        MyViewHolder viewHolder = new MyViewHolder(imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        // if message is sent by me hides bot box and show mines and vice versa
        if(message.getSentBy().equals(Message.SENT_BY_ME)){
            holder.lImageview.setVisibility(View.GONE);
            holder.rChatview.setVisibility(View.VISIBLE);
            holder.rTextview.setText(message.getMessage());
        }else{
            holder.rChatview.setVisibility(View.GONE);
            holder.lImageview.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(message.getMessage()).into(holder.limage);

        }
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout lImageview,rChatview;
        TextView rTextview;
        ImageView limage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lImageview = itemView.findViewById(R.id.l_image_view);
            rChatview = itemView.findViewById(R.id.r_chatImage_view);
            limage = itemView.findViewById(R.id.l_image);
            rTextview = itemView.findViewById(R.id.r_textImage_view);
        }
    }
}
