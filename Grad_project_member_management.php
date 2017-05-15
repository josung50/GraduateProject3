<?php
/**
 * Created by PhpStorm.
 * User: choi
 * Date: 2017-05-15
 * Time: 오후 3:00
 */
$servername = "localhost";
$username = "root";
$password = "";
$dbTable = "opendesign";
// Create connection
$link = mysqli_connect($servername, $username, $password, $dbTable);
mysqli_query($link, " SET NAMES UTF8");
if (!$link) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}
$_PROJECT_SEQ = "NULL";
$_PROJECT_SEQ = urldecode("PROJECT_SEQ");

?>