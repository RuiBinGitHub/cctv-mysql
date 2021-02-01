$(document).ready(function () {

    let code = "";
    $("#icon").attr("title", "用户登录");
    $("#tab1 input[name=mail]").attr("list", "list");
    $("#tab1 input[type=password]:eq(0)").attr("name", "password");
    /** *************************************************************** */
    //输入框显示提示信息
    $("#tab1 input[type=text]:eq(0)").attr("placeholder", "Please enter your UserName");
    $("#tab1 input[type=text]:eq(1)").attr("placeholder", "Please enter your E-Mail");
    $("#tab1 input[type=text]:eq(2)").attr("placeholder", "Please enter your Code");
    $("#tab1 input[type=password]:eq(0)").attr("placeholder", "Please enter your new PassWord");
    $("#tab1 input[type=password]:eq(1)").attr("placeholder", "Please confirm your PassWord");
    /** *************************************************************** */
    $("#tab1 input[name=mail]").on("input", function () {
        let value = $(this).val();
        if (value == null || value === "") {
            $("#list").html("");
            return false;
        }
        if (value.indexOf("@") !== -1)
            value = value.substring(0, value.indexOf("@"));
        let context = "";
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
    $("#btn2").on("click", function () {
        if (!checkUserName())
            return false;
        if (!checkMail() || !checkCode())
            return false;
        if (!checkPassWord())
            return false;
        $(this).css("background-color", "#CCC");
        $(this).attr("disable", true);
        $(this).val("Confirm...");
        $("#form1").submit();
    });

    /** *************************************************************** */
    /** 检测账号 */
    function checkUserName() {
        const username = $("#tab1 input[name=name]").val();
        if (username.length < 6 || username.length > 16) {
            $("#tab1 input[name=name]").css("border-color", "#f00");
            $("#tips").text("*登录账号格式不正确！");
            return false;
        }
        return true;
    }

    /** 检测密码 */
    function checkPassWord() {
        const password1 = $("#tab1 input[type=password]:eq(0)").val();
        const password2 = $("#tab1 input[type=password]:eq(1)").val();
        if (password1.length < 6 || password1.length > 16) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tips").text("*登录密码格式不正确！");
            return false;
        }
        if (password1 !== password2) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tab1 input[type=password]:eq(1)").css("border-color", "#f00");
            $("#tips").text("*两次密码输入不一致！");
            return false;
        }
        return true;
    }

    /** 检测邮箱 */
    function checkMail() {
        if (!checkUserName())
            return false;
        const name = $("#tab1 input[name=name]").val();
        const mail = $("#tab1 input[name=mail]").val();
        const expr = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (mail === "" || !mail.match(expr)) {
            $("#tab1 input[name=mail]").css("border-color", "#f00");
            $("#tips").text("*电子邮箱格式不正确！");
            return false;
        }
        if (!Ajax("/cctv/userview/check", {username: name, mail: mail})) {
            $("#tab1 input[name=mail]").css("border-color", "#f00");
            $("#tips").text("*登录账号和电子邮箱不匹配！");
            return false;
        }
        return true;
    }

    /** 检测验证码 */
    function checkCode() {
        const temp = $("#tab1 input[name=code]").val();
        if (temp === "" || temp + "" !== code + "") {
            $("#tab1 input[name=code]").css("border-color", "#f00");
            $("#tips").text("*验证码输入不正确！!");
            return false;
        }
        return true;
    }

    $("#btn1").on("click", function () {
        if (!checkMail())
            return false;
        const mail = $("#tab1 input[name=mail]").val();
        if ((code = Ajax("sendmail", {"mail": mail})) === "") {
            $("#tab1 input[name=mail]").css("border-color", "#f00");
            $("#tips").text("*电子邮箱格式不正确！");
            return false;
        }
        $(this).attr("disabled", true);
        $(this).css("color", "#CCC");
        changeTime(60);
    });

    function changeTime(time) {
        if (time-- === 0) {
            $("#btn1").css("color", "#00953E");
            $("#btn1").attr("disabled", false);
            $("#btn1").attr("value", "Get Code");
        } else {
            $("#btn1").attr("value", time + " second");
            setTimeout(changeTime, 1000, time);
        }
    }

    $("#tab1 .textbox").on("input", function () {
        $(this).css("border-color", "#00953E");
        $("#tips").text("");
    });

    /** 执行AJAX操作 */
    function Ajax(url, data) {
        let result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            dataType: "json",
            success: function (data) {
                result = data;
            }
        });
        return result;
    }
});
