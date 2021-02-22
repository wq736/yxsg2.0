/*公共部分的js*/
$(function() {	
	/* 密码左边图标的单击事件 */
	$("#login, #register, #updPass").on("click", "span[data-type='pass']", function() {
		var passInput = $(this).next("input");
		if(passInput.attr("type") == "password"){
			passInput.attr("type", "text");
			$(this).removeClass("glyphicon-eye-open");
			$(this).addClass("glyphicon-eye-close");
		} else {
			passInput.attr("type", "password");
			$(this).removeClass("glyphicon-eye-close");
			$(this).addClass("glyphicon-eye-open");
		}
	});
	
	/* 判断权限相关 */
	$.canContinue = function(canType, value, info) {
		if($.cookie("uName") == null){	//判断用户名是否登录
			$("#login").modal("show");
		} else {
			if($.cookie(canType) == value){
				return true;
			} else {
				alert(info);
			}
		}
		return false;
	}
	
	/* 倒计时相关 */
	$.countDowun = function(time, now) {
		var will = new Date(time).getTime();
		var times = (will - now) / 1000;
		var d = parseInt(times / 60 / 60 / 24);
		d = d < 10 ? '0' + d : d;
		var h = parseInt(times / 60 / 60 % 24);
		h = h < 10 ? '0' + h : h;
		var m = parseInt(times / 60 % 60);
		m = m < 10 ? '0' + m : m;
		var s = parseInt(times % 60);
		s = s < 10 ? '0' + s : s;
		return djs = d + '天' + h + '时' + m + '分' + s + '秒';
	}
	
	/* 包含更新cookie中用户状态、等级的请求 */
	$.updUserGenSta = function() {
		$.ajax({
			url : "updUserGenSta?uName=" + $.cookie("uName"),
			type : "GET",
			dataType : "json",
			success : function(result) {
				$.cookie("canSta", result.uStatus, {path:"/"});
				$.cookie("canGen", result.uGender, {path:"/"});
			}
		});
	}
	
	/* 包含修改状态的ajax请求的函数 */
	$.updStaAjax = function(url, thisId, thisStaCode, isTr) {
		$.ajax({
			url : url,
			type : "PUT",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			data : JSON.stringify({
				id : thisId,
				staCode : thisStaCode
			}),
			success : function(result){
				if(isTr){
					$("#updStatus").modal("hide");
					var tabName = "#" + $("#updTabName").val();
					var updIndex = $("#updStaIndex").val();
					$(tabName + " tbody tr:eq(" + updIndex + ") td:last").prev().text(result[0]);
				}
			}
		});
	}
	
	/* 分页条相关开始 */
	$.cookie("pageCount", 0, {path:"/"});	//保存总记录数，总记录数为0表示第一次请求
	/*输出分页条使用 */
	$.printPageNav = function(id, on, total, begin, end) {
		var p = "";
		for(var i = begin; i <= end; i++){
			if(on == i){
				p += "<li><a><b>" + i + "</b></a></li>";						
			} else {						
				p += "<li><a>" + i + "</a></li>";											
			}
		}
		if(on > 1){
			p = "<li><a name='1'>返回第一页</a></li>" + 
			"<li><a name='" + (on - 1) + "' aria-label='Previous'>" +
			"<span aria-hidden='true'>&laquo;</span></a></li>" + p
		}
		if(on < total){
			p = p + "<li><a name='" + (on + 1) + "' aria-label='Previous'>" +
			"<span aria-hidden='true'>&raquo;</span></a></li>" +
			"<li><a name='" + total + "'>跳转到末页</a></li>"
		}
		$("#" + id + " ul[data-name='pageNav']").empty();
		$("#" + id + " ul[data-name='pageNav']").append(p);
	}
	
	$.cookie("pageOn", 1, {path:"/"});	//设置当前页默认为1
	$.cookie("pageCount", 0, {path:"/"});
	/*获取一页数据 */
	var print = "";
	$.pageNav = function(reqUrl, needSelCon, printFunc) {
		var pageOn = $.cookie("pageOn");	//设置当前页码
		var pageCount = $.cookie("pageCount");	//设置总记录数
		var where = "";
		if($.cookie("where") != null){where = $.cookie("where");}
		$.ajax({
			url : reqUrl + "?pageOn=" + pageOn + "&pageCount=" + pageCount + "&needSelCon=" + needSelCon + where,	//取出祖父元素nav的值作为ajax的请求路径，后台根据不同的请求路径返回不同的数据
			type:"GET",
			dataType:"json",
			contentType : "application/json;charset=UTF-8",
			success:function(result){
				var thisContainer = "";
				var navParClass = $("#" + reqUrl).attr("class");
				var needUpdPic = false;
				print = printFunc;
				if(navParClass == "page table"){	//商品列表页的navPar的class为page div，判断输出表格还是div块
					thisContainer = $("#" + reqUrl).parent().find("table tbody");
				} else if(navParClass == "page shopDiv"){
					thisContainer = $("#shopAll");	
					needUpdPic = true;
				} else if(navParClass == "page commDiv"){
					thisContainer = $("#pjArea #commList");
				}
				thisContainer.empty();
				thisContainer.append(printFunc(result.list));
				if(needUpdPic){$.shopAllShopArea();}

				$.printPageNav(reqUrl, result.pageOn, result.pageTotal, result.pageBegin, result.pageEnd, printFunc);
				$.cookie("pageCount", result.pageCount, {path:"/"});
				$.cookie("pageOn", result.pageOn, {path:"/"});
			}
		});
	};
	
	/*页码条标签(包括未来ajax请求刷新的页码条)的单击事件*/
	$("body").on("click", "ul[data-name='pageNav'] a", function() {
		var pageUrl = $(this).parents("nav").attr("id");
		var scrollPosition = 0;
		if(pageUrl == "commPage"){scrollPosition = $("header").height() + $(".conSite1").height() - $("#pjArea").height();}
		if($(this).attr("name") == undefined){
			$.cookie("pageOn", $(this).text(), {path:"/"});			
		} else {			
			$.cookie("pageOn", $(this).attr("name"), {path:"/"});
		}
		$.pageNav(pageUrl, false, print);
		$('html').scrollTop(scrollPosition, 100);
		
	});
	
	/*获取最后一页的页码*/
	$.getPageTotal = function(thisNav) {
		if($("#" + thisNav + " ul").text() != ""){
			if($("#" + thisNav + " a:last").text() == '跳转到末页'){
				return $("#" + thisNav + " a:last").attr("name");
			} else {
				return $("#" + thisNav + " a:last").text();
			}
		} else {
			return 1;
		}	
	}
	/* 页码条相关结束 */
	
	/* 表格删除相关开始 */
	$.delTr = function(thisTbody, addTr) {
		if($(thisTbody + " tr").text() == ""){
			$(thisTbody).append(addTr);
		} else {							
			$(thisTbody + " tr:last").after(addTr);
		}
	}
	/* 表格删除相关结束 */

	/**
	 * 处理请求出错的提示信息
	 * codeArr：后台处理后可能返回错误码数组
	 * thisCode：当前返回的错误码
	 * thisMssage：当前返回的提示信息
	 */
	$.ifRequestError = function (codeArr, thisCode, thisMessage){
		let arrLen = codeArr.length;
		for (let i = 0; i < arrLen; i++) {
			if(thisCode == codeArr[i]){
				alert(thisMessage); //弹出后台发送的提示信息
				return;
			}
		}
		alert("服务器繁忙，请稍后再试.....");  //弹出默认的提示信息
	}
});

