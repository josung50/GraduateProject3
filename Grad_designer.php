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
$_CODE = "null";
$_CODE = urldecode($_POST['CODE']);
//디자이너 고유번호,
$query = mysqli_query($link,"select mem_deprcali.seq, mem_deprcali.uname, mem_deprcali.image_url, mem_deprcali.category_name, mem_deprcali.comments,
	mem_deprcali.U_count, mem_deprcali.Received_like, R_viewed.View_count
from
	(select * from
		(select mem_with_Udesign.seq, mem_with_Udesign.uname, mem_with_Udesign.image_url, user_cate.category_name, mem_with_Udesign.comments, mem_with_Udesign.U_count
		from
			(select mem.seq, mem.uname, mem.image_url, count(mem.seq) as U_count, mem.comments
			from t_member mem left outer join t_design_work up_design on mem.seq = up_design.member_seq
			where up_design.del_flag = 'N'
			group by mem.seq) mem_with_Udesign,
			(select t_mc.member_seq, t_mc.category_code, t_c.category_name
			from t_member_category t_mc, t_category t_c
			where t_mc.category_code = t_c.category_code
			and t_mc.category_code like \"$_CODE%\") user_cate
			where mem_with_Udesign.seq = user_cate.member_seq) mem_deprca left outer join
			(select td.member_seq, sum(ifnull(tdl.like_count, 0)) as Received_like
			from t_design_work td left outer join 
				(select design_work_seq, count(*) as like_count
				from t_design_work_like
				group by design_work_seq) tdl on td.seq = tdl.design_work_seq
			where td.del_flag = 'N'
			group by member_seq) R_like on R_like.member_seq = mem_deprca.seq) mem_deprcali left outer join
	(select td.member_seq, sum(td.view_cnt) as View_count
	from t_design_work td
	group by td.member_seq) R_viewed on mem_deprcali.seq = R_viewed.member_seq
	
");

//echo "$_CODE tester";
while($row = mysqli_fetch_array($query))
{
    echo "<br>";
    echo "$row[seq]a!PJP";
    echo "$row[uname]a!PJP";
    echo "$row[image_url]a!PJP";
    echo "$row[category_name]a!PJP";
    echo "$row[comments]a!PJP";
    echo "$row[U_count]a!PJP";
    echo "$row[View_count]a!PJP";
    echo "$row[Received_like]a!PJP";
    $inner_query = mysqli_query($link, "select t_design_work.seq, t_design_work.title, t_design_work.thumb_uri
    from t_design_work where t_design_work.member_seq = $row[seq]");
    while($inner_row = mysqli_fetch_array($inner_query)){
        echo"$inner_row[seq]&";
        echo"$inner_row[title]&";
        echo"$inner_row[thumb_uri]::";
    }
}

$_CODE = "null";

?>