$(document).ready(function() {
	
    var code = null;
    $("#icon").attr("title", "用户登录");
    $("#tab1 .textbox:eq(0)").attr("name", "name");
    $("#tab1 .textbox:eq(1)").attr("name", "mail");
    $("#tab1 .textbox:eq(1)").attr("list", "list");
    $("#tab1 input[type=password]:eq(0)").attr("name", "password");
    /********************************************************************/
    //输入框显示提示信息
    $("#tab1 input[type=text]:eq(0)").attr("placeholder", "Please enter your UserName");
    $("#tab1 input[type=text]:eq(1)").attr("placeholder", "Please enter your E-Mail");
    $("#tab1 input[type=text]:eq(2)").attr("placeholder", "Please enter your Code");
    $("#tab1 input[type=password]:eq(0)").attr("placeholder", "Please enter your new PassWord");
    $("#tab1 input[type=password]:eq(1)").attr("placeholder", "Please confirm your PassWord");
    /********************************************************************/
    $("#tab1 .textbox:eq(1)").on("input", function(){
    	var value = $(this).val();
    	if(value.indexOf("@") != -1)
    		value = value.substring(0 ,value.indexOf("@"));
    	var context = "";
    	context += "<option>" + value + "@qq.com</option>";
    	context += "<option>" + value + "@126.com</option>";
    	context += "<option>" + value + "@163.com</option>";
    	context += "<option>" + value + "@sina.com</option>";
    	context += "<option>" + value + "@sohu.com</option>";
    	context += "<option>" + value + "@gmail.com</option>";
    	context += "<option>" + value + "@outlook.com</option>";
    	$("#list").html(context);
    });
    /** 提交表单 */
    $("#btn2").bind("click", function () {
        if (!checkUserName())
            return false;
        if (!checkMail() || !checkCode())
            return false;
        if (!checkPassWord())
            return false;
        $(this).css("background-color", "#ccc");
        $(this).attr("disable", true);
        $(this).val("Confirm...");
        $("#form1").submit();
    });
    /********************************************************************/
    /** 检测账号 */
    function checkUserName() {
        var username = $("#tab1 .textbox:eq(0)").val();
        if (username.length < 6 || username.length > 16) {
            $("#tab1 .textbox:eq(0)").css("border-color", "#f00");
            $("#tips").text("*Please check the input UserName!");
            return false;
        }
        return true;
    }

    /** 检测密码 */
    function checkPassWord() {
        var password1 = $("#tab1 input[type=password]:eq(0)").val();
        var password2 = $("#tab1 input[type=password]:eq(1)").val();
        if (password1.length < 6 || password1.length > 16) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tips").text("*Please check the input PassWord!");
            return false;
        }
        if (password1 != password2) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tab1 input[type=password]:eq(1)").css("border-color", "#f00");
            $("#tips").text("*The two passwords you typed do not match!");
            return false;
        }
        return true;
    }

    /** 检测邮箱 */
    function checkMail() {
        if (!checkUserName())
            return false;
        var mail = $("#tab1 .textbox:eq(1)").val();
        var expr = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (mail == "" || !mail.match(expr)) {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            $("#tips").text("*Please check the input E-Mail!");
            return false;
        }
        var name = $("#tab1 .textbox:eq(0)").val();
        if (!Ajax("checknamemail", {username: name, mail: mail})) {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            $("#tips").text("*The UserName doesn't match the E-Mail!");
            return false;
        }
        return true;
    }

    /** 检测验证码 */
    function checkCode() {
        var temp = $("#tab1 .textbox:eq(2)").val();
        if (temp != null && temp == code)
        	return true;
        $("#tab1 .textbox:eq(2)").css("border-color", "#f00");
        $("#tips").text("*Please check the input Code!");
        return false;
    }

    var time = 0;
    $("#btn1").click(function() {
        if (!checkMail())
            return false;
        var mail = $("#tab1 .textbox:eq(1)").val();
        if ((code = Ajax("sendmail", {"mail": mail})) == "") {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            $("#tips").text("*Please check the input E-Mail!");
            return false;
        }
        time = 60;
        $(this).attr("disabled", true);
        $(this).css("color", "#ccc");
        changeTime();
    });

    function changeTime() {
        if (time-- == 0) {
        	$("#btn1").css("color", "#00953E");
        	$("#btn1").attr("disabled", false);
            $("#btn1").attr("value", "Get Code");
        } else {
            $("#btn1").attr("value", time + " second");
            setTimeout(changeTime, 1000);
        }
    }
    $("#tab1 .textbox").bind("input", function() {
        $(this).css("border-color", "#00953E");
        $("#tips").text("");
    });
    
    /** 执行AJAX操作 */
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            dataType: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
});
