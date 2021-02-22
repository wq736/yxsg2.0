/* 输出我的模块内容时调用的方法 */
/*输出购物车时调用*/
$.printCar = function(car) {
	var c = "<tr><td>" + car.cId + "</td>" +
			"<td>" + car.shopName + "</td>" +
			"<td><button data-name='jj' name='cutNumBtn' class='btn btn-primary btn-xs'>-</button>&nbsp;&nbsp;" +
			"<input data-oldNum='" + car.shopSum + "' type='text' value='" + car.shopSum + "' style='width:36pt' />&nbsp;&nbsp" +
			"<button data-name='jj' name='addNumBtn' class='btn btn-primary btn-xs'>+</button></td>" +
			"<td>" + car.shopPrice + "</td>" +
			"<td>" + (car.shopSum * car.shopPrice).toFixed(2) + "</td>" +				 
			"<td>" + car.stoId + "</td>" +
			"<td>" + car.cTime + "</td>" +
			"<td><input type='hidden' value='" + car.shopDiscount + "'>" +
			"<button name='saveBtn' class='btn btn-primary btn-xs'>保存</button>&nbsp;&nbsp;" +
			"<button name='delBtn' class='btn btn-danger btn-xs'>删除</button></td></tr>";
	return c;
}

/*输出店铺对应的商品列表时调用*/
$.printMyShop = function(shop) {
	var t = "<tr><td>" + shop.shopId + "</td>" +
			"<td>" + shop.shopName + "</td>" + 
			"<td>" + shop.shopPrice + "</td>" +
			"<td>" + shop.shopUnit + "</td>" + 
			"<td>" + shop.shopTime + "</td>" + 
			"<td>" + shop.shopDiscount + "</td>" + 
			"<td>" + shop.shopStock + "</td>" + 
			"<td>" + shop.shopAllPrice + "</td>" + 
			"<td><button name='updShopBtn' class='btn btn-primary btn-xs'>修改</button>&nbsp;" +
			"<button name='delShopBtn' class='btn btn-danger btn-xs'>删除</button></td></tr>";
	return t;
}

/*输出个人订列表时调用*/
$.printMyOrder = function(myOrder) {
	var t = "<tr><td>" + myOrder.oId + "</td>" +
			"<td>" + myOrder.oNum + "</td>" +
			"<td>" + myOrder.oPrice + "</td>" +
			"<td>" + myOrder.oStatus + "</td>" +
			"<td>" + myOrder.oTime + "</td>" +
			"<td><button name='myOrderItem' class='btn btn-primary btn-xs'>查看详细</button>&nbsp;" +
			"<button name='delUserOrder' class='btn btn-danger btn-xs'>删除</button></td></tr>";
	return t;
}

/*输出个人订单详细信息时调用*/
$.printOrderInfo = function(infoList) {
	var t = "";
	for(var i in infoList){
		var btns = "<button name='putAwayBtn' data-index='0' class='btn btn-success'>签收</button>&nbsp;&nbsp;"
		var status = infoList[i].oiStatus;
		var aboutSto = infoList[i].aboutSto;
		if(aboutSto == "店铺已被删除"){
			if(status == "未发送"){				
				btns = "<button name='refundBtn' data-index='4' class='btn btn-success'>退款</button>";
			} else if(status == "已发送"){
				btns += "<button name='refundBtn' data-index='4' class='btn btn-success'>退款</button>";
			} else{btns = "";}
			status = aboutSto;
		} else {
			btns += "<button name='applyBtn' data-index='1or2' class='btn btn-default'>申请退货</button>&nbsp;&nbsp;" +
					"<button name='exitApplyBtn' data-index='3' class='btn btn-default'>取消申请</button>";
		}
		if(status == "申请退货中1" || status == "申请退货中2"){status = status.substr(0, 5) + "...";}
		t += "<div class='modal-body orderInfo'>" + 
			 "<ul><li>商品名称：" + infoList[i].shopName + "</li>" +
			 "<li>商品数量：" + infoList[i].oiCount + "</li>" +
			 "<li>商品单价：￥" + infoList[i].oiPrice + "</li>" +
			 "<li>商品状态：" + status + "</li>" +
			 "<li>购买时间：" + infoList[i].oiTime + "</li></ul>" + btns + "</div>"; 
	}
	return t;
}

/* 输出商品相关订单时调用 */
$.printShopOi = function(oi) {
	var t = "<tr><td>" + oi.uName + "</td>" +
			"<td>" + oi.shopName + "</td>" +
			"<td>" + oi.oiPrice + "</td>" +
			"<td>" + oi.oiCount + "</td>" +
			"<td>" + oi.oiTime + "</td>" +
			"<td>" + oi.oiStatus + "</td>" +
			"<td>" + oi.exitWhy + "</td></tr>"; 
	return t;
};

/* 输出店铺订单管理的信息时调用 */
$.printStoOrder = function(omOrder) {
	var status = omOrder.oiStatus;
	var btn = "无需操作";
	if(status == "未发送" || status == "取消退货"){btn = "<button name='sendBtn' class='btn btn-primary btn-xs'>发货</button>";}
	if(status == "申请退货中1" || status == "申请退货中2"){
		status = status.substr(0, 5) + "...";
		btn = "<button name='exitBtn' class='btn btn-danger btn-xs'>退货</button>&nbsp;&nbsp;" +
		"<button name='lookWhyBtn' data-why='" + omOrder.exitWhy + "' " +
		"class='btn btn-default btn-xs'>查看原因</button>";
	}
	if(status == "已退货" || status == "已签收"){btn = "<button name='delBtn' class='btn btn-danger btn-xs'>删除</button>";}
	var t = "<tr data-id='" + omOrder.oId + "'>" +
			"<td data-id='" + omOrder.uId + "'>" + omOrder.uName + "</td>" +
			"<td>" + omOrder.shopName + "</td>" +
			"<td>" + omOrder.oiPrice + "</td>" +
			"<td>" + omOrder.oiCount + "</td>" +
			"<td>" + omOrder.oiTime + "</td>" +
			"<td>" + status + "</td>" +
			"<td>" + btn + "</td></tr>"; 
	return t;
}