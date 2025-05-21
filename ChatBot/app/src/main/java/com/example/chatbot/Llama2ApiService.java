package com.example.chatbot;

public class Llama2ApiService {
    public static void sendMessage(String userMessage, Callback callback) {
        // Simulate a bot response with a simple auto-reply
        new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            String botReply = generateBotReply(userMessage);
            callback.onResponse(botReply);
        }).start();
    }

    private static String generateBotReply(String userMessage) {
        // Simple auto-reply logic (replace with real API call as needed)
        if (userMessage.toLowerCase().contains("hello")) {
            return "Hi there! How can I help you today?";
        } else if (userMessage.toLowerCase().contains("how are you")) {
            return "I'm just a bot, but I'm doing great!";
        } else if (userMessage.toLowerCase().contains("bye")) {
            return "Goodbye! Have a nice day!";
        } else {
            return "You said: " + userMessage + "\n(I'm a demo bot, ask me anything!)";
        }
    }

    public interface Callback {
        void onResponse(String response);
    }
} 