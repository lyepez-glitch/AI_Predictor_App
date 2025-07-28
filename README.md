AI_Predictor_App
AI_Predictor_App was originally envisioned as a real-world motion sensor predictor app — something you might pair with smart glasses to anticipate opponents' moves in combat or help customer service agents predict conversation responses. In its current state, it functions as a ChatGPT-style AI interaction app, with user account features and AI-powered prediction responses.

🌐 Tech Stack
Frontend: Thymeleaf + Tailwind CSS

Backend: Spring Boot + JPA + PostgreSQL

Deployment: AWS (formerly Oracle Cloud Infrastructure), Render

CI/CD: Jenkins

AI Integration: OpenAI API

🚀 Features
🔐 User Authentication (Signup, Login, Reset Password)

🙍 User Profile Management (Edit Profile)

💬 Submit a Prediction and get an AI-generated response

🕒 View Prediction History

🗄️ SQL/JPA-based data persistence

⚙️ Setup Instructions
Prerequisites
Java 17+

Maven

Node.js & npm

PostgreSQL

OpenAI API Key

Environment Setup
Clone the Repository

bash
Copy
Edit
git clone https://github.com/lyepez-glitch/AI_Predictor_App.git
cd AI_Predictor_App
Set your OpenAI API key


export OPENAI_API_KEY=your_actual_api_key_here
Install Tailwind CSS


npm install
Build Tailwind Styles


npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --minify
Package and Run the App


mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
📂 Directory Overview

src/
├── main/
│   ├── java/          # Spring Boot backend code
│   ├── resources/
│   │   ├── static/    # Tailwind CSS files
│   │   └── templates/ # Thymeleaf templates
🛠 Development
Tailwind Dev Command


npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch
CI/CD

Automated with Jenkins (build & deployment to AWS/Render)

📄 License
MIT

