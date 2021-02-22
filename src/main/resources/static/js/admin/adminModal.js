/* 输出后台管理模块时调用的方法 */
/*输出用户*/
$.printUser = function(user) {
	return "<tr><td>" + user.uId + "</td>" +
		   "<td>" + user.uName + "</td>" +
		   "<td>" + user.uPassword + "</td>" +
		   "<td>" + user.uTel + "</td>" +
		   "<td>" + user.uEmail + "</td>" +
		   "<td>" + user.uTime + "</td>" +
		   "<td>" + user.uStatus + "</td>" + 
		   "<td><button id='updUserSta' class='btn btn-primary btn-xs'>修改状态</button>&nbsp;" +
		   "<button id='delUser' class='btn btn-danger btn-xs'>删除</button></td></tr>";
}

/*输出店铺*/
$.printStore = function(store) {
	return "<tr><td>" + store.stoId + "</td>" +
		   "<td>" + store.stoName + "</td>" + 
		   "<td>" + store.stoTime + "</td>" +
		   "<td>" + store.stoAddress + "</td>" + 
		   "<td>" + store.uid + "</td>" +
		   "<td>" + store.stoStatus + "</td>" + 
		   "<td><button id='updStoSta' class='btn btn-primary btn-xs'>修改状态</button>&nbsp;" +
		   "<button id='delSto' class='btn btn-danger btn-xs'>删除</button></td></tr>"
}

/* 设置修改状态模态框中的标签属性 */
$.updStatCont = function(thisTr, modalId) {
	$("#updStaIndex").val(thisTr.index());
	$("#updTabName").val(thisTr.parents("table").attr("id"));
	$("#updStatus .modal-footer input:eq(0)").attr("id", modalId);
	$("#updStatus").modal("show");
}