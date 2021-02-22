/* 网页头部的请求 */
$(function() {
	/* 包含登录的ajax请求的函数 */
	$.loginAjax = function(name, pass, code) {
		$.ajax({
			url : "login?n=" + name + "&p=" + pass.split("").reverse().join("") + "&code=" + code,
			type : "GET",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			success : function(result){
				$("#login").modal("hide");
				$("#logreg").empty();
				$.cookie("uName",result.uName, {path:"/"});//登录成功后将数据保存到cookie中
				$.cookie("canSta", result.uStatus, {path:"/"});
				$.cookie("canGen", result.uGender, {path:"/"});
				$("#logreg").append($.loginAfter(result.uName));
			},
			error : function(XMLHttpRequest){
				let thisCode = XMLHttpRequest.status;
				let codeArr = [405, 406]
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
				if(thisCode == 405) {
					$("#login input[name='user']").val("");
					$("#login input[name='pass']").val("");
				}
				$("#login input[name='dlyz']").val("");
				$("#login form div:eq(2) img").attr("src", "kaptcha.jpg");
			}
		});
	}
	
	/* 包含注册的ajax请求的函数 */
	$.registerAjax = function(name, pass, email, tel, code) {
		$.ajax({
			url : "register",
			type : "POST",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				uName : name,
				uPassword : pass,
				uEmail : email,
				uTel : tel,
				code : code
			}),
			success : function(result){
				alert(result.success);
				$("#register").modal("hide");
				$("#logreg").empty();
				$.cookie("uName", name, {path: "/"});
				$.cookie("canSta", "staCan", {path: "/"});
				$.cookie("canGen", "notGenCan", {path: "/"});
				$("#logreg").append($.loginAfter(name));
			},
			error : function(XMLHttpRequest){
				let thisCode = XMLHttpRequest.status;
				let codeArr = [405, 406]
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
				if(thisCode == 405){$("#register input[name='uName']").val("");}
				$("#register form div:eq(4) img").attr("src", "kaptcha.jpg");
				$("#register input[name='zcyz']").val("");
			}
		});
	}
})