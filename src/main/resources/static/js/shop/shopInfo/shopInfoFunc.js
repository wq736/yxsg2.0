/**
 * 商品详情页需要使用的函数
 */
$(function() {
	/* 输出商品详细信息使用 */
	$.printShopInfo = function(result) {
		$("#picArea img:eq(0)").attr("src", result.shop.shopBpicture);
		$("#picArea img:eq(1)").attr("src", result.shop.shopBpicture);
		if($(result.shop.shopSpicture1 != null)){$("#picArea img:eq(2)").attr("src", result.shop.shopSpicture1);}
		if($(result.shop.shopSpicture2 != null)){$("#picArea img:eq(3)").attr("src", result.shop.shopSpicture2);}
		if($(result.shop.shopSpicture3 != null)){$("#picArea img:eq(4)").attr("src", result.shop.shopSpicture3);}
		$("#infoArea h3").text(result.shop.shopName);
		var info = [result.stoName, result.shop.shopTime, result.shop.shopPrice, result.shop.shopUnit, result.shop.shopStock, 
					result.stoAddress, result.shop.shopAllPrice, result.shop.shopDiscount, result.uTel];
		var len = info.length;
		for(var i = 0; i < len; i++){
			var pText = $("#infoArea p:eq(" + i +")").text();
			if(pText == "折扣："){				
				$("#infoArea p:eq(" + i +")").text(pText + info[i] + "折");
			} else{
				$("#infoArea p:eq(" + i +")").text(pText + info[i]);
			}
		}
	}
	
	/* 输出畅销榜调用 */
	$.printRank = function() {
		var shopIds = $.cookie("hotShopId").split(",");
		var shopNames = $.cookie("hotShopName").split(",");
		var len = shopIds.length;
		for(var i = 1; i <= len; i++){
			$("#rank li:eq(" + i + ")").text(i + "." + shopNames[i - 1]);
			$("#rank li:eq(" + i + ")").attr("data-shopId", shopIds[i - 1]);
		}
	}
	
	/* 输出商品详情页评论区时调用 */
	$.printComList = function(comList) {
		var t = "";
		for(var i in comList){
			t += $.printComm(comList[i]);
		}
		return t;
	}
	
	/*商品内容页输出评论*/
	$.printComm = function(comm) {
		var pic = "";
		if(comm.comPic1 != null){pic += "<img src='" + comm.comPic1 + "' width='50px' height='50px' />&nbsp;&nbsp;"}
		if(comm.comPic2 != null){pic += "<img src='" + comm.comPic2 + "' width='50px' height='50px' />&nbsp;&nbsp;"}
		if(comm.comPic3 != null){pic += "<img src='" + comm.comPic3 + "' width='50px' height='50px' />&nbsp;&nbsp;"}
		var com = "<div class='panel panel-default'>" +
						"<div class='panel-heading'>" +
							"<img src='" + comm.uHeader + "' width='25px' style='border-radius: 50%;' />&nbsp;&nbsp;" + 
							comm.uName +	
							"<b class='pjMark'>评分：" + comm.comMark + "</b>" +
						"</div>" +
						"<div class='panel-body'>" + comm.comContent + "</div>" +
						"<div class='panel-body'>" + pic + "</div>" + 
						"<div class='panel-footer'>时间：" + comm.comTime + "</div>" +
					"</div>"
		return com;
	}
	
	/* 设置图片的大小 */
	$.shopInfoPicSize = function() {
		var width = $(".col-lg-4").width();
		$(".col-lg-4 img:first").attr("width", width);
		$(".col-lg-4 img:first").attr("height", width);
		$(".col-lg-4 img:gt(0)").attr("width", width * 0.2);
		$(".col-lg-4 img:gt(0)").attr("height", width * 0.2);
	}
});