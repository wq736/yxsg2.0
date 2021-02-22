/**
 * 我的---购物车面板的ajax请求
 */
$(function() {
	/* 包含查看购物车中商品数量以及总金额的ajax请求的函数 */
	$.getCouPriAjax = function(uId) {
		$.ajax({
			url : "countAndPrice?uId=" + uId,
			type : "GET",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			success : function(result) {
				$("#car span:eq(0) b:eq(0)").text(result.allSum);
				$("#car span:eq(0) b:eq(1)").text(result.allPrice);
				$("#car span:eq(1) b:eq(0)").text(result.allSum);
				$("#car span:eq(1) b:eq(1)").text(result.allPrice);
			}	
		});
	}
	
	/* 包含修改购物车中商品数量的ajax请求 */
	$.updNumAjax = function(thisTr) {
		let newNum = parseInt(thisTr.find("input:eq(0)").val());
		let oldNum = parseInt(thisTr.find("input:eq(0)").attr("data-oldNum"));
		$.ajax({
			url : "saveShopNum",
			type : 'PUT',
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				cId : thisTr.find("td:eq(0)").text(),
				newShopNum : newNum,
				oldShopNum : oldNum
			}),
			success : function(result) {
                alert(result.success);
				thisTr.find("input:eq(1)").val(parseInt(thisTr.find("input:eq(1)").val()) + oldNum - newNum);
			},
			error : function(XMLHttpRequest) {
				let codeArr = [404];
				let thidCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thidCode, XMLHttpRequest.responseJSON.message);
				$.updCar(thisTr, newNum, oldNum);
			}
		});
	}
	
	/* 包含删除购物车某一项商品的ajax请求的函数 */
	$.delAcarAjax = function(thisTr) {
		$.ajax({
			url : "delACar",
			type : "DELETE",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				uId : $("#myInfo #myInfoId").val(),
				cId : thisTr.find("td:eq(0)").text(),
				pageCount : $.cookie("pageCount"),
				pageOn : $.cookie("pageOn"),
				pageTotal : $.getPageTotal("carManageNav")
			}),
			success:function(result){
				$.updCar(thisTr, thisTr.find("td:eq(2) input").val(), 0);
				thisTr.remove();
				if(result.type == "carList" || result.list != null){
					var car = $.printCarList(result.list);
					$.delTr("#car tbody", car);
				}
				var oldAllNum = parseInt($("#car div b:eq(0)").text());
				var oldAllPrice = parseFloat($("#car div b:eq(1)").text());
				var oldDis = parseInt(thisTr.find("input:eq(1)").val())
				thisTr.find("input:eq(1)").val(oldDis + thisTr.find("input:eq(0)").val());
				$.printPageNav("carManageNav", result.pageOn, result.pageTotal, result.pageBegin, result.pageEnd);
				$.cookie("pageCount", result.pageCount, {path:"/"});
				$.cookie("pageOn", result.pageOn, {path:"/"});
			}
		});	
	}
	
	/* 包含清空结算购物车的ajax请求的函数 */
	$.delOrPutAjax = function(reqUrl, tiShi2) {
		var price = $("#car b:eq(1)").text();
		$.ajax({
			url : reqUrl,
			type : 'DELETE',
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				uId : $("#myInfoId").val(),
				oNum : $("#car b:eq(0)").text(),
				oPrice : price
			}),
			success : function() {
				alert(tiShi2);
				$.updMoneyAndCar(price);
			},
			error : function(XMLHttpRequest) {
				if(reqUrl == "carEnd"){
					let codeArr = [404];
					let thisCode = XMLHttpRequest.status;
					$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message)
				} else {
					alert("服务器繁忙，请稍后再试.....");
				}
			}
		});
	}
	
	/*包含删除下架商品的ajax请求的函数*/
	$.delNotExistAjax = function() {
		$.ajax({
			url : "delNotExistShop?uId=" + $("#myInfoId").val(),
			type : 'DELETE',
			success : function() {
				window.location.reload();
			}
		});	
	}
});