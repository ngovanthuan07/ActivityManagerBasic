<?php
require_once "connect.php";
$username = $_REQUEST['username'];
$password = $_REQUEST['password'];
$fullname = isset($_REQUEST['fullname']) ? $_REQUEST['fullname'] : null;
$photo = isset($_REQUEST['photo']) ? $_REQUEST['photo'] : null;
// tao class user
$user = new stdClass();
// tao request
$response = array();

$sql = "INSERT INTO `users`(`id`,`username`, `password`, `fullname`, `photo`, `status`, `role`) 
       VALUES(null,'$username','$password','$fullname','$photo', 1, 'user')";
if (mysqli_query($connect, $sql)) {
    $sql = "SELECT * FROM `users` WHERE `username` = '$username' AND `password` =  '$password'" ;
    if($data = mysqli_query($connect, $sql)){
        while (($rows = mysqli_fetch_assoc($data)) != null) {
            $user->id = $rows['id'];
            $user->username = $rows['username'];
            $user->password = $rows['password'];
            $user->fullname = $rows['fullname'];
            $user->photo = $rows['photo'];
            $user->status = $rows['status'];
            $user->role = $rows['role'];
            break;
        }
        $response = array("success" => true, "user" => $user);
        echo json_encode($response);
    } else {
        echo json_encode($response = array("error"=>'No Find User'));
    }
} else {
    echo json_encode($response = array("error"=> 'Can not crete user'));
}
