<?php
require_once "connect.php";
    class profile{
        var $activityid;
        var $userid;
        var $timeregistration;

        function __construct($activityid,$userid,$timeregistration)
        {
            $this-> $activityid = $activityid;
            $this-> $userid = $userid;
            $this-> $timeregistration = $timeregistration;
        }
    }
    // tao mang
    $mang = array();
    // them vao mang
    $sql = "SELECT * FROM `activeregistrations`";
    $data = mysqli_query($connect,$sql);
    while (($rows = mysqli_fetch_assoc($data))!=null){
        array_push($mang, new profile($rows['activityid'],$rows['userid'], $rows['timeregistration']));
    }
    echo json_encode($mang);
