/**
 * 个人店铺信息面板的函数
 */
$(function() {
	var thisSTime;
	var storeFrom = $("#myStoreId").parent();
	var stoStaInp = storeFrom.find("input[name='stoStatus']");
	/* 显示店铺信息时调用 */
	$.addStoreInfo = function(store, now) {
		var sta = store.stoStatus;
		$("#myStoreId").val(store.stoId);
		storeFrom.find("input[name='stoName']").val(store.stoName);
		storeFrom.find("input[name='stoAddress']").val(store.stoAddress);
		storeFrom.find("input[name='stoMainShop']").val(store.stoMainShop);
		storeFrom.find("input[name='stoTime']").val(store.stoTime);
		stoStaInp.attr("data-status", sta);
		storeFrom = null;
		if($("#myInfo div:eq(1) input[name='uStatus']").val() == "注销"){sta = "注销"}
		if(sta == "注销"){
			stoStaInp.val(sta);
			alert("您的店铺已被注销！请在店铺被删除之前确认订单是否有误，无误后下载订单信息并删除店铺！");
			$.selStoreOiAjax(store.stoId, 2);
		}
		else if(sta != "正常" && sta != "永久禁用"){	
			thisSTime = now;
			stoStaInp.val("您的店铺已被禁用：" + $.countDowun(sta, now) + "后解封！");
			setInterval($.flushSStatus, 1000);
		} else {
			stoStaInp.val(sta);
		}
	}
	
	/* 每秒刷新状态的函数 */
	closeSFlush = false;
	$.flushSStatus = function() {
		if(!closeSFlush){
			var sta = stoStaInp.attr("data-status");
			thisSTime += 1000;
			var val = "您的店铺已被禁用：" + $.countDowun(sta, thisSTime) + "后解封！";
			stoStaInp.val(val);
			if(val == "您的店铺已被禁用：00天00时00分0-1秒后解封！"){
				$.updStaAjax("updStoSta", $("#myStoreId").val(), "-1", false);
				if(confirm("店铺已经解封，是否刷新页面?")){
					window.location.reload();
				} else {
					closeSFlush = true;
					stoStaInp.val("正常");
				}
			}
		}
	}
	
	/*验证店铺信息的函数*/
	$.testStore = function(name, address, mainShop) {
		if(name == "" || address == "" || mainShop == ""){
			alert("内容不能为空");
			return false;
		}
		if(name != null){
			var nameTest = /^[\u4e00-\u9fa5\-_·0-9A-z]+$/;
			if(!nameTest.test(name) || name.length > 15){
				alert("名称不合法(用户名只能由中文、英文大小写、数字或下划线组成，最多15个字符)");
				return false;
			}
		}
		if(address != null){
			var addressTest = /^[\u4e00-\u9fa5\-_·0-9A-z]+$/;
			if(!addressTest.test(address) || address.length > 50){
				alert("地址不合法(用户名只能由中文、英文大小写、数字或_ - .组成，最多15个字符)");
				return false;
			}
		}
		if(mainShop != null){
			if(mainShop.length > 25){
				alert("字数不能超过25)");
				return false;
			}			
		}
		return true;
	}
});