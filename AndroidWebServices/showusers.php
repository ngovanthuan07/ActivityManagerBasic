<?php

require_once "connect.php";
class User
{
    var $id;
    var $username;
    var $password;
    var $fullname;
    var $photo;
    var $status;
    var $role;
    function __construct($id, $username, $password, $fullname, $photo, $status, $role)
    {
        $this->$id = $fullname;
        $this->$username = $username;
        $this->$password = $password;
        $this->$fullname = $fullname;
        $this->$photo = $photo;
        $this->$status = $status;
        $this->$role = $role;
    }
}
// tao mang
$mang = array();
// them vao mang
$sql = "SELECT * FROM `users`";

$data = mysqli_query($connect, $sql);
while (($rows = mysqli_fetch_assoc($data)) != null) {
    array_push($mang, new User($rows['id'], $rows['username'], $rows['password'], $rows['fullname'], $rows['photo'], $rows['status'], $rows['role']));
}
echo json_encode($mang);
