/**
 * 个人订单面板的事件
 */
$(function() {
	/*单击个人订单面板*/
	var isJoinMyOrder = true;
	$("a[href='#order']").click(function() {
		if(!isJoinMyOrder){return;}
		var uId = $("#myInfoId").val();
		$.cookie("pageCount", 0, {path:"/"});
		$.cookie("where", "&uId=" + uId, {path:"/"});
		$.pageNav("orderNav", true, $.printMyOrderList);
		isJoinMyOrder = false;
	});
	
	/*个人订单面板的查看详细*/
	$("#order tbody").on("click", "button[name='myOrderItem']", function() {
		var thisTr = $(this).parents("tr");
		var oId = thisTr.find("td:eq(0)").text();
		if($("#myOrderItem .modal-body ul li:eq(0)").text() == "" || $("#myOrderItem h4").text() != oId){	
			$("#myOrderItem .modal-body").remove();
			$.getItemAjax(oId);
		} else {
			$("#myOrderItem").modal("show");			
		}
	});
	
	/*个人面板的删除按钮*/
	$("#order tbody").on("click", "button[name='delUserOrder']", function() {
		var thisTr = $(this).parents("tr");
		if(thisTr.find("td:eq(3)").text() != "已处理"){
			alert("该订单中有尚未处理的商品信息(有未签收或未退货的商品),请先处理这些商品之后在删除该订单!");
		} else {
			$.delOrderAjax(thisTr);
		}
	});
	
	/* 签收/申请退货/取消申请/退款按钮 */
	$("#myOrderItem").on("click", ".modal-body button", function() {
		var thisBtnName = $(this).attr("name");
		var thisDiv = $(this).parent();
		var status = thisDiv.find("ul li:eq(3)").text()
		var isCan = $.canUpdSta(thisBtnName, status);
		var yes = true;
		var why = null;
		if(isCan && thisBtnName == "putAwayBtn"){
			yes = confirm("签收前请先确定已收到商品！");
		} else if(isCan && thisBtnName == "applyBtn"){
			why = prompt("请说明退货的原因！");
			if(why == null || why == "" || why >= 25){
				alert("原因不能为空且不能大于25个字");
				yes = false;
			}
		} 
		if(isCan && yes){
			var staIndex = $(this).attr("data-index");
			if(staIndex == "1or2"){
				(thisDiv.find("ul li:eq(3)").text().substring(5) == "已签收") ? staIndex = 1 : staIndex = 2;
			}
			$.itemReqAjax(thisDiv, why, staIndex);
		}
	});
});