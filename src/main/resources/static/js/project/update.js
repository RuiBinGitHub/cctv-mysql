$(document).ready(function() {
	// 获取当前语言
	var language = $("#infoTop").text().length == 4 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "請輸入項目編號！" : "Please enter the Project No!";
    var tipsText2 = language == "zh" ? "项目名称不能包含'/'字符！" : "The Project No can't contain the '/' !";
    var tipsText3 = language == "zh" ? "项目名称不能包含'\\'字符！" : "The Project No can't contain the '\\' !";
    var tipsText4 = language == "zh" ? "請輸入公司名稱！" : "Please enter the Company Name!";
    var tipsText5 = language == "zh" ? "請選擇操作人員！" : "Please select Operator!";
    var tipsText6 = language == "zh" ? "請輸入調查日期！" : "Please enter the Date!";
	/** *************************************************************** */
	$("#tab1 input[type=text]:eq(0)").attr("maxlength", 24);
	$("#tab1 input[type=text]:eq(2)").attr("readonly", true);
	$("#tab1 input[type=text]:eq(2)").attr("title", "无法编辑");
	$("#tab1 input[type=text]:eq(2)").css("background-color", "#F0F0F0");
	$("#tab1 input[type=text]:eq(3)").attr("readonly", true);
	$("#tab1 input[type=text]:eq(3)").focus(function() {
		laydate();  // 启用日期控件
	});
	/** *************************************************************** */
	$(".combtn").click(function() {
		if ($(".textbox:eq(0)").val() == "") {
			$(".textbox:eq(0)").css("background-color", "#f00");
			showTips(tipsText1);
			return false;
		}
		if ($(".textbox:eq(0)").val().indexOf("/") != -1) {
			$(".textbox:eq(0)").css("background-color", "#f00");
			showTips(tipsText2);
			return false;
		}
		if ($(".textbox:eq(0)").val().indexOf("\\") != -1) {
			$(".textbox:eq(0)").css("background-color", "#f00");
			showTips(tipsText3);
			return false;
		}
		if ($(".textbox:eq(1)").val() == "") {
			$(".textbox:eq(1)").css("background-color", "#f00");
			showTips(tipsText4);
			return false;
		}
		if ($(".select:eq(0)").val() == null) {
			$(".select:eq(0)").css("background-color", "#f00");
			showTips(tipsText5);
			return false;
		}
		if ($(".textbox:eq(2)").val() == "") {
			$(".textbox:eq(2)").css("background-color", "#f00");
			showTips(tipsText6);
			return false;
		}
		/** 提交数据 */
		$(this).css("background-color", "#ccc");
		$(this).attr("disabled", true);
		$("#form1").submit();
	});
	/** *************************************************************** */
	/** 输入框获取焦点事件 */
	$("#tab1 input[type=text]").on("input", function() {
		$(this).css("background-color", "#fff");
	});
	$("#tab1 select[name=operator]").focus(function() {
		$(this).css("background-color", "#fff");
	});
	/** *************************************************************** */
	function showTips(text) {
		$("#Tip").show().delay(1800).hide(200);
		$("#Tip").text(text);
	}

});
