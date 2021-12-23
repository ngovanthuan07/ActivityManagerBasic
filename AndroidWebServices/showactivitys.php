<?php
require_once "connect.php";

// tao mang
$response = array();
$array = array();
// sql
$sql = "SELECT * FROM `activities`";
if($data = mysqli_query($connect, $sql)){
    while (($rows = mysqli_fetch_assoc($data)) != null) {
        $activity = new stdClass();
        $activity->id = $rows['id'];
        $activity->title =  $rows['title'];
        $activity->content =  $rows['content'];
        $activity->costactivities =  $rows['costactivities'];
        $activity->quantity = $rows['quantity'];
        $activity->status = $rows['status'];
        $activity->photo = $rows['photo'];
        $activity->datestart =  date("d-m-Y", strtotime($rows['datestart']));
        $activity->dateend =  date("d-m-Y", strtotime($rows['dateend']));
        $activity->createddate = date("d-m-Y", strtotime($rows['createddate']));
        $activity->modifieddate = date("d-m-Y", strtotime($rows['modifieddate']));
        $activity->createdby = $rows['createdby'];
        $activity->modifiedby = $rows['modifiedby'];
        // tao user
        $user = new stdClass();
        // lấy user
        $newsql = "SELECT * FROM `users` WHERE `username` = '$activity->createdby'";
        if ($newdate = mysqli_query($connect, $newsql)) {
            while (($rowUser = mysqli_fetch_assoc($newdate)) != null) {
                $user->id = $rowUser['id'];
                $user->username = $rowUser['username'];
                $user->password = $rowUser['password'];
                $user->fullname = $rowUser['fullname'];
                $user->photo = $rowUser['photo'];
                $user->status = $rowUser['status'];
                $user->role = $rowUser['role'];
                break;
            } 
        }
        $activity->user = $user;

        array_push($array,$activity);
    }
    $response = array("success"=>true,"activitys" => $array);
    echo json_encode($response);
} else{
    echo array("error"=>false);
}
