package com.example.chatbot;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        username = getIntent().getStringExtra("username");

        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Welcome message
        messageList.add(new Message("Welcome " + username + "!", false));
        chatAdapter.notifyItemInserted(messageList.size() - 1);

        sendButton.setOnClickListener(v -> {
            String userMsg = messageEditText.getText().toString().trim();
            if (!userMsg.isEmpty()) {
                messageList.add(new Message(userMsg, true));
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.scrollToPosition(messageList.size() - 1);
                messageEditText.setText("");

                // Call Llama2 API
                Llama2ApiService.sendMessage(userMsg, response -> {
                    runOnUiThread(() -> {
                        messageList.add(new Message(response, false));
                        chatAdapter.notifyItemInserted(messageList.size() - 1);
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    });
                });
            }
        });
    }
} 