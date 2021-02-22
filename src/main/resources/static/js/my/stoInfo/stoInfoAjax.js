/**
 * 个人店铺信息的ajax请求
 */
$(function() {
	/* 页面加载后查看店铺信息的ajax请求 */
	$.ajax({
		url : "sto?uName=" + $.cookie("uName"),
		type : "GET",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success:function(result){
			if(result.store == null){
				$("#myInfo .col-lg-6:last .panel-body").empty();
				$("#myInfo .col-lg-6:last .panel-body").append("暂无店铺，点击此处<a id='addStore'>创建</a>");
			} else {				
				$.addStoreInfo(result.store, result.now);
			}
		}
	});
	
	/*包含创建店铺的ajax请求的函数*/
	$.creStoAjax = function(name, address, mainShop, code) {
		$.ajax({
			url : "regStore",
			type : "POST",
			dataType : "json",
			contentType : "application/json;charset=utf-8",
			data:JSON.stringify({
				stoName : name,
				stoAddress : address,
				stoMainShop : mainShop,
				uId : parseInt($("#myInfoId").val()),
				code : code
			}),
			success : function(result){
				alert(result.success);
				window.location.reload();
			},
			error : function (XMLHttpRequest){
				let thisCode = XMLHttpRequest.status;
				let codeArr = [405, 406]
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
				if(XMLHttpRequest.status == 405){$("#myStore input[name='stoName']").val("");}
				$("#myStore form div:eq(4) img").attr("src", "kaptcha.jpg");
				$("#myStore input[name='addStoCode']").val("");
			}
		});
	}
	
	/* 包含修改个人店铺信息的ajax请求的函数 */
	$.setStoInfoAjax = function(name, address, mainShop) {
		$.ajax({
			url : "saveStore",
			type : "PUT",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				stoId : $("#myStoreId").val(),
				stoName : name,
				stoAddress : address,
				stoMainShop : mainShop
			}),
			success : function(result){
				alert(result.success);
				$("fieldset:last").attr("disabled", "disabled");
				$("#saveStoreBtn").attr("disabled", "disabled");
			},
			error : function (XMLHttpRequest){
				let thisCode = XMLHttpRequest.status;
				let codeArr = [405];
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
				$("#myInfo input[name='stoName']").val("");
			}
		});
	}
	
	/* 查看店铺的订单 */
	$.selStoreOiAjax = function(stoId, delGender) {
		$.ajax({
			url : "selStoOi?stoId=" + stoId + "&delGender=" + delGender,
			type : "GET",
			dataType : "json",
			success : function(oiList) {
				$("#befDelShop table tbody").empty();
				$("#befDelShop table tbody").append($.printShopOis(oiList));
				$("#befDelShop .modal-footer button:eq(1)").attr("id", "delStore");
				$("#befDelShop .modal-footer button:eq(1)").attr("data-gender", delGender);
				$("#befDelShop").modal("show");
			},
			error : function(XMLHttpRequest) {
				let codeArr = [400];
				let thisCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
			}
		});
	}
	
	/* 包含注销个人店铺的ajax请求的函数 */
	$.delStoreAjax = function(stoId, uId, delGender) {
		$.ajax({
			url : "delMySto?stoId=" + stoId + "&uId=" + uId + "&delGender=" + delGender,
			type : "DELETE",
			dataType : "text",
			contentType : "application/json;charset=UTF-8",
			success : function(result) {
				window.location.reload();
			},
			error : function(XMLHttpRequest) {
				let codeArr = [400];
				let thisCode = XMLHttpRequest.status;
				$.ifRequestError(codeArr, thisCode, XMLHttpRequest.responseJSON.message);
			}
		});
	}
});