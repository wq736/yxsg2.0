/* 网页头部需要的函数 */
$(function() {
	/* 登录之前显示的右侧区域 */
	$.loginBefore = function() {
		return "<li class='dropdown-toggle'><span class='glyphicon glyphicon-user'>未登录</span>" + 
		  	   "<ul class='dropdown-menu'>" +
		  	   		"<li><span id='loginA'>登录</span>&nbsp;&nbsp;<span id='registerA'>注册</span></li>" +
			   "</ul></li>"
	}
	
	/* 登录之后显示的右侧区域 */
	$.loginAfter = function(name) {
		return "<li class='dropdown-toggle'><span class='glyphicon glyphicon-user overHidden'>" + $.cookie("uName") + "</span>" + 
			   "<ul class='dropdown-menu'>" +
	   				"<li><span id='exit'>退出登录</span></li>" +
	   				"<input id='status' type='hidden' value />" + 
	   				"<input id='can' type='hidden' value />" + 
	   		   "</ul></li>"
	}
	
	/*验证用户内容的函数*/
	$.testUser = function(name, pass, email, tel) {
		if(name == "" || pass == "" || email == "" || tel == ""){
			alert("内容不能为空");
			return false;
		}
		
		/* 验证用户名 */
		if(name != null){
			var nameTest = /^[\u4e00-\u9fa5\-_·0-9A-z]+$/;
			if(!nameTest.test(name) || name.length > 10){
				alert("地址不合法(用户名只能由中文、英文大小写、数字或_ - .组成，最多15个字符)");
				return false;
			}
		}
		/* 验证密码 */
		if(pass != null){
			var passTest = /^\w{6,12}$/;
			if(!passTest.test(pass)){
				alert("密码输入不合法(密码只能由数字、字母、下划线组成，6~12个字符)");
				return false;
			}
		}
		
		/* 验证邮箱 */
		if(email != null){
			var emailTest = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
			if(!emailTest.test(email)){
				alert("邮箱输入不合法");
				return false;				
			}
		}
		
		/* 验证联系方式 */
		if(tel != null){
			var telTest = /^1[3456789]\d{9}$/;
			if(!telTest.test(tel)){
				alert("联系方式输入不合法");
				return false;								
			}
		}
		return true;
	}
})
