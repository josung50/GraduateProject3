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
// 프로젝트 이름 / 프로젝트 생성자 / 프로젝트 생성자 seq / 프로젝트 썸네일경로 / 멤버수 / 파일수 / 프로젝트 고유 SEQ / 그룹 멤버 전원 SEQ<br>
$query = mysqli_query($link,"select * from (select distinct t_project.project_name,t_project.file_url as thumb_uri,t_member.uname, t_project.owner_seq, t_project.seq, t_project.register_time
from t_project,t_project_member,t_member
where t_member.seq=t_project.owner_seq
and t_project.del_flag = 'N'
and t_project.progress_status='P') t1,
(select t_project_subject.project_seq, count(*) as 'file_count' from t_project_work, t_project_subject
where t_project_work.project_subject_seq = t_project_subject.seq
and t_project_work.del_flag = 'N'
group by t_project_subject.project_seq)t2,
(select t_project.seq, count(t_project_member.member_seq) as 'mem_count'
from t_project, t_project_member
where t_project.seq = t_project_member.project_seq
group by t_project.seq)t3
where t2.project_seq = t1.seq
and t1.seq = t3.seq
order by t1.seq asc;");
//echo "$_CODE tester";
$p_count = 0;
while($row = mysqli_fetch_array($query))
{
    echo "<br>";
    echo "$row[project_name]#";
    echo "$row[uname]#";
    echo "$row[owner_seq]#";
    echo "$row[thumb_uri]#";
    echo "$row[mem_count]#";
    echo "$row[file_count]#";
    echo "$row[seq]#";
    $inner_query = mysqli_query($link, "select project_seq, member_seq from t_project_member where project_seq = $row[seq] order by project_seq asc, member_seq asc");
    while($inner_row = mysqli_fetch_array($inner_query)){
        echo "M$inner_row[member_seq]";
    }
    /*$p_count++;
    if($p_count == 4){
        exit();
    }*/
}
$_CODE = "NULL";

?>