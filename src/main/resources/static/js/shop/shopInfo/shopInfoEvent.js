/**
 * 商品详情页的事件
 */
$(function() {
	/*页面加载时发送ajax请求，获取第一页用户的数据*/
	$.cookie("where", "&shopId=" + $.cookie("shopId"), {path:"/"});
	$.shopInfoAjax();
	
	/* 更新cookie中的状态、等级 */
	$.updUserGenSta();
	
	/* 页面大小改变事件 */
	$(window).resize(function() {
		$.shopInfoPicSize();
	});
	
	/*点击显示大图*/
	$(".col-lg-4 img:gt(0)").click(function() {
		$(".col-lg-4 img:first").attr("src", $(this).attr("src"));
	});
	
	/*畅销品商品名称的单击事件*/
	$("#rank li:gt(0)").click(function() {
		$.cookie("shopId", $(this).attr("data-shopId"), {path:"/"});
		$(location).attr("href", "pages/shop/shopInfo.html");
	});
	
	/* 加入购物车按钮 */
	$("#addToCarBtn").click(function() {
		var info = "无法添加商品到购物车，请在个人信息面板中查看您的账号是否已被禁用！";
		if($.canContinue("canSta", "staCan", info)){		
			var shopnum = prompt("请输入要购买的数量");
			if(shopnum <= 0){
				alert("添加失败，购买的数量不能为0");			
			} else {
				var price = $("p[data-name='price']").text();
				var stock = $("p[data-name='stock']").text();
				var shopprice =  parseFloat(price.substring(3)) * parseFloat(stock.substring(3, stock.length - 1)) / 10;
				var uname = $.cookie("uName");
				$.joinCarAjax(uname, shopprice, shopnum);
			}
		}	
	});
	
	/* 查看购物车按钮 */
	$("#lookToCarBtn").click(function() {
		if($.cookie("uName") == null){
			$("#login").modal("show");
		} else {			
			$(location).attr("href", "pages/user/my.html");
		}
	});
	
	/* 发送评论 */
	$("#pjArea span").click(function() {
		var info = "无法发送评论，请在个人信息面板中查看您的账号是否已被禁用！";
		if($.canContinue("canSta", "staCan", info)){			
			$("#addCommont").modal("show");	
		}
	});
	
	/* 发送评论模态框的确认按钮 */
	$("#addCommont").on("click", "#saveCommontBtn", function() {
		if($("#addCommontForm textarea").val().length <= 0 || $("#addCommontForm textarea").val().length > 50){
			alert("添加失败，添加的内容字数不能小于等于0，且不能超过50个字！");
		} else {
			var formData = new FormData($("#addCommontForm")[0]);
			formData.append("uName", $.cookie("uName"));
			formData.append("shopId", $.cookie("shopId"));
			formData.append("pageCount", $.cookie("pageCount"));
			$.sendCommAjax(formData);
		}
	});
})