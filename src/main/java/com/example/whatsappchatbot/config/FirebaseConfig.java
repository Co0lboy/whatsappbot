package com.example.whatsappchatbot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {
            InputStream serviceAccount;
            String credPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

            if (credPath != null && !credPath.isBlank()) {
                File credFile = new File(credPath);
                if (!credFile.exists()) {
                    throw new RuntimeException("❌ Firebase credential file not found at: " + credPath);
                }

                serviceAccount = new FileInputStream(credFile);
                System.out.println("🔁 Loading Firebase credentials from: " + credPath);
            } else {
                serviceAccount = getClass().getClassLoader()
                        .getResourceAsStream("firebase-service-account.json");

                if (serviceAccount == null) {
                    throw new RuntimeException("❌ firebase-service-account.json not found in resources.");
                }

                System.out.println("🧪 Loading Firebase credentials from local resources.");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase initialized!");
            }

        } catch (Exception e) {
            System.err.println("🔥 Firebase initialization failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
