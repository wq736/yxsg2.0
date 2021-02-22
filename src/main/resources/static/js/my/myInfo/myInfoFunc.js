/**
 * 个人信息面板的函数
 */
$(function() {
	var userStaInp = $("#myInfo div:eq(1) input[name='uStatus']");
	var thisUTime;
	/* 显示个人信息时调用 */ 
	$.addUserInfo = function(user, now) {
		var sta = user.uStatus;
		$("#myInfo .yue").text(user.uMoney.toFixed(2) + "元");
		$("#myInfo .yue").attr("data-money", user.uMoney.toFixed(2));
		$("#updPass input[name='oldPass']").val(user.uPassword);
		$("#myInfo div:first img").attr("src", user.uHeader);
		$("#myInfo div:first b").text("欢迎登录：" + user.uName + "用户");
		$("#myInfoId").val(user.uId);
		$("#myInfo div:eq(1) input[name='uName']").val(user.uName);
		$("#myInfo div:eq(1) input[name='uEmail']").val(user.uEmail);
		$("#myInfo div:eq(1) input[name='uTel']").val(user.uTel);
		$("#myInfo div:eq(1) input[name='uTime']").val(user.uTime);
		userStaInp.attr("data-status", sta);
		if(sta == "注销"){
			alert("您的账号已被注销！在删除账户之前先经余额全部取出，若有订单，则先将订单信息下载！");
			userStaInp.val(sta);
		} else if(sta != "正常" && sta != "永久禁用"){
			thisUTime = now;
			userStaInp.val("您的账号已被禁用：" + $.countDowun(sta, now) + "后解封！");
			setInterval($.flushUStatus, 1000);
		} else {
			userStaInp.val(sta);
		}
	}
	
	/* 修改用户余额 */
	$.updYue = function(yue){
		var yueSpan = $("#myInfo .yue");
		var oldYue = parseFloat(yueSpan.attr("data-money"));
		yueSpan.attr("data-money", (oldYue + parseFloat(yue)).toFixed(2));
		yueSpan.text(yueSpan.attr("data-money") + "元");
	}
	
	/* 每秒刷新状态的函数 */
	closeUFlush = false;
	$.flushUStatus = function() {
		if(!closeUFlush){
			var sta = userStaInp.attr("data-status");
			thisUTime += 1000;
			var val = "您的账号已被禁用：" + $.countDowun(sta, thisUTime) + "后解封！";
			userStaInp.val(val);
			if(val == "您的账号已被禁用：00天00时00分0-1秒后解封！"){
				$.updStaAjax("updUserSta", $("#myInfoId").val(), "-1", false);
				if(confirm("账号已经解封，是否刷新页面?")){
					window.location.reload();
				} else {
					userStaInp.val("正常");
					closeUFlush = true;
					$.cookie("canSta", "staCan", {path:"/"});
				}
			}
		}
	}
	
	/* 判断是否可以修改密码 */
	$.updPassTest = function(inputOld, oldPass, newPass1, newPass2) {
		if(oldPass != inputOld){
			alert("原密码输入错误！");
			$("#updPass input[name='uPass']").val("");
			return false;
		}
		if(newPass1 == ""){
			alert("请输入新密码");
			return false;
		} else {
			if(newPass1 != newPass2){
				$("#updPass input[name='newPass2']").val("");
				alert("两次输入的密码不一致！");
				return false;
			}
			return $.testUser(null,newPass1,null,null);
		}
		return true;
	}
	
	/* 判断是否可以修改个人信息 */
	$.updMyInfoTest = function(name, email, tel) {
		if(name == "" || email == "" || tel == ""){
			alert("内容不能为空");
			return false;
		} else {
			return $.testUser(name,null,email,tel);
		}
		return true;
	}
});
