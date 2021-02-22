/**
 * 我的---订单管理面板的ajax请求
 */
$(function() {
	/* 包含删除/退货/发货的ajax请求的函数 */
	$.updItemAjax = function(thisTr, reqUrl) {
		var pri = parseFloat(thisTr.find("td:eq(2)").text());
		var num = parseFloat(thisTr.find("td:eq(3)").text());
		$.ajax({
			url : reqUrl,
			type : "PUT",
			datatType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				stoId : $("#myStoreId").val(),
				shopName : thisTr.find("td:eq(1)").text(),
				time : thisTr.find("td:eq(4)").text(),
				oId : thisTr.attr("data-id"),
				allPrice : (num * pri).toFixed(2),
				number : num,
				pageCount : $.cookie("pageCount"),
				pageOn : $.cookie("pageOn"),
				pageTotal : $.getPageTotal("orderManageNav")
			}),
			success : function(result) {
				if(result.list != null){
					thisTr.remove();
					var order = $.printStoOrderList(result.list);
					$.delTr("#orderList tbody", order);
				}
				$.updTd(reqUrl, thisTr, result);
				$.printPageNav("orderManageNav", result.pageOn, result.pageTotal, result.pageBegin, result.pageEnd);
			}
		});
	}
});