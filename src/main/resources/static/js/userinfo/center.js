layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    let code = "";
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "用户名称格式不正确！" : "Please check the input Nick Name!";
    const tipsText2 = language === "zh" ? "用户名称修改成功！" : "Operating successfully!";
    const tipsText3 = language === "zh" ? "密碼格式不正確，請重新輸入！" : "Please check the input password!";
    const tipsText4 = language === "zh" ? "兩次密碼不壹致，請重新輸入！" : "Two inconsistent password input!";
    const tipsText5 = language === "zh" ? "旧密码不正确，请重新输入！" : "The old password is incorrect!";
    const tipsText6 = language === "zh" ? "登录密碼修改成功！" : "Operating successfully!";
    const tipsText7 = language === "zh" ? "郵箱格式不正確，請重新輸入！" : "Please check the input E-Mail!";
    const tipsText8 = language === "zh" ? "電子郵箱已經被使用！" : "This email already exists!";
    const tipsText9 = language === "zh" ? "請輸入正確的驗證碼！" : "Please check the input code!";
    const tipsText0 = language === "zh" ? "电子郵箱绑定成功！" : "Operating successfully!";
    /** *************************************************************** */
    // 修改用户昵称
    $("#edit").on("click", function () {
        const name = $("#name").val().trim();
        if (name.length < 2 || name.length > 12) {
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        if (!confirm("确定要修改用户名称为：" + name + " ?"))
            return false;
        if (Ajax("updatename", {name: name}))
            layer.msg(tipsText2, {icon: 1});
        setTimeout("location.reload()", 2000);
    });
    /** *************************************************************** */
    $("#link1").on("click", function () {
        layer.open({
            type: 1,
            title: "重置密码",
            area: ["500px", "365px"],
            content: $("#webform1").html()
        });
    });
    $("#link2").on("click", function () {
        layer.open({
            type: 1,
            title: "重置邮箱",
            area: ["500px", "365px"],
            content: $("#webform2").html()
        });
    });
    $("#link3").on("click", function () {
        layer.msg("功能维护中...");
    });
    // 输入框获取焦点事件
    $("body").on("focus", ".textbox", function () {
        $(this).css("border-color", "#CCC");
    });
    /** *************************************************************** */
    /** 修改密码 */
    $("body").on("click", ".layui-layer-content #table1 .btn", function () {
        const oldpass = $(".layui-layer-content .textbox:eq(0)").val();
        const newpass = $(".layui-layer-content .textbox:eq(1)").val();
        const conpass = $(".layui-layer-content .textbox:eq(2)").val();
        if (oldpass.length < 6 || oldpass.length > 16) {
            $(".layui-layer-content input:eq(0)").css("border-color", "#f00");
            layer.msg(tipsText3, {icon: 2});
            return false;
        }
        if (newpass.length < 6 || newpass.length > 16) {
            $(".layui-layer-content input:eq(1)").css("border-color", "#f00");
            layer.msg(tipsText3, {icon: 2});
            return false;
        }
        if (newpass !== conpass) {
            $(".layui-layer-content input:eq(1)").css("border-color", "#f00");
            $(".layui-layer-content input:eq(2)").css("border-color", "#f00");
            layer.msg(tipsText4, {icon: 2});
            return false;
        }
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);

        if (!Ajax("updatepass", {oldpass: oldpass, newpass: newpass})) {
            $("#table1 input:eq(0)").css("border-color", "#f00");
            $(this).css("background-color", "#51C024");
            $(this).attr("disabled", false);
            layer.msg(tipsText5, {icon: 2});
        } else {
            layer.msg(tipsText6, {icon: 1});
            setTimeout("location.reload()", 2000);
        }
    });
    /** 修改邮箱 */
    $("body").on("click", ".layui-layer-content #table2 .btn", function () {
        if (!checkMail() || !checkCode())
            return false;
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        const mail = $(".layui-layer-content #table2 input[type=text]:eq(0)").val();
        const code = $(".layui-layer-content #table2 input[type=text]:eq(1)").val();
        if (Ajax("updatemail", {mail: mail, code: code}))
            layer.msg(tipsText0, {icon: 1});
        setTimeout("location.reload()", 2000);
    });

    /** *************************************************************** */
    function checkMail() {
        /** 检测邮箱 */
        const mail = $(".layui-layer-content #table2 input[type=text]:eq(0)").val();
        if (!mail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            $(".layui-layer-content #table2 input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg(tipsText7, {icon: 2});
            return false;
        }
        if (Ajax("/cctv/userview/isexistmail", {mail: mail})) {
            $(".layui-layer-content #table2 input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg(tipsText8, {icon: 2});
            return false;
        }
        return true;
    }

    /** 检测验证码 */
    function checkCode() {
        const temp = $(".layui-layer-content #table2 input[type=text]:eq(1)").val();
        if (temp.length === 0 || temp !== code) {
            $(".layui-layer-content #table2 input[type=text]:eq(1)").css("border-color", "#f00");
            layer.msg(tipsText9, {icon: 2});
            return false;
        }
        return true;
    }

    /** *************************************************************** */
    /** 获取验证码 */
    $("body").on("click", ".layui-layer-content #getCode", function () {
        if (!checkMail())
            return false;
        const mail = $(".layui-layer-content #table2 input[type]:eq(0)").val();
        if ((code = Ajax("/cctv/userview/sendmail", {"mail": mail})) === "") {
            $(".layui-layer-content #table2 input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg(tipsText7, {icon: 2});
            return false;
        }
        $(this).attr("disabled", true);
        $(this).css("color", "#CCC");
        changeTime(60);
    });

    const value = $("#getCode").val();

    function changeTime(time) {
        if (--time === 0) {
            $(".layui-layer-content #getCode").attr("value", value);
            $(".layui-layer-content #getCode").attr("disabled", false);
            $(".layui-layer-content #getCode").css("color", "#51C024");
        } else {
            $(".layui-layer-content #getCode").attr("value", time + " second");
            setTimeout(changeTime, 1000, time);
        }
    }

    /** *************************************************************** */
    /** 执行AJAX操作 */
    function Ajax(url, data) {
        let result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function (data) {
                result = data;
            }
        });
        return result;
    }
});
