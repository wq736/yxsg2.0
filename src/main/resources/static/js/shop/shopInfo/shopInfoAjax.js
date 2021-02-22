/**
 * 商品详情页的ajax请求或把包含ajax请求的函数
 */
$(function() {
	/*包含页面加载后发送ajax请求查询对应的商品信息的函数*/
	$.shopInfoAjax = function() {
		$.ajax({
			url:"shopInfo?shopId=" + $.cookie("shopId"),
			type:"GET",
			dataType:"json",
			contentType : "application/json;charset=UTF-8",
			success:function(result){
				$.printShopInfo(result);
				$.shopInfoPicSize();
				$.printRank();
			}
		});
		$.pageNav("commPage", true, $.printComList);
	}

	/* 加入购物车按钮的ajax请求 */
	$.joinCarAjax = function(uname, shopprice, shopnum) {
		$.ajax({
			url:"addCar",
			type:"POST",
			dataType:"text",
			contentType : "application/json;charset=UTF-8",
			data:JSON.stringify({
				uName:uname,
				shopPrice:shopprice,
				shopId:$.cookie("shopId"),
				shopSum:shopnum
			}),
			success:function(result){
				if(result != "success"){
					alert("添加失败，购买数量已超过该商品库存");
				} else {
					alert("添加成功");						
					var discount = $("p[data-name='discount']").text().substring(3);
					discount -= shopnum;
					$("p[data-name='discount']").text("库存：" + discount);
				}
			},
			error:function(){
				alert("添加失败，服务器繁忙，请稍后在尝试");
			}
		});
	}

	/*发送评论的ajax请求*/
	$.sendCommAjax = function(formData) {
		$.ajax({
			url:"addCommontBtn",
			type:"POST",
			dataType:"json",
			contentType:false,
			processData: false,
			data:formData,
			success:function(result){
				$("#addCommont").modal("hide");
				$("#addCommontForm textarea").val("");
				if($("#pjArea").children("div").length == result.page.pageSize){
					$("#pjArea .panel:last").remove();
				}
				$("#pjArea span").after($.printComm(result.comm));
				$.printPageNav("commPage", 1, result.page.pageTotal, result.page.pageBegin, result.page.pageEnd);
			}
		});
	}
});
