Here is a complete and beginner-friendly README.md for your WhatsApp Chatbot using Spring Boot and Render, with Firebase support and deployment instructions:

⸻


# 📱 WhatsApp Chatbot using Spring Boot + Firebase + Render

This is a simple backend application built using **Spring Boot**, which integrates with the **WhatsApp Cloud API** to send and receive messages. It also stores chat messages in **Firebase Firestore**.

Live on Render → [https://whatsappbot-j657.onrender.com](https://whatsappbot-j657.onrender.com)

---

## 🚀 Features

- ✅ Receive WhatsApp messages using a REST API
- ✅ Send replies via WhatsApp Cloud API
- ✅ Save chats in Firebase Firestore
- ✅ Deploy to [Render.com](https://render.com/)
- ✅ Dockerized + Maven Wrapper for easy deployment

---

## 🧱 Tech Stack

| Layer       | Tech                        |
|------------|-----------------------------|
| Backend     | Spring Boot (Java 17)       |
| API Client  | WhatsApp Cloud API (Meta)   |
| Database    | Firebase Firestore          |
| Hosting     | Render.com (free tier)      |
| Build Tool  | Maven + Docker              |

---

## 📦 API Endpoints

| Method | Endpoint                      | Description                        |
|--------|-------------------------------|------------------------------------|
| `GET`  | `/api/whatsapp`               | Health check endpoint              |
| `POST` | `/api/whatsapp/message`       | Receive and send WhatsApp messages |

Sample POST body:
```json
{
  "to": "9199xxxxxxxx",
  "message": "Hello from WhatsApp bot!"
}


⸻

🔐 Prerequisites

Before you start:
	1.	✅ Meta Developer Account
	2.	✅ A WhatsApp Business Phone Number ID
	3.	✅ A Permanent Access Token (Meta)
	4.	✅ Firebase Project with Service Account JSON
	5.	✅ Register your test number in Meta Dashboard

⸻

🛠️ Setup Locally
	1.	Clone the project:

git clone https://github.com/Co0lboy/whatsappbot.git
cd whatsappbot

	2.	Add Firebase config:
Place firebase-service-account.json inside:

src/main/resources/

	3.	Update application.properties:

# WhatsApp API
META_PHONE_NUMBER_ID=659917977208507
META_ACCESS_TOKEN=your_whatsapp_token

# Optional Firebase Config (used in cloud)
GOOGLE_APPLICATION_CREDENTIALS=src/main/resources/firebase-service-account.json

	4.	Run the project:

mvn clean install
java -jar target/whatsapp-chatbot-0.0.1-SNAPSHOT.jar


⸻

🌐 Deploy to Render
	1.	Push to GitHub

git init
git add .
git commit -m "Initial Commit"
git remote add origin https://github.com/<your-username>/whatsappbot.git
git push -u origin main

	2.	Create New Web Service on Render
	3.	Set Environment Variables:
| Key                            | Value                                                |
|––––––––––––––––|––––––––––––––––––––––––––––|
| GOOGLE_APPLICATION_CREDENTIALS | /etc/secrets/firebase-service-account.json         |
| META_ACCESS_TOKEN           | your-whatsapp-access-token                           |
	4.	Add Secret File:
| Path                                      | Content                              |
|—————————————––|—————————————|
| /etc/secrets/firebase-service-account.json | Paste the Firebase JSON here          |
	5.	✅ Deploy and test at:

https://your-service-name.onrender.com/api/whatsapp/message


⸻

📁 Project Structure

src/
 └── main/
     ├── java/com/example/whatsappchatbot/
     │   ├── controller/        # REST Controller
     │   ├── dto/               # Request/Response DTOs
     │   ├── service/           # Business Logic
     │   ├── model/             # ChatMessage Model
     │   ├── config/            # Firebase Config
     │   └── util/              # WhatsApp API Helper
     └── resources/
         ├── application.properties
         └── firebase-service-account.json


⸻

📸 Screenshot


⸻

🙌 Credits

Developed by Aniket Pawar
Built with ❤️ using Java, Spring Boot, Firebase, and Render

⸻

🧪 License

MIT License — feel free to use and modify.

---
