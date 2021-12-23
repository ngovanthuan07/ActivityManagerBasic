<?php 

require_once "connect.php";

$activity = new stdClass();
$activity->id = $_REQUEST['id'];
$activity->title =  $_REQUEST['title'];
$activity->content =  $_REQUEST['content'];
$activity->costactivities =  $_REQUEST['costactivities'];
$activity->quantity = $_REQUEST['quantity'];
$activity->status = $_REQUEST['status'];
$activity->photo = $_REQUEST['photo'];
$activity->datestart = date('Y-m-d', strtotime($_REQUEST['datestart']));
$activity->dateend = date('Y-m-d', strtotime($_REQUEST['dateend']));
$activity->createdby = $_REQUEST['createdby'];
$activity->modifiedby = $_REQUEST['modifiedby'];

$sql = "UPDATE `activities` 
        SET `title`=  '$activity->title', `content`='$activity->content', `costactivities`='$activity->costactivities',`quantity`='$activity->quantity',
            `status`='$activity->status', `photo`='$activity->photo', `datestart`='$activity->datestart',`dateend`='$activity->dateend',
            `createdby`='$activity->createdby',`modifiedby`='$activity->modifiedby' 
        WHERE `id` = $activity->id";
$response = array();
if($data = mysqli_query($connect, $sql))
{
    
    $sql = "SELECT * FROM `activities` WHERE `id`= $activity->id";
    if($data = mysqli_query($connect,$sql)){
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
            break;
            
        }
        $response = array("success"=>true,"activity" => $activity);
        echo json_encode($response);
    }else {
        echo json_encode($response = array("success"=>false));
    }
}
else{
    echo json_encode($response = array("success"=>false));
}

