/**
 * 个人店铺面板的ajax请求 
 */
$(function() {
	/*创建店铺弹出模态框*/
	$("#myInfo .col-lg-6:last").on("click", "#addStore", function() {
		var info = "无法创建店铺，您的账号已被禁用！";
		if($.canContinue("canSta", "staCan", info)){		
			$("#myStore").modal("show");
			$("#myStore form div:eq(3) img").attr("src", "kaptcha.jpg");
		}	
	});
	
	/*创建店铺模态框中的创建按钮*/
	$("#myStore").on("click", "#regStoreBtn", function() {
		var name = $.trim($("#myStore input[name='stoName']").val());
		var address = $.trim($("#myStore input[name='stoAddress']").val());
		var mainShop = $.trim($("#myStore input[name='stoMainShop']").val());
		var code = $.trim($("#myStore input[name='addStoCode']").val());
		if(code == ""){
			alert("验证码不能为空！")
			return;
		}
		var canCreSto = $.testStore(name, address, mainShop);
		if(canCreSto){$.creStoAjax(name, address, mainShop, code);}
	});
	
	/*店铺面板的修改按钮*/
	$(".storeUpdate").click(function() {
		if($("#myStoreId").parent().find("input[name='stoStatus']").val() == "注销"){
			alert("您的店铺已被注销！");
			return false;
		}
		$("fieldset:last").removeAttr("disabled");
		$("#saveStoreBtn").removeAttr("disabled");
	});
	
	/*修改店铺按钮*/
	$("#saveStoreBtn").click(function() {
		var name = $.trim($("#myInfo input[name='stoName']").val());
		var address = $.trim($("#myInfo input[name='stoAddress']").val());
		var mainShop = $.trim($("#myInfo input[name='stoMainShop']").val());
		var canUpdSto = $.testStore(name, address, mainShop);
		if(canUpdSto){$.setStoInfoAjax(name, address, mainShop)}
	});
	
	/* 注销店铺按钮 */
	$("#delMySto").click(function() {
		var gender = 1;
		if(confirm("是否要注销店铺！")){
			if($(this).parents("form").find("input[name='stoStatus']").val() == "注销"){gender = 2;}
			$.selStoreOiAjax($("#myStoreId").val(), gender);
		}
	});
	
	/* 直接删除按钮 */
	$("#befDelShop").on("click", "#delStore", function() {
		if(confirm("请先点击左边的生成表格按钮将该商品的相关订单保存到自己的电脑，防止数据丢失哦！")){
			$.delStoreAjax($("#myStoreId").val(), $("#myInfoId").val(), $(this).attr("data-gender"));
		}
	});
});