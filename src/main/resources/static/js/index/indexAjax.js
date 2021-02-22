/* 首页需要的ajax请求 */
/* 包含获取查询首页商品的ajax请求的函数 */
$(function() {	
	$.indexShopAjax = function() {
		$.ajax({
			url : "indexShop",
			type : "GET",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			success : function(result){
				var indexSeasonShop = $.printShopList(result.seasonShop);
				var indexHotShop = $.printHotShops(result.hotShop);
				var indexStockShop = $.printStockShops(result.stockShop);
				$("#indexSeasonShop").append(indexSeasonShop);
				$("#indexHotShop").append(indexHotShop);
				$("#indexStockShop").append(indexStockShop);
				var lastShop = $("#indexSeasonShop, #indexHotShop, #indexStockShop").find("ul:last");
				lastShop.removeClass("col-sm-4");
				lastShop.addClass("hidden-sm");
				/* 设置商品区域模块的大小 */
				$.indexShopArea();
			}
		});
	}
})