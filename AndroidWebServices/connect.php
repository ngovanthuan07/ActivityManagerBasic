<?php
    header("Content-type: text/html; charset=utf-8");

    $connect = mysqli_connect("localhost","root","","manageractivity");
    mysqli_query($connect, "SET NAME 'utf8'");
    
