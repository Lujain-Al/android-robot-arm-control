<?php
$conn = new mysqli("localhost", "root", "", "robot_arm_db");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "UPDATE run_status SET status = 0 WHERE id = 1";

if ($conn->query($sql) === TRUE) {
    echo "Status updated successfully.";
} else {
    echo "Error: " . $conn->error;
}

$conn->close();
?>
