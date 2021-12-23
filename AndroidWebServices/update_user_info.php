<?php
require_once "connect.php";
$username = $_REQUEST['username'];
$fullname = $_REQUEST['fullname'];
$photo = $_REQUEST['photo'];

// tao request
$response = array();
$id = -1;
$sql = "SELECT * FROM `users` WHERE `username` = '$username'";
if ($data = mysqli_query($connect, $sql)) {
    while (($rows = mysqli_fetch_assoc($data)) != null) {
        $id = $rows['id'];
        break;
    }
    $sql = "UPDATE `users` SET `fullname`='$fullname',`photo`='$photo' WHERE `id` = $id";
    if (mysqli_query($connect, $sql)) {
        $sql = "SELECT * FROM `users` WHERE `id` = $id";
        while (($rows = mysqli_fetch_assoc($data)) != null) {
            $fullname = $fullname['fullname'];
            $photo = $rows['photo'];
            break;
        }
        $response = array("success" => true, "photo" => $photo, 'fullname' => $fullname);
        echo json_encode($response);
    } else {
        echo json_encode($response = array("error" => 'Can Not Update User Info'));
    }
} else {
    echo json_encode($response = array("error" => 'No Find User'));
}
