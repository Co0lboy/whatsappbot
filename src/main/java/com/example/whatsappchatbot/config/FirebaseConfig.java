package com.example.whatsappchatbot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() throws IOException {
        InputStream serviceAccount;

        // 🔍 Check if running on cloud (Render) or local
        String credPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credPath != null) {
            // 🟢 Running on Render: load from environment path
            serviceAccount = new FileInputStream(credPath);
            System.out.println("🔁 Loading Firebase credentials from environment variable path.");
        } else {
            // 🟢 Local: load from resources folder
            serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("firebase-service-account.json");
            System.out.println("🧪 Loading Firebase credentials from resources.");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized!");
        }
    }
}