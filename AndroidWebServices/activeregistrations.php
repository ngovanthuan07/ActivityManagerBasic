<?php 
require_once "connect.php";
// tao mang
$response = array();

// tao list activityid

$listActivity = array();
$sql = "SELECT `activityid` FROM `activeregistrations` GROUP BY `activityid`";
if($getActivityId = (mysqli_query($connect, $sql))){
    // get activity
    while($rowActivityId = mysqli_fetch_assoc($getActivityId)){
        $idActivity = $rowActivityId['activityid'];
        $sqlActivity = "SELECT * FROM `activities` WHERE `id` =  $idActivity";
        if($activityQuery = mysqli_query($connect,$sqlActivity)){
             // lay 1 activity
            while(($rows = mysqli_fetch_assoc($activityQuery))){
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
    
                // list users
                $users = array();
                // lay list user id
                $sqlRegisterUser = "SELECT `userid`, `timeregistration` FROM `activeregistrations` WHERE `activityid` = $idActivity";
                if($userRegisterQuery = mysqli_query($connect, $sqlRegisterUser)){
                    while($rowUserRegister = mysqli_fetch_assoc($userRegisterQuery)){
                        $userId = $rowUserRegister['userid'];
                        $sqlQueryUser = "SELECT * FROM `users` WHERE `id` = $userId";
                        // lay 1 user va them vao list
                        if($dataUser = mysqli_query($connect, $sqlQueryUser)){
                            while (($rowUser = mysqli_fetch_assoc($dataUser)) != null) {
                                $user = new stdClass();
                                $user->id = $rowUser['id'];
                                $user->username = $rowUser['username'];
                                $user->password = $rowUser['password'];
                                $user->fullname = $rowUser['fullname'];
                                $user->photo = $rowUser['photo'];
                                $user->status = $rowUser['status'];
                                $user->role = $rowUser['role'];
                                $user->timeregistration = date("d-m-Y", strtotime($rowUserRegister['timeregistration']));
                                array_push($users, $user);
                            } 
                        }
                    }
                }
                $activity->users = $users;
                array_push($listActivity, $activity);
                break;
            }
            
        }
    }

    $response = array("success" => true, "activitys" => $listActivity);
    echo json_encode($response);
}
else{
    echo json_encode(array("success" => false));
}