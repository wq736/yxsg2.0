/**
 * 商品列表页的函数
 */
$(function() {
	$.cookie("where", null, {path:"/"});
	/*设置要查询的商品条件、商品导航条对应的商品的样式*/
	$.loadShopAll = function() {
		if($.cookie("shopClass") != null){
			$("#shopClassBar ul li[data-shopClass='" + $.cookie("shopClass") + "']").addClass("list-group-item-hover");
			$.cookie("where", "&shopClass=" + $.cookie("shopClass"), {path:"/"});
			$.cookie("shopClass", null, {path:"/"});
			$.cookie("indexMore", null, {path:"/"});
		} else if($.cookie("shopNameLike") != null){
			$("#shopClassBar ul li:contains('" + $.cookie("shopNameLike") + "')").addClass("list-group-item-hover");
			$.cookie("where", "&shopNameLike=" + $.cookie("shopNameLike"), {path:"/"});
			$.cookie("shopNameLike", null, {path:"/"});
			$.cookie("indexMore", null, {path:"/"});
		} else if($.cookie("indexMore") != null){
			$("#shopClassBar ul li[data-shopType='" + $.cookie("indexMore") + "']").addClass("list-group-item-hover");
			var btn = $.cookie("indexMore");
			$.cookie("where", "&indexMore=" + btn, {path:"/"});
		}
	}
	
	/* 设置商品列表图片的大小 */
	$.shopAllShopArea = function() {
		/* 设置首页的商品图片大小 */
		var shopWidth = $(".shopArea img").width();
		$(".shopArea img").height(shopWidth * 1.2);
	}
	
	/* 输出全部商品时调用 */
	$.printShopList = function(shopList) {
		var t = "";
		for(var i in shopList){
			t += $.printShop(shopList[i]);
		}
		return t;
	}
	
	/* 输出热门商品时调用 */
	$.printHotShops = function(hotShop) {
		var hotShopId = "";
		var hotShopName = "";
		for(var i in hotShop){
			hotShopId += hotShop[i].shopId + ",";
			hotShopName += hotShop[i].shopName + ",";
		}
		$.cookie("hotShopId", hotShopId);
		$.cookie("hotShopName", hotShopName);
		return $.printShopList(hotShop);
	}
	
	/* 输出打折商品时调用 */
	$.printStockShops = function(stockShop) {
		var t = "";
		for(var i in stockShop){
			t += $.printStoShop(stockShop[i]);
		}
		return t;
	};
	
	/*商品列表页输出商品(含首页季节推荐、热门商品)*/
	$.printShop = function(shop) {
		return "<ul class='col-lg-2 col-md-2 col-sm-4 col-xs-6' data-shopId='" + shop.shopId + "'>" +
			   "<li><img src='" + shop.shopBpicture + "' width='100%'></li>" +
			   "<li>名称：" + shop.shopName + "</li>" +
			   "<li>价格：￥" + shop.shopPrice + "</li>" +
			   "<li>成交价：￥" + shop.shopAllPrice + "</li></ul>";   
	}

	/*输出打折商品时调用*/
	$.printStoShop = function(stoShop) {
		return "<ul class='col-lg-2 col-md-2 col-sm-4 col-xs-6' data-shopId='" + stoShop.shopId + "'>" +
			   "<img src='" + stoShop.shopBpicture + "' width='100%'>" +
		   	   "<li>名称：" + stoShop.shopName + "</li>" +
		   	   "<li>价格：￥" + stoShop.shopPrice + "&nbsp;&nbsp;<b class='text-danger'>" + 
		   	   stoShop.shopStock + "折</b></li>" +
		   	   "<li>成交价：￥" + stoShop.shopAllPrice + "</li></ul>"
	}
});