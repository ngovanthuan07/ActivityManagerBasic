<?php
require_once "connect.php";
$title = $_REQUEST['title'];
$content = $_REQUEST['content'];
$costactivities = $_REQUEST['costactivities'];
$quantity = $_REQUEST['quantity'];
$status = $_REQUEST['status'];
$photo = $_REQUEST['photo'];
// format date

$datestart = date('Y-m-d', strtotime($_REQUEST['datestart']));
$dateend = date('Y-m-d', strtotime($_REQUEST['dateend']));
$createdby = $_REQUEST['createdby'];
$modifiedby = $_REQUEST['modifiedby'];

// tao request
$response = array();




$sql = "INSERT INTO `activities`(`id`, `title`, `content`, `costactivities`, `quantity`, `status`, `photo`, `datestart`, `dateend`, `createdby`, `modifiedby`) 
                        VALUES (null,'$title','$content','$costactivities','$quantity','$status','$photo','$datestart','$dateend','$createdby','$modifiedby')" ;
if (mysqli_query($connect, $sql)) {
    echo json_encode($response = array("success"=> true));
} else {
    echo json_encode($response = array("error"=> 'Can not crete user'));
}
