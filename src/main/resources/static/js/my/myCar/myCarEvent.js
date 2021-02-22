/**
 * 我的---购物车面板的事件
 */
$(function() {
	/*单击购物车选项卡*/
	var isJoinCar = true;
	$("a[href='#car']").click(function() {
		if(!isJoinCar){return;}
		$.cookie("pageCount", 0, {path:"/"});
		var uId = $("#myInfoId").val();
		$.cookie("where", "&uId=" + uId, {path:"/"});
		$.pageNav("carManageNav", true, $.printCarList);
		$.getCouPriAjax(uId);
		isJoinCar = false;
	});
	
	/*购物车的"+-"按钮*/
	$("#car tbody").on("click", "tr button[data-name='jj']", function() {
		var thisTr = $(this).parents("tr");
		var oldNum = thisTr.find("input:eq(0)").val();
		thisNum = oldNum;
		var maxNum = thisTr.find("input:eq(1)").val();
		if($(this).attr('name') == "addNumBtn"){
			oldNum++;
		} else {
			oldNum--;						
		}
		if($.testNum(oldNum, maxNum)){
			$.updCar(thisTr, thisNum, oldNum);
		}
	});
	
	/*购物车商品数量文本框获取焦点时保存当前数量*/
	var thisNum = 0;
	$("#car tbody").on("focusin", "input[type='text']", function() {
		thisNum = $(this).val();
	});
	
	/*购物车商品数量文本框失去焦点时判断是否符合条件*/
	$("#car tbody").on("focusout", "input[type='text']", function() {
		var thisTr = $(this).parents("tr");
		var maxNum = thisTr.find("input:eq(1)").val();
		var oldNum = $(this).val();
		if(!$.testNum(oldNum, maxNum)){
			thisTr.find("input:eq(0)").val(thisNum);
		} else {
			$.updCar(thisTr, thisNum, oldNum);
		}
	});
	
	/*购物车保存按钮*/
	$("#car tbody").on("click", "button[name='saveBtn']", function() {
		var info = "无法保存购物车信息！请在个人信息面板中查看您的账号是否已被注销！";
		if($.canContinue("canSta", "staCan", info)){			
			var thisTr = $(this).parents("tr");
			if(thisTr.find("td:eq(1)").text() == "该商品已下架"){
				alert("保存失败，该商品已下架！");
				return;
			}
			$.updNumAjax(thisTr);
		}	
	});
	
	/*购物车删除按钮*/
	$("#car tbody").on("click", "button[name='delBtn']", function() {
		if(confirm("请确定是否要从购物车中移除这件商品！")){
			var thisTr = $(this).parents("tr");
			$.delAcarAjax(thisTr);
		}
	});

	/*购物车清空/结算按钮*/
	$("#car .caozuo button:gt(0)").click(function() {
		var info = "无法结算购物车！请在个人信息面板中查看您的账号是否已被注销！";
		var thisBtnId = $(this).attr("id");
		if(($.canContinue("canSta", "staCan", info) && thisBtnId == "carEnd") || thisBtnId == "carEmpty"){				
			var reqUrl = $(this).attr("id");
			if($("#car tbody tr").length == 0){return;}
			if(reqUrl == "carEmpty"){
				var tiShi1 = "请确定是否要清空购物车中的数据！";
				var tiShi2 = "清空成功";
			} else {
				if(parseFloat($("#myInfo .yue").attr("data-money")) < parseFloat($("#car span:eq(0) b:eq(1)").text())){
					alert("结账失败，您的账户余额不足！");
					return;
				}
				var tiShi1 = "请确定是否要对购物车进行结账操作！";			
				var tiShi2 = "订单生生成成功";
			}
			if(confirm(tiShi1)){
				$.delOrPutAjax(reqUrl, tiShi2);
			}
		}
	});
	
	/*删除下架商品按钮*/
	$("#delNotExistShop").click(function() {
		$.delNotExistAjax();
	});
});