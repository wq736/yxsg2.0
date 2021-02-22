/* 网页头部的事件 */
$(function() {
	/* 页面加载时判断cookie中是否存在用户名，在导航栏右侧添加对应的元素 */
	if($.cookie("uName") != null){	
		$("#logreg").append($.loginAfter($.cookie("uName")));
	} else {
		$("#logreg").append($.loginBefore());	
	}
	
	/* 导航栏"购物大厅面板" */
	$("a[data-joinTo='shopAll']").click(function() {
		$.cookie("indexMore", null, {path:"/"});		
		$(location).attr("href", "pages/shop/shopAll.html");
	});
	
	/* 导航栏4类商品的单击事件 */
	$(".dropdown-toggle a").click(function() {
		if($(this).attr("data-toggle") == "dropdown"){
			$.cookie("shopNameLike", null, {path:"/"});
			$.cookie("shopClass", $(this).text(), {path:"/"});
		} else {
			$.cookie("shopClass", null, {path:"/"});
			$.cookie("shopNameLike", $(this).text(), {path:"/"});
		}
		$(location).attr("href", "pages/shop/shopAll.html");
	});
	
	/* 导航栏"我的"单击事件 */
	$("#myA").click(function() {
		if($.cookie("uName") == null){	//存放用户名称的cookie为空表示用户未登录，弹出登录模态框	
			$("#login").modal("show");
		} else {	//用户登录则进入"我的"页面
			$(location).attr("href", "pages/user/my.html");
		}
	});
	
	/* 导航栏"后台管理"单击事件 */
	$("#adminA").click(function() {
		var info = "进入失败，您的权限不够！";
		if($.canContinue("canGen", "genCan", info)){			
			$(location).attr("href", "pages/admin/admin.html");
		}
	});
	
	/* 导航栏搜索(根据名称进行搜索) */
	$("#headSeaInput span").click(function() {
		var shopNameLike = $("#headSeaInput input").val();
		if(shopNameLike == ""){
			alert("请输入要搜索的商品名称！");
		} else {			
			$.cookie("shopNameLike", shopNameLike, {path:"/"});
			$(location).attr("href", "pages/shop/shopAll.html");
		}
	});
	
	/*导航栏中点击"登录"弹出"登录"的模态框*/
	$("#logreg").on("click", "#loginA", function() {
		$("#login").modal("show");
		$("#login form div:eq(2) img").attr("src", "kaptcha.jpg");
	});
	
	/*登录*/
	$("#login").on("click", "#loginBtn", function() {
		var name = $("#login input[name='user']").val();
		var pass = $("#login input[name='pass']").val();
		var code = $("#login input[name='dlyz']").val();
		if(name.length == ""){
			alert("请输入用户名！");
			return;
		}
		if(pass.length == ""){
			alert("请输入密码！");
			return;
		}
		if(code == ""){
			alert("验证码不能为空");
			$("#login form div:eq(2) img").attr("src", "kaptcha.jpg");
			return;
		}
		$.loginAjax(name, pass, code)
	}); 
	
	/* 导航条单击"注册"弹出"注册模态框" */
	$("#logreg").on("click", "#registerA", function() {
		$("#register").modal("show");
		$("#register form div:eq(4) img").attr("src", "kaptcha.jpg");
	});
	
	/* 注册模态框中"注册"按钮的单击事件 */
	$("#register").on("click", "#registerBtn", function() {
		/* 注册时用户输入内容的验证 */
		var name = $.trim($("#register input[name='uName']").val());
		var pass = $.trim($("#register input[name='uPassword']").val());
		var email = $.trim($("#register input[name='uEmail']").val());
		var tel = $.trim($("#register input[name='uTel']").val());
		var code = $.trim($("#register input[name='zcyz']").val());
		if(code == ""){
			alert("验证码不能为空！");
			return;
		}
		var canRegister = $.testUser(name, pass, email, tel);	
		if(canRegister){$.registerAjax(name, pass, email, tel, code)};
	});
	
	/* 退出登录 */
	$("#logreg").on("click", "#exit", function() {
		$.cookie("uName", null, {path:"/"});
		$.cookie("canGen", null, {path:"/"});
		$.cookie("canSta", null, {path:"/"});
		$(location).attr("href", "index.html");
	});
});