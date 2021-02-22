/**
 * 后台---用户管理面板的事件
 */
$(function() {
	/*页面加载时发送ajax请求，获取第一页用户的数据*/
	$.cookie("where", null, {path:"/"});
	$.pageNav("userManageNav", true, $.printUserList);
	
	/* "后台管理---用户管理搜索按钮" */
	$("#user form span").click(function() {
		var userName = $("#user input").val();
		if(userName == ""){
			alert("请输入要查询的用户的名称");
		} else {
			$.cookie("where", "&uName=" + userName, {path:"/"});
			$.pageNav("userManageNav", true, $.printUserList);
		}
	});
	
	/* 修改用户状态按钮 */
	var id;
	$("#userTab").on("click", "#updUserSta", function() {
		var thisTr = $(this).parents("tr");
		id = thisTr.find("td:eq(0)").text();
		$.updStatCont(thisTr, "updUserStaBtn");
	});
	
	/* 修改状态请求 */
	$("#updStatus").on("click", "#updUserStaBtn", function() {
		var staCode = $("#updStatus input:checked").val();
		$.updStaAjax("updUserSta", id, staCode, true);
		id = null;
	});
	
	/* 删除按钮 */
	$("#userTab").on("click", "#delUser", function() {
		if(confirm("是否要将该用户的状态设置成注销！")){
			var id = $(this).parents("tr").find("td:eq(0)").text();
			$.updStaAjax("updUserSta", id, -2, true);
		}
	});
});