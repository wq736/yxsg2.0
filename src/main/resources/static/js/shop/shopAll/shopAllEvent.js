/**
 * 商品列表页的事件
 */
$(function() {
	$.loadShopAll();
	$.pageNav("shopAllNav", true, $.printShopList);
	
	/* 页面大小改变事件 */
	$(window).resize(function() {
		$.shopAllShopArea();
	});
	
	/* 商品页商品类别条的单击事件 */
	$(".list-group").find("li:gt(0)").click(function() {
		var printFunc = $.printShopList;
		$(".list-group-item").siblings().removeClass("list-group-item-hover");
		$(this).addClass("list-group-item-hover");
		$.cookie("pageOn", 1, {path:"/"});
		var temp = $(this).text();		
		if(temp == "更多>>"){
			$.cookie("where", "&shopClass=" + $(this).attr("data-shopClass"), {path:"/"});			
		} else if(temp == "季节推荐" || temp == "热门商品" || temp == "打折促销"){
			$.cookie("where", "&indexMore=" + $(this).attr("data-shopType"), {path:"/"});
			if(temp == "打折促销"){printFunc = $.printStockShops;}
		} else {
			$.cookie("where", "&shopNameLike=" + temp, {path:"/"});
		}
		$.pageNav("shopAllNav", true, printFunc);								
	});
	
	
	
	/*商品的单击事件*/
	$("#shopAll").on("click", "ul", function() {
		$.cookie("shopId", $(this).attr("data-shopId"), {path:"/"});
		$(location).attr("href", "pages/shop/shopInfo.html");
	})
});
