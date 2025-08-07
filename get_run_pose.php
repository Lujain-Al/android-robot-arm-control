<?php
header('Content-Type: application/json');

$conn = new mysqli("localhost", "root", "", "robot_arm_db");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM poses ORDER BY id DESC";
$result = $conn->query($sql);

$poses = [];

while($row = $result->fetch_assoc()) {
    $poses[] = $row;
}

echo json_encode($poses);
$conn->close();
?>
