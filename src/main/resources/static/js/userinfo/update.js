$(document).ready(function() {

	$("#tab1 input[type=text]").attr("readonly", true);
	$("#tab1 input[type=text]").css("background-color", "#f0f0f0");
	
	var username = $("input[type=text]:eq(1)").val();
	if (username.indexOf("0001") != -1) {
		$("select[name=role]").attr("disabled", true);
		$("input[type=submit]").attr("disabled", true);
		$("input[type=submit]").css("background-color", "#ddd");
		$("select[name=role]").attr("title", "默认管理员权限无法修改！");
		$("input[type=submit]").attr("title", "默认管理员权限无法修改！");
	}
	/** ****************************************** */
	// 隐藏用户密码
	var value = $("input[type=text]:eq(2)").val();
	var cont = getrRepeats("", value.length - 4);
	var text = value.replace(/(.{2}).*(.{2})/, cont);
	$("input[type=text]:eq(2)").val(text);
	/** ****************************************** */
	// 隐藏用户邮箱
	var value = $("input[type=text]:eq(3)").val();
	var cont = getrRepeats("", value.length - 13);
	var text = value.replace(/(.{3}).*(.{10})/, cont);
	$("input[type=text]:eq(3)").val(text);
	/** ****************************************** */
	function getrRepeats(str, length) {
		str += "$1";
		for (var i = 0; i < length; ++i)
			str += "*";
		str += "$2";
		return str;
	}

});