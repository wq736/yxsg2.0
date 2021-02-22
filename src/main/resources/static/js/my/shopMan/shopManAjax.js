/**
 * 我的---商品管理面板的ajax请求
 */
$(function() {
	/* 包含添加商品的ajax请求的函数 */
	$.addShopAjax = function(formData){
		$.ajax({
			url:"saveShop",
			type:"POST",
			dataType:"json",
			contentType:false,
			processData: false,
			data:formData,
			success:function(shopPage){
				$("#addShop").modal("hide");
				$("#addShop input[type='text']").val("");
				$("#addShop input[type='file']").val("");
				$("#shopList tbody").empty();
				$("#shopList tbody").append($.printShopBySto(shopPage.list));
				$.printPageNav("shopManageNav", 1, shopPage.pageTotal, shopPage.pageBegin, shopPage.pageEnd);
				$.cookie("pageCount", shopPage.pageCount, {path:"/"});
				$.cookie("pageOn", shopPage.pageOn, {path:"/"});
			},
			error : function(XMLHttpRequest) {
				alert(XMLHttpRequest.responseJSON.message);
			}
		});
	}
	
	/* 包含修改商品信息的ajax请求的函数 */
	$.updShopAjax = function(thisTr, price, unit, stock){
		$.ajax({
			url : "updShop",
			type : "PUT",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				shopId : thisTr.find("td:eq(0)").text(),
				shopPrice : price,
				shopUnit : unit,
				shopStock : stock
			}),
			success:function(result){
				if(price != ""){thisTr.find("td:eq(2)").text(price);}
				if(unit != ""){thisTr.find("td:eq(3)").text(unit);}
				if(stock != ""){thisTr.find("td:eq(6)").text(stock);}
			}
		});
	}
	
	/* 包含查看当前商品相关订单的ajax请求的函数 */
	$.selShopOiAjax = function(shopName) {
		$.ajax({
			url : "selShopOi?shopName=" + shopName + "&stoId=" + $("#myStoreId").val(),
			type : "GET",
			dataType : "json",
			success : function(oiList) {
				$("#befDelShop table tbody").empty();
				$("#befDelShop table tbody").append($.printShopOis(oiList));
				$("#befDelShop .modal-footer button:eq(1)").attr("id", "delShop");
				$("#befDelShop").modal("show");
			},
			error : function (XMLHttpRequest){
				let codeArr = [400];
				let thisCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
			}
		});
	}
	
	/* 包含删除商品的ajax请求的函数 */
	$.delShopAjax = function(thisTr){
		$.ajax({
			url : "delShop",
			type : "DELETE",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				uId : $("#myInfoId").val(),
				stoId : $("#myStoreId").val(),
				shopId : thisTr.find("td:eq(0)").text(),
				shopName : thisTr.find("td:eq(1)").text(),
				pageCount : $.cookie("pageCount"),
				pageOn : $.cookie("pageOn"),
				pageTotal : $.getPageTotal("shopManageNav")
			}),
			success:function(result){
				thisTr.remove();
				if(result.type == "shopListBySto" || result.list != null){
					var shop = $.printShopBySto(result.list);
					$.delTr("#shopList tbody", shop);
				}
				$.printPageNav("shopManageNav", result.pageOn, result.pageTotal, result.pageBegin, result.pageEnd);
			},
			error : function(XMLHttpRequest) {
				let codeArr = [400];
				let thisCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
			}
		});
	}
});