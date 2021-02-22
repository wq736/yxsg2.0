/**
 * 个人订单面板的ajax请求
 */
$(function() {
	/* 包含查看订单详细信息的ajax请求的函数 */
	$.getItemAjax = function(oId) {
		$.ajax({
			url:"myOrderItem?oId=" + oId,
			type:"GET",
			dataType:"json",
			success:function(result){
				$("#myOrderItem .modal-header h4").text(oId);
				$("#myOrderItem .modal-header").after($.printOrderInfo(result));
				$("#myOrderItem").modal("show");
			}
		});
	}
	
	/* 包含删除个人订单的ajax请求的函数 */
	$.delOrderAjax = function(thisTr) {
		$.ajax({
			url : "delUserOrder",
			type : "DELETE",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				oId : thisTr.find("td:eq(0)").text(),
				uId : $("#myInfo #myInfoId").val(),
				pageCount : $.cookie("pageCount"),
				pageOn : $.cookie("pageOn"),
				pageTotal : $.getPageTotal("orderNav")
			}),
			success : function(result) {
				thisTr.remove();
				if(result.type == "myOrderList" || result.list != null){
					var t = $.printMyOrderList(result.list);
					$.delTr("#order tbody", t);
				}
				$.printPageNav("orderNav", result.pageOn, result.pageTotal, result.pageBegin, result.pageEnd);
			}
		});
	}
	
	/*签收/申请退货/取消申请的ajax请求*/
	$.itemReqAjax = function(thisDiv, why, newSta) {
		var id = thisDiv.parent().find("h4").text();
		var num = parseFloat(thisDiv.find("ul li:eq(1)").text().substring(5));
		var pri = parseFloat(thisDiv.find("ul li:eq(2)").text().substring(6));
		$.ajax({
			url : "updOiSta",
			type : "PUT",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				oId : id,
				shopName : thisDiv.find("ul li:eq(0)").text().substring(5),
				time : thisDiv.find("ul li:eq(4)").text().substring(5),
				allPrice : (num * pri).toFixed(2),
				staIndex : newSta,
				exitWhy : why
			}),
			success : function(result) {
				var status = result.itemSta;
				if(status == "申请退货中1" || status == "申请退货中2"){status = status.substr(0, 5) + "...";}
				thisDiv.find("ul li:eq(3)").text("商品状态：" + status);
				$("tr:contains('" + id +"')").find("td:eq(3)").text(result.orderSta);
			}
		});
	}
});
