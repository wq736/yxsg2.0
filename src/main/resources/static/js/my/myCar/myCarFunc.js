/**
 * 我的---购物车面板的函数
 */
$(function() {
	/*测试商品数量的函数*/
	$.testNum = function(shopNum, maxNum) {
		var test = /^[1-9]\d*$/;
		if(!test.test(shopNum)){
			alert("购物车中商品的数量必须大于1，若想设为0，请直接删除该商品！");
			return false;
		} else if(shopNum - maxNum > 0){
			alert("购物车中商品的数量不能大于库存！");
			return false;
		} else {
			return true;
		}
	}
	
	/*修改购物车信息的函数*/
	$.updCar = function(thisTr, oldNum, newNum) {
		thisTr.find("input:eq(0)").val(newNum);
		var price = thisTr.find("td:eq(3)").text();
		thisTr.find("td:eq(4)").text((newNum * price).toFixed(2));
		var oldAllNum = parseInt($("#car div b:eq(0)").text());
		var oldAllPrice = parseFloat($("#car div b:eq(1)").text());
		var oldDis = parseInt(thisTr.find("input:eq(1)").val());
		var newAllNum = oldAllNum + (newNum - oldNum);
		var newAllPrice = (oldAllPrice + ((newNum - oldNum) * price)).toFixed(2);
		thisTr.find("input:eq(1)").val(oldDis + (oldNum - newNum));
		$("#car span:eq(0) b:eq(0)").text(newAllNum);		
		$("#car span:eq(0) b:eq(1)").text(newAllPrice);		
		$("#car span:eq(1) b:eq(0)").text(newAllNum);		
		$("#car span:eq(1) b:eq(1)").text(newAllPrice);		
	}
	
	/* 重置购物车以及用户余额 */
	$.updMoneyAndCar = function(oPrice){
		$("#car tbody").empty();
		$("#car span:eq(0) b:eq(0)").text(0);		
		$("#car span:eq(0) b:eq(1)").text(0);		
		$("#car span:eq(1) b:eq(0)").text(0);		
		$("#car span:eq(1) b:eq(1)").text(0);		
		$("#carManageNav ul").empty();
		var yueSpan = $("#myInfo .yue");
		var oldYue = parseFloat(yueSpan.attr("data-money"));
		yueSpan.attr("data-money", (oldYue - parseFloat(oPrice)).toFixed(2));
		yueSpan.text(yueSpan.attr("data-money"));
	}
	
	/* 输出购物车列表的函数 */
	$.printCarList = function(carList) {
		var t = "";
		for(var i in carList){
			t += $.printCar(carList[i]);
		}
		return t;
	}
});