<?php
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

$_MARK = "null";
$_MARK = urldecode($_POST['MARK']);

$_JOIN_ID="null";
$_JOIN_ID = urldecode($_POST['JOIN_ID']);

$_U_NAME="null";
$_U_NAME= urldecode($_POST['U_NAME']);


$_TRUE=1;
$_FALSE=0;

switch($_MARK)
{
    case "1" :
          $query1 = mysqli_query($link,"select t_member.email from t_member where email=\"$_JOIN_ID\"");
          $row1=mysqli_fetch_array($query1); // ID 중복체크
          if($row1!=null)
            echo "$_TRUE";  // 중복
          else
            echo "$_FALSE"; // 중복이 아님
        break;

    case "2":
            $query2 = mysqli_query($link,"select t_member.uname from t_member where uname=\"$_U_NAME\"");
            $row2=mysqli_fetch_array($query2);
            if($row2!=null)
                echo $_TRUE; //중복
            else
                echo $_FALSE; // 중복이 아님
                // 닉네임 중복 체크
        break;
    case "3":

    case "4":

}

?>