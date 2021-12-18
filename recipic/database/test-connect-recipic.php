<?php
$servername = "localhost";
$database = "u692615542_recipic_v1";
$username = "u692615542_admin_recipic";
$password = "9jiKjn48qyYK/Hn";
// Create connection
$conn = mysqli_connect($servername, $username, $password, $database);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
echo "Connected successfully";
mysqli_close($conn);
?>