package com.example.simplegpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class simpleDally extends AppCompatActivity {

    EditText message_edit;
    ImageButton homebtn,sendButton;
    TextView welcome_text;
    RecyclerView recycler_view;
    List<Message> messageList;
    ImageAdapter imageAdapter;

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_dally);

        recycler_view = findViewById(R.id.recycler_view);
        welcome_text = findViewById(R.id.welcome_text);
        message_edit = findViewById(R.id.message_edit);
        sendButton = findViewById(R.id.sendButton);
        homebtn = findViewById(R.id.homeBtn2);

        messageList = new ArrayList<>();

        // setup recycler view
        imageAdapter = new ImageAdapter(messageList);
        recycler_view.setAdapter(imageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recycler_view.setLayoutManager(llm);

        // back to home button
        homebtn.setOnClickListener((v)->{
            openHome();
        });

        // on click send call api
        sendButton.setOnClickListener((v)->{
            String prompt = message_edit.getText().toString().trim();
            //Toast.makeText(this,Message.SENT_BY_ME,Toast.LENGTH_LONG).show();
            addToChat(prompt,Message.SENT_BY_ME);
            message_edit.setText("");
            callApi(prompt);
            welcome_text.setVisibility(View.GONE);
        });
    }

    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                // update recyclerview
                imageAdapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(imageAdapter.getItemCount());
            }
        });
    }

    public void openHome() {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        //System.out.println(response);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callApi(String prompt){

        messageList.add(new Message("Drawing...",Message.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","dall-e-2");
            jsonBody.put("prompt",prompt);
            jsonBody.put("size","512x512");
            jsonBody.put("quality","standard");
            jsonBody.put("n",1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // create request body
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to \n"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        // return response to app
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        String result = jsonArray.getJSONObject(0).getString("url") + " ";
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    addResponse("Failed to load response due to \n"+response.body().string());
                }
            }
        });

    }
}