/* 首页的事件 */
$(function() {
	/*设置轮播动画3s切换一次*/
	$(".carousel").carousel({
		interval : 3000
	});
	
	/* 获取首页的商品信息 */
	$.indexShopAjax();
	
	/* 页面大小修改事件 */
	$(window).resize(function() {
		/* 设置商品区域模块的大小 */
		$.indexShopArea();
	});
	
	/*设置商品的单击事件*/
	$(".shopArea").on("click", "ul", function() {
		$.cookie("shopId", $(this).attr("data-shopId"), {path:"/"});
		$(location).attr("href", "pages/shop/shopInfo.html");
	});
	
	/*首页的更多按钮*/
	$(".shopArea button").click(function() {		
		$.cookie("indexMore", $(this).attr("id"), {path:"/"});
		$(location).attr("href", "pages/shop/shopAll.html");
	});
})