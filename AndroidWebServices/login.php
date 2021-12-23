<?php

require_once "connect.php";

if(isset($_REQUEST['username']) && $_REQUEST['password']){
    $username = $_REQUEST['username'];
    $password = $_REQUEST['password'];
    // tao class user
    $user = new stdClass();
    $response = array();
    // tim tai khoan
    $sql = "SELECT * FROM `users` WHERE `username` = '$username' AND `password` =  '$password'" ;

    if ($data = mysqli_query($connect, $sql)){
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
        $response = array("success"=>true,"user" => $user);
        echo json_encode($response);
    }else{
        echo  json_encode($response = array("error"=>'No Find User'));
    }
   


}else {
    echo json_encode($response = array("error"=> 'No Data'));
}

