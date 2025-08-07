# 🤖 Robot Arm Control Panel – Android + PHP + MySQL

This project is an Android application designed to control a robot arm. It connects to a local server using PHP and stores/retrieves motor positions (poses) from a MySQL database.

---

## 📱 Features
- 4 Motor sliders (0–180°)
- Save Pose (saves the current motor positions)
- Load Pose (retrieves last saved pose)
- Reset Status (sets run_status to 0)

---

## 🧱 Tech Stack
- Android Studio (Java)
- PHP (backend scripts)
- MySQL (XAMPP server)
- Volley (network library)

---

## 🗂 Folder Structure
robot/
├── save_pose.php
├── get_run_pose.php
├── update_status.php
└── robot_arm_db.sql


---

## ⚙️ Setup Instructions

### 1. 🖥️ Server (XAMPP)

1. Start **Apache** and **MySQL** in XAMPP.
2. Copy all PHP files into:  
   `C:\xampp\htdocs\robot\`
3. Open [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
4. Import the file `robot_arm_db.sql`.

---

### 2. 📱 Android App Setup

1. Open Android Studio.
2. Clone or import the project.
3. Add **Volley** to `build.gradle`:
   ```gradle
   implementation 'com.android.volley:volley:1.2.1'

### 3.  Replace server IP:
String serverUrl = "http://10.0.2.2/robot"; // for emulator
// or http://<your_PC_IP>/robot for real device
To find your PC's IP: run ipconfig in CMD.

### 4. Run the app on a real device or emulator.

✅ Testing
Move the sliders.

Click Save Pose → saves data to DB.

Click Load Pose → loads latest pose.

Click Reset → sets status = 0.

