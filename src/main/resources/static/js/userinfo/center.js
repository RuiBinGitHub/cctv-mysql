$(document).ready(function() {
    var code = null;
    var language = $("#infoTop").text() == "個人中心" ? "zh" : "en";
    var tipsText1 = language == "zh" ? "用户名称格式不正确！" : "Please check the input Nick Name!";
    var tipsText2 = language == "zh" ? "用户名称修改成功！" : "Operating successfully!";
    var tipsText3 = language == "zh" ? "*密碼格式不正確，請重新輸入！" : "*Please check the input password!";
    var tipsText4 = language == "zh" ? "*兩次密碼不壹致，請重新輸入！" : "*Two inconsistent password input!";
    var tipsText5 = language == "zh" ? "*旧密码不正确，请重新输入！" : "*The old password is incorrect!";
    var tipsText6 = language == "zh" ? "登录密碼修改成功！" : "Operating successfully!";
    var tipsText7 = language == "zh" ? "*郵箱格式不正確，請重新輸入！" : "*Please check the input E-Mail!";
    var tipsText8 = language == "zh" ? "*電子郵箱已經被使用！" : "*This email already exists!";
    var tipsText9 = language == "zh" ? "*請輸入正確的驗證碼！" : "*Please check the input code!";
    var tipsText0 = language == "zh" ? "电子郵箱绑定成功！" : "Operating successfully!";
    /** *************************************************************** */
    $("#edit").click(function() {
        var name = $("#name").val();
        if (name.length < 2 || name.length > 12) {
            showTips(tipsText1);
            return false;
        }
        if (!confirm("确定要修改用户名称为：" + name))
        	return false;
        if (Ajax("updatename", {name: name}))
        	showTips(tipsText2);
        setTimeout("location.reload()", 2000);
    });
    //获取使用期限
    var date = $(".table1 tr:eq(2) td:eq(1)").text();
    var term = $(".table1 tr:eq(2) td:eq(3)").text();
    $(".table1 tr:eq(2) td:eq(3)").text(getDate(date, term));
    function getDate(date, idate) {   // 计算使用期限
        var date = new Date(date);
        date.setFullYear(date.getFullYear() + Number(idate));
        var y = date.getFullYear();
        var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return y + "-" + m + "-" + d;
    }
    /** *************************************************************** */
    $("#link1").click(function() {
        $("#page").show();
        $("#webform1 .textbox").val("");
        $("#webform1 a:eq(0)").text("");
        $("#webform1").show();
        $("#webform2").hide();
    });
    $("#link2").click(function() {
        $("#page").show();
        $("#webform2 .textbox").val("");
        $("#webform2 a:eq(0)").text("");
        $("#webform1").hide();
        $("#webform2").show();
    });
    $("#link3").click(function() {
        showTips("系统维护中...");
    });
    // 输入框获取焦点事件
    $("#webform1 .textbox").focus(function() {
        $(this).css("border-color", "#ccc");
        $("#webform1 a:eq(0)").text("");
    });
    $("#webform2 .textbox").focus(function() {
        $(this).css("border-color", "#ccc");
        $("#webform2 a:eq(0)").text("");
    });
    $(".colse").click(function() {
        $("#webform1 .textbox").css("border-color", "#ccc");
        $("#webform2 .textbox").css("border-color", "#ccc");
        $("#page").hide();
    });
    /********************************************************************/
    /** 修改密码 */
    $("#table1 .btn:eq(0)").click(function() {
        var name = $("#table1 input:eq(0)").val();
        var pass1 = $("#table1 input:eq(1)").val();
        var pass2 = $("#table1 input:eq(2)").val();
        if (name.length < 6 || name.length > 16) {
            $("#table1 input:eq(0)").css("border-color", "#f00");
            $("#table1 a:eq(0)").text(tipsText3);
            return false;
        }
        if (pass1.length < 6 || pass1.length > 16) {
            $("#table1 input:eq(1)").css("border-color", "#f00");
            $("#table1 a:eq(0)").text(tipsText3);
            return false;
        }
        if (pass1 != pass2) {
            $("#table1 input:eq(1)").css("border-color", "#f00");
            $("#table1 input:eq(2)").css("border-color", "#f00");
            $("#table1 a:eq(0)").text(tipsText4);
            return false;
        }
        $(this).attr("disabled", true);
        $(this).css("background-color", "#CCC");
        if (!Ajax("updatepass", {name: name, pass: pass1})) {
        	$("#table1 a:eq(0)").text(tipsText5);
        	$("#table1 input:eq(0)").css("border-color", "#f00");
            $(this).css("background-color", "#51C024");
            $(this).attr("disabled", false);
        } else {
        	showTips(tipsText6);
        	setTimeout("location.reload()", 2000);
        }
    });
    /** 修改邮箱 */
    $(".btn:eq(1)").click(function() {
    	if (!checkMail() || !checkCode())
            return false;
        $(this).attr("disabled", true);
        $(this).css("background-color", "#CCC");
        var mail = $("#table2 input[type=text]:eq(0)").val();
        var code = $("#table2 input[type=text]:eq(1)").val();
        if (Ajax("updatemail", {mail: mail, code: code})) 
        	showTips(tipsText0);
       	setTimeout("location.reload()", 2000);
    });
    /********************************************************************/
    function checkMail() {
        /** 检测邮箱 */
        var mail = $("#table2 input[type=text]:eq(0)").val();
        if (!mail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            $("#table2 input[type=text]:eq(0)").css("border-color", "#f00");
            $("#table2 a:eq(0)").text(tipsText7);
            return false;
        }
        if (Ajax("/cctv/userview/isexistmail", {mail: mail})) {
            $("#table2 input[type=text]:eq(0)").css("border-color", "#f00");
            $("#table2 a:eq(0)").text(tipsText8);
            return false;
        }
        return true;
    }
    /** 检测验证码 */
    function checkCode() {
        var temp = $("#table2 input[type=text]:eq(1)").val();
        if (temp.length == 0 || temp != code) {
            $("#table2 input[type=text]:eq(1)").css("border-color", "#f00");
            $("#table2 a:eq(0)").text(tipsText9);
            return false;
        }
        return true;
    }
    /********************************************************************/
    var time = 60;
    $("#getCode").click(function() {
        /** 获取验证码 */
        if (!checkMail())
            return false;
        var mail = $("#table2 input[type]:eq(0)").val();
        if ((code = Ajax("/cctv/userview/sendmail", {"mail": mail})) == "") {
            $("#table2 input[type=text]:eq(0)").css("border-color", "#f00");
            $("#table2 a:eq(0)").text(tipsText7);
            return false;
        }
        time = 60;
        $(this).attr("disabled", true);
        $(this).css("color", "#ccc");
        changeTime();
    });
    var value = $("#getCode").val();
    function changeTime() {
        if (--time == 0) {
            $("#getCode").attr("value", value);
            $("#getCode").attr("disabled", false);
            $("#getCode").css("color", "#51C024");
        } else {
            $("#getCode").attr("value", time + " second");
            setTimeout(changeTime, 1000);
        }
    }
    /********************************************************************/
    /** 显示提示信息 */
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips").text(text);
    }
    /** 执行AJAX操作 */
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
});
