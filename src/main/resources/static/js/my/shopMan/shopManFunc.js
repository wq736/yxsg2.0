/**
 * 我的---商品管理面板的函数
 */
$(function() {
	//判断商品信息是否合法的函数
	$.testShop = function(name, price, unit, discount, stock, picture) {
		if(name == "" || name.length > 15){
			alert("商品名不能为空且不能超过15个字！")
			return false;
		}
		var testPrice = /\d{1,10}(\.\d{1,2})?$/;
		if(!testPrice.test(price)){
			alert("商品价格的格式为：1~10位整数或1~10位整数+1~2位小数");
			return false;
		}
		if(unit == null){
			alert("商品规格不合法！");
			return false;
		}
		var discountTest = /^[1-9]\d*|0$/;
		if(!discountTest.test(discount)){
			alert("库存输入不合法(因为单位对应的份数，例单位为元/斤，10表示10斤)！")
			return false;
		}
		var stockTest = /^(10|[1-9])$/;
		if(!stockTest.test(stock)){
			alert("折扣输入不合法(只能输入1-10，10表示没有折扣)！")
			return false;
		}
	}
	
	/* 输出商品对应的订单使用 */
	$.printShopOis = function(oiList) {
		var o = "";
		for(var i in oiList){
			o += $.printShopOi(oiList[i]);
		}
		return o;
	};
	
	/* 输出店铺对应的商品列表使用 */
	$.printShopBySto = function(shopList) {
		var t = "";
		for(var i in shopList){
			t += $.printMyShop(shopList[i]);
		}
		return t;
	};
});