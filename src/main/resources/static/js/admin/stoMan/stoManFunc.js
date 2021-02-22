/**
 * 后台---店铺管理面板的函数
 */
$(function() {
	/* 输出店铺列表使用 */
	$.printStoreList = function(storeList) {
		var t = "";
		for(var i in storeList){
			t += $.printStore(storeList[i]);
		}
		return t;
	};
});