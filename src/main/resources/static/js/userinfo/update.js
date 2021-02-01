$(document).ready(function () {

    $("#tab1 input[type=text]").attr("readonly", true);
    $("#tab1 input[type=text]").css("background-color", "#f0f0f0");
    /** *************************************************************** */
    const username = $("input[name=name]").val();
    if (username.indexOf("0001") !== -1 && username !== "100001") {
        $("select[name=role]").attr("disabled", true);
        $("input[type=submit]").attr("disabled", true);
        $("input[type=submit]").css("background-color", "#ddd");
        $("select[name=role]").attr("title", "默认管理员权限无法修改！");
        $("input[type=submit]").attr("title", "默认管理员权限无法修改！");
    }
    /** *************************************************************** */
    /** 隐藏用户密码 */
    const pass = $("input[type=text]:eq(2)").val();
    const cont1 = getrRepeats("", pass.length - 4);
    const text1 = pass.replace(/(.{2}).*(.{2})/, cont1);
    $("input[type=text]:eq(2)").val(text1);

    /** *************************************************************** */
    /** 隐藏用户邮箱 */
    const mail = $("input[type=text]:eq(3)").val();
    const cont2 = getrRepeats("", mail.length - 9);
    const text2 = mail.replace(/(.{3}).*(.{10})/, cont2);
    $("input[type=text]:eq(3)").val(text2);

    /** *************************************************************** */
    function getrRepeats(str, length) {
        str += "$1";
        for (let i = 0; i < length; ++i)
            str += "*";
        str += "$2";
        return str;
    }

});