/**
 * 我的---订单管理面板的函数
 */
$(function() {
	/*根据发货/退货/删除修改单元格内容或删除行*/
	$.updTd = function(type, thisTr, result) {
		if(type == "sendBtn"){
			thisTr.find("td:eq(5)").text("已发送");			
			thisTr.find("td:eq(6)").text("无需操作");	
		} else if(type == "exitBtn"){
			thisTr.find("td:eq(5)").text("已退货");			
			thisTr.find("td:eq(6)").empty();				
			thisTr.find("td:eq(6)").append("<button name='delBtn' class='btn btn-danger btn-xs'>删除</button>");				
		} else {
			thisTr.remove();
			if(result.stoOrderList != null){
				var t = $.printStoOrderList(result.stoOrderList);
				if($("#orderList tbody tr").text() == ""){
					$("#orderList tbody").append(t);
				} else {							
					$("#orderList tbody tr:last").after(t);
				}
			}
			$.printPageNav("orderManageNav", result.page, result.head, result.foot);
		}
	}
	
	/*输出店铺订单管理时调用*/
	$.printStoOrderList = function(stoOrders) {
		var t = ""
		for(var i in stoOrders){
			t += $.printStoOrder(stoOrders[i]);
		}
		return t;
	}
});