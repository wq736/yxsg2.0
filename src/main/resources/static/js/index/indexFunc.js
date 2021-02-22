/* 首页需要调用的函数 */
/* 设置首页商品区域的模块大小 */
$.indexShopArea = function() {
	/* 设置首页的商品图片大小 */
	var shopWidth = $(".shopArea:eq(1) div:eq(2) img").width();
	for(var i = 0; i <= 2; i++){
		$(".shopArea:eq(" + i + ") div:eq(2) img").height(shopWidth * 1.2);
	}
	/* 设置商品左边区域图片的大小 */
	var shopLeftHeight = $(".shopArea:eq(2) div:eq(2) ul").height();
	$(".shopArea:eq(0) div:eq(1)").height(shopLeftHeight * 2 + 4);
	$(".shopArea:eq(1) div:eq(1)").height(shopLeftHeight * 2 + 4);
	$(".shopArea:eq(2) div:eq(1)").height(shopLeftHeight * 2 + 4);
}