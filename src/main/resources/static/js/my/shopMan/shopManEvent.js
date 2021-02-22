/**
 * 我的---商品管理面板的事件
 */
$(function() {	
	/* 没有店铺时弹出提示框，有店铺时根据店铺id查询商品信息 */
	var searShop = "";
	var isJoinShop = true;
	$("a[href='#shopList']").click(function() {
		if($("#myStoreId").val() == undefined){
			alert("暂无店铺，请先创建！");
			return false;
		} else {
			if($("#myStoreId").parent().find("input[name='stoStatus']").val() == "注销"){
				alert("您的店铺已被注销！");
				return false;
			}
			if(isJoinShop){
				$.cookie("pageCount", 0, {path:"/"});
				searShop = "&stoId=" + $("#myStoreId").val();
				$.cookie("where", searShop, {path:"/"});
				$.pageNav("shopManageNav", true, $.printShopBySto);
				isJoinShop = false;
			}
		}
	});
	
	/* 筛选按钮 */
	$("#shopList button:eq(0)").click(function(){
		var where = searShop;
		var id = $("#shopList input:eq(0)").val();
		var name = $("#shopList input:eq(1)").val();
		var time = $("#shopList input:eq(2)").val();
		if(id == "" && name == "" && time == ""){
			alert("请输入至少一个筛选条件");
			return;
		}
		if(id != ""){
			$.cookie("where", where + "&shopId=" + id, {path:"/"});
		} else {
			if(name != ""){where += ("&shopName=" + name);}
			if(time != ""){where += ("&shopTime=" + time);}
			$.cookie("where", where, {path:"/"});
		}
		$.pageNav("shopManageNav", true, $.printShopBySto);
	});
	
	/* 弹出商品模态框 */
	$("#shopList button:eq(1)").click(function() {
		var info = "无法添加商品，您的账号已被禁用！";
		if(!$.canContinue("canSta", "staCan", info)){return;}	
		if($("#myStoreId").parent().find("input[name='stoStatus']").val() != "正常"){
			alert("无法添加商品，您的店铺已被禁用！");
			return;
		}
		$("#addShop").modal("show");
	});
	
	/* 添加商品 */
	$("#saveShopBtn").click(function() {
		var name = $("#addShop input[name='shopName']").val();
		var price = $("#addShop input[name='shopPrice']").val();
		var unit = $("#addShop input[name='shopUnit']").val();
		var discount = $("#addShop input[name='shopDiscount']").val();
		var stock = $("#addShop input[name='shopStock']").val();
		if($.testShop(name, price, unit, discount, stock) == false){return;}
		var bpicture = $("#addShop input[name='shopBpicture']").val();
		if (bpicture == "") {
			alert("请选择一张主要的显示图片！");
			return;
		}
		var formData = new FormData($("#addShopForm")[0]);
		formData.append("uId", $("#myInfoId").val());
		formData.append("stoId", $("#myStoreId").val());
		formData.append("pageCount", $.cookie("pageCount"));
		formData.append("pageOn", $.cookie("pageOn"));
		$.addShopAjax(formData);
	});
	
	/* 商品管理面板修改按钮 */
	$("#shopList tbody").on("click", "button[name='updShopBtn']", function() {
		var price = prompt("请设置该商品的价格(可不设置)");
		var unit = prompt("请设置该商品的单位可不设置)");
		var stock = prompt("请设置该商品的折扣(可不设置)");
		var ok = confirm("请确认是否确定修改该商品的信息：商品价格——" + price + "，商品单位——" + unit + "，商品折扣——" + stock);
		if(price == "" && unit == "" && stock == ""){
			alert("修改商品时需要的商品信息不能都为空");
			return;
		}
		if(ok){
			var thisTr = $(this).parents("tr");
			$.updShopAjax(thisTr, price, unit, stock);
		}
	});
	
	var delShopTabTr = null;
	/* 商品管理面板删除按钮 */
	$("#shopList tbody").on("click", "button[name='delShopBtn']", function(){
		if(confirm("请确定是否要删除该商品！")){
			$.selShopOiAjax($(this).parents("tr").find("td:eq(1)").text());
			delShopTabTr = $(this).parents("tr");
		}
	});	
	
	/* 生成表格按钮 */
	$("#befDelShop").on("click", "#creTable", function() {
		$(this).parent().parent().find("table").tableExport({
	        type : 'excel',
	        fileName : 'orderItem'
	    }); 
	});
	
	/* 直接删除按钮 */
	$("#befDelShop").on("click", "#delShop", function() {
		if(confirm("请先点击左边的生成表格按钮将该商品的相关订单保存到自己的电脑，防止数据丢失哦！")){
			$.delShopAjax(delShopTabTr);
			delShopTabTr = null;
			$("#befDelShop").modal("hide");
		}
	});
});