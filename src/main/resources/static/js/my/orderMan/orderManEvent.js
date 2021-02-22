/**
 * 我的---订单管理面板的事件
 */
$(function() {
	/*查看店铺对应的订单详情*/
	var searOrder = ""
	var isJoinOrderMan = true;
	$("a[href='#orderList']").click(function() {
		if($("#myStoreId").val() == undefined){
			alert("暂无店铺，请先创建！");
			return false;
		} else {
			if($("#myStoreId").parent().find("input[name='stoStatus']").val() == "注销"){
				alert("您的店铺已被注销！");
				return false;
			}
			if(isJoinOrderMan){				
				$.cookie("pageCount", 0, {path:"/"});
				searOrder = "&stoId=" + $("#myStoreId").val();
				$.cookie("where", searOrder, {path:"/"});
				$.pageNav("orderManageNav", true, $.printStoOrderList);
				isJoinOrderMan = false;
			}
		}
	});

	/* 筛选按钮 */
	$("#selOrderItemBtn").click(function() {
		var where = searOrder;
		var uName = $("#orderList input[name='uName']").val();
		var shopName = $("#orderList input[name='shopName']").val();		
		var oiTime = $("#orderList input[name='oiTime']").val();
		var oiStatus = $("#orderList input[name='oiStatus']").val();
		if(uName == "" && shopName == "" && oiTime == "" && oiStatus == ""){
			alert("请输入至少一个筛选条件条件！");
			return;
		} 		
		if(uName != ""){where += ("&uId=" + $("#orderList tbody td:contains(" + uId + ")").attr("data-id"));}		
		if(shopName != ""){where += ("&shopName=" + shopName);}
		if(oiTime != ""){where += ("&oiTime=" + oiTime);}
		if(oiStatus != ""){where += ("&oiStatus=" + oiStatus);}
		
		$.cookie("where", where, {path:"/"});
		$.pageNav("orderManageNav", true, $.printStoOrderList);
	});

	/*订单数据的按钮(发货/退货/删除)*/
	$("#orderList tbody").on("click", "button", function() {
		var thisTr = $(this).parents("tr");
		var reqUrl = $(this).attr("name");
		if(reqUrl == "delBtn"){
			if(!confirm("请确定是否要从列表中删除该条记录？")){
				return;
			}
		}
		$.updItemAjax(thisTr, reqUrl);
	});
	
	/*查看原因按钮*/
	$("#orderList tbody").on("click", "button[name='lookWhyBtn']", function() {
		alert($(this).attr("data-why"));
	});
});