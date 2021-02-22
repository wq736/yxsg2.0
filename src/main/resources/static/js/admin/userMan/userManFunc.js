/**
 * 后台---用户管理面板的函数
 */
$(function() {
	/* 输出用户列表使用 */
	$.printUserList = function(userList) {
		var t = "";
		for(var i in userList){
			t += $.printUser(userList[i]);
		}
		return t;
	};
});