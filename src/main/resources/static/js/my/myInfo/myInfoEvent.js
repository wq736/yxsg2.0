/**
 * 个人信息面板的事件
 */
$(function() {
	/* 更新cookie中的状态、等级 */
	$.updUserGenSta();
	
	/*  */
	$("#myInfo .lookYue").click(function() {
		$("#myInfo .yue").css("display", "inline-block");
	});
	
	/*退出登录按钮*/
	$("#exitA").click(function() {
		$.cookie("uName", null, {path:"/"});
		$(location).attr("href", "index.html");
	});

	/* 充值取出按钮 */
	$("#myInfo #addYue, #myInfo #reduceYue").click(function() {
		var info = "无法充值，请在个人信息面板中查看您的账号是否已被注销！";
		var thisBtn = $(this).attr("id");
		if($.canContinue("canSta", "staCan", info)){			
			var tishi = "";
			(thisBtn == "addYue") ? tishi = "请输入要充值的金额" : tishi = "请输入要取出的金额";
			var yue = prompt(tishi);
			if(yue < 1 || !/^0{1}[.]?$|^[1-9]\d*([.]{1}[0-9]{1,2})?$/.test(yue)){
				alert("操作失败，充值或取出的金额不能少于1元，且最多只能有2位小数！");	
			} else if(thisBtn == "reduceYue" && parseFloat(yue) > parseFloat($("#myInfo .yue").attr("data-money"))){
				alert("提现失败！体现的金额不能超过余额");
			} else {
				$.updYueAjax(thisBtn ,yue, $("#myInfoId").val());
			}
		}	
	});
	
	/*个人信息的修改按钮，单击时设置对应标签可用*/
	$("#userUpdateBtn").click(function() {
		var info = "无法修改个人信息！请在个人信息面板中查看您的账号是否已被注销！";
		if($.canContinue("canSta", "staCan", info)){			
			$("fieldset:first").removeAttr("disabled");
			$("#saveUserBtn").removeAttr("disabled");
			$("#updPassBtn").removeAttr("disabled");
		}	
	});
	
	/*修改密码，弹出模态框*/
	$("#updPassBtn").click(function() {
		$("#updPass").modal("show");
	});
	
	/*修改密码模态框的修改按钮*/
	$("#updPass").on("click", "#savePassBtn", function() {
		var oldPass = $("#updPass input[name='oldPass']").val(); 
		var inputOld = $("#updPass input[name='uPass']").val();
		var newPass1 = $("#updPass input[name='newPass1']").val();
		var newPass2 = $("#updPass input[name='newPass2']").val();
		var canUpd = $.updPassTest(inputOld, oldPass, newPass1, newPass2);
		if(canUpd){$.updPassAjax(newPass1);}
	});
	
	/*修改个人信息按钮*/
	$("#saveUserBtn").click(function() {
		var name = $.trim($("#myInfo input[name='uName']").val());
		var email = $.trim($("#myInfo input[name='uEmail']").val());
		var tel = $.trim($("#myInfo input[name='uTel']").val());
		var canUpd = $.updMyInfoTest(name, email, tel);
		if(canUpd){$.updMyInfoAjax(name, email, tel);}
	});
	
	/* 修改头像按钮 */
	$("#updHeadBtn").click(function() {
		var info = "无法修改个人头像！请在个人信息面板中查看您的账号是否已被注销！";
		if($.canContinue("canSta", "staCan", info)){	
			$("#updHead").modal("show");
		}
	});
	
	/*修改头像*/
	$("#updHead").on("click", "#saveHeadBtn", function() {
		var file = $("input[name='updateHead']").val();
		if(file.length == 0) {
			alert("请选择一张图片");		
		} else {
			var formData = new FormData();
			formData.append("uId", $("#myInfoId").val());
			formData.append("updateHead", $("#updHead input[name='updateHead']")[0].files[0]);
			$.updHeadAjax(formData);
		}
	});
	
	/* 注销用户按钮 */
	$("#myInfo #delMy").click(function() {
		var uId = $("#myInfoId").val();
		if(confirm("注销账户后，该用户所有的数据将会被清空，请确定是否要继续注销！")){
			if($("#myStoreId").val() != undefined){
				alert("注销用户之前请先注销店铺！");
			} else {
				$.delMyAjax(uId);
			}
		}
	});
});