/**
 * 个人信息面板的ajax请求
 */
$(function() {
	/* 页面加载时发送查看个人信息的ajax请求 */
	$.ajax({
		url:"my?uName=" + $.cookie("uName"),
		type:"GET",
		dataType:"json",
		contentType : "application/json;charset=UTF-8",
		success:function(result){
			$.addUserInfo(result.user, result.now);
		}
	});
	
	/* 包含重置金额的ajax请求的函数 */
	$.updYueAjax = function(thisType, yue, id) {
		$.ajax({
			url : "updMoney",
			type : "PUT",
			dataType : "text",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				ye : yue,
				uId : id,
				type : thisType
			}),
			success : function(updYue) {
				alert("操作成功！");
				$.updYue(updYue);
			}
		})
	}
	
	/* 包含修改密码的ajax请求方法 */
	$.updPassAjax = function(newPass1) {
		$.ajax({
			url : "savePass",
			type : "PUT",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				u : parseInt($("#myInfoId").val()),
				p : newPass1.split("").reverse().join("")
			}),
			success : function(){
				alert("修改成功");
				$("#updPass").modal("hide");
				$("#updPass .modal-body input").val("");
				$("#updPass input[name='oldPass']").val(newPass1);
			}
		});
	}
	
	/* 包含修改个人信息的ajax请求的方法 */
	$.updMyInfoAjax = function(name, email, tel) {
		$.ajax({
			url : "saveUser",
			type : "PUT",
			dataType : "json",
			contentType : "application/json;charset=utf-8",
			data : JSON.stringify({
				uId : parseInt($("#myInfoId").val()),
				uName : name,
				uEmail : email,
				uTel : tel
			}),
			success : function(result){
				alert(result.success);
				$("fieldset:first").attr("disabled", "disabled");
				$("#saveUserBtn").attr("disabled", "disabled");
				$("#updPassBtn").attr("disabled", "disabled");
				$.cookie("uName", name, {path:"/"});
				$("#logreg .glyphicon").text(name);
				$("#myInfo div:first b").text("欢迎登录：" + name + "用户");
			},
			error : function (XMLHttpRequest){
				let codeArr = [405]
				$.ifRequestError(codeArr, XMLHttpRequest.status, XMLHttpRequest.responseJSON.message);
				$("#myInfo input[name='uName']").val("");
			}
		});
	}
	
	/* 包含修改头像的ajax请求 */
	$.updHeadAjax = function(formData) {
		$.ajax({
			url : "saveHead",
			type : "POST",
			dataType : "text",
			contentType : false,
			processData : false,	//告诉jQuery不要去处理发送的数据，用于对data参数进行序列化处理
			data : formData,
			success : function(result){
				$("#updHead").modal("hide");
				$("#myInfo div:first img").attr("src", result);
			}
		});			
	}
	
	/* 包含注销用户的ajax请求的函数 */
	$.delMyAjax = function(uId, gender) {
		$.ajax({
			url : "delMy?uId=" + uId,
			type : "DELETE",
			success : function() {
				alert("注销成功！");
				$.cookie("uName", null, {path:"/"});
				window.location.reload();
			},
			error : function(XMLHttpRequest) {
				let codeArr = [400];
				let thisCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
			}
		});
	}
});