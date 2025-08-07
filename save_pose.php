<?php
header('Content-Type: application/json');

// الاتصال بقاعدة البيانات
$conn = new mysqli("localhost", "root", "", "robot_arm_db");

if ($conn->connect_error) {
    die(json_encode(["status" => "error", "message" => "DB connection failed"]));
}

// التأكد من توفر كل القيم
if (isset($_POST["motor1"], $_POST["motor2"], $_POST["motor3"], $_POST["motor4"])) {
    $m1 = $_POST["motor1"];
    $m2 = $_POST["motor2"];
    $m3 = $_POST["motor3"];
    $m4 = $_POST["motor4"];

    $sql = "INSERT INTO poses (motor1, motor2, motor3, motor4) VALUES ('$m1', '$m2', '$m3', '$m4')";
    
    if ($conn->query($sql)) {
        echo json_encode(["status" => "success"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Insert failed"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Missing parameters"]);
}

$conn->close();
?>
