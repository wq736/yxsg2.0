/**
 * 个人订单面板的函数
 */
$(function() {
	/* 测试是否可以签收、申请退货、取消退货、退款 */
	$.canUpdSta = function(btnType, status) {
		if(btnType == "putAwayBtn" && status != "商品状态：已发送"){
			alert("该状态不能签收商品！");
			return false;
		}
		if(btnType == "applyBtn" && (status == "商品状态：已退货" || status == "商品状态：申请退货中...")){
			alert("该状态不能进行退货");
			return false;
		}
		if(btnType == "exitApplyBtn" && status != "商品状态：申请退货中..."){
			alert("该状态不能取消申请退货");
			return false;
		}
		if(btnType == "refundBtn" && status != "商品状态：店铺已被删除" && status != "已签收"){
			alert("该状态不能进行退款");
			return false;
		}
		return true;
	}
	
	/* 输出个人订单的函数 */
	$.printMyOrderList = function(myOrders) {
		var t = ""
		for(var i in myOrders){
			t += $.printMyOrder(myOrders[i]);
		}
		return t;
	}
});