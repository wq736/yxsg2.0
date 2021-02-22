/**
 * 后台---店铺管理面板的事件
 */
$(function() {
	var isFirst = true;
	/*单击后台管理---店铺管理时获取第一页店铺的数据*/
	$("#manageTab a[href='#store']").click(function() {
		if(!isFirst){return;}
		$.cookie("pageCount", 0, {path:"/"});
		$.cookie("where", null, {path:"/"});
		$.pageNav("storeManageNav", true, $.printStoreList);
		isFirst = false;
	});
	
	/* 后台管理---店铺管理的搜索按钮 */
	$("#store form span").click(function() {
		var stoName = $("#store input").val();
		if(stoName == ""){
			alert("请输入要查询的店铺的名称");
		} else {
			$.cookie("where", "&stoName=" + stoName, {path:"/"});
			$.pageNav("storeManageNav", true, $.printStoreList);
		}
	});
	
	/* 修改店铺状态按钮 */
	var id;
	$("#storeTab").on("click", "#updStoSta", function() {
		var thisTr = $(this).parents("tr");
		id = thisTr.find("td:eq(0)").text();
		$.updStatCont(thisTr, "updStoStaBtn");
	});
	
	/* 修改状态请求 */
	$("#updStatus").on("click", "#updStoStaBtn", function() {
		var staCode = $("#updStatus input:checked").val();
		$.updStaAjax("updStoSta", id, staCode, true);
		id = null;
	});

	/* 删除按钮 */
	$("#storeTab").on("click", "#delSto", function() {
		if(confirm("是否要将该店铺的状态设置成注销！")){
			var id = $(this).parents("tr").find("td:eq(0)").text();
			$.updStaAjax("updStoSta", id, -2, true);
		}
	});
});