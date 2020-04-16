$(document).ready(function() {

	$("#name").attr("autofocus", true);
	$("#pass").attr("type", "password");
	$("#name").attr("placeholder", "Please enter your username");
	$("#pass").attr("placeholder", "Please enter your password");
	/** ***************************************************************** */
	$("#btn").click(function() {
		if ($("#name").val() == "") {
			$("#tips").text("*Please enter your UserName!");
			$("#name").css("border-color", "#f00");
			return false;
		}
		if ($("#pass").val() == "") {
			$("#tips").text("*Please enter your PassWord!");
			$("#pass").css("border-color", "#f00");
			return false;
		}
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("Sign In...");
		$("#form1").submit();
	});

	$("#name, #pass").bind("input", function() {
		$(this).css("border-color", "#00953E");
		$("#tips").text("");
	});
	$("#name, #pass").keydown(function() {
		if (event.keyCode == 13)
			$("#btn").click();
	});

});