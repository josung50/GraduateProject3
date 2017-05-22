<?php
/**
 * Created by PhpStorm.
 * User: choi
 * Date: 2017-05-22
 * Time: 오후 8:40
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

$NOW_DATE = date("YmdHi");

$_RECEIVER = urldecode($_POST["RECEIVER"]);
$_SENDER_SEQ = urldecode($_POST["MEMBER_SEQ"]);
$_MESSAGE = urldecode($_POST["MESSAGE"]);

$_REGISTER_TIME= $NOW_DATE;

$receiver_search_query = mysqli_query($link, "select * from t_member where email LIKE \"%$_RECEIVER%\"");
$receiver_search_row = mysqli_fetch_array($receiver_search_query);

$_SENT = "N";

if($receiver_search_row != null){
    $send_msg_query = mysqli_query($link, "insert into t_talk(contents, receive_seq, send_seq, register_time)
                values(\"$_MESSAGE\", $receiver_search_row[seq], $_SENDER_SEQ, \"$_REGISTER_TIME\")");
    $send_msg_row = mysqli_fetch_row($send_msg_query);
    $_SENT = "Y";
}
echo "$_SENT";

?>