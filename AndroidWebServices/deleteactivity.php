<?php
require_once "connect.php";
$id = $_REQUEST['id'];
$sql = "DELETE FROM `activities` WHERE `id` = $id";
if (mysqli_query($connect, $sql)) {
    echo json_encode($message = array("success" => true));
} else {
    echo json_encode($message = array("error" => false));
}
