layui.use(["layer", "form", "laydate"], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "請輸入項目編號！" : "Please enter the Project No!";
    const tipsText2 = language === "zh" ? "項目編號不能包含'/'字符！" : "The Project No can't contain the '/' !";
    const tipsText3 = language === "zh" ? "項目編號不能包含'\\'字符！" : "The Project No can't contain the '\\' !";
    const tipsText4 = language === "zh" ? "請輸入公司名稱！" : "Please enter the Company Name!";
    const tipsText5 = language === "zh" ? "請選擇操作人員！" : "Please select Operator!";
    /** *************************************************************** */
    const myDate = new Date();
    let y = myDate.getFullYear();
    let m = myDate.getMonth() + 1;
    let d = myDate.getDate();
    m = m < 10 ? "0" + m : m;
    d = d < 10 ? "0" + d : d;

    $("#tab1 input[name=name]").attr("maxlength", 24);
    $("#tab1 input[name=name]").attr("placeholder", "最多输入24位");
    $("#tab1 input[type=date]").attr("readonly", true);
    laydate.render({
        elem: "input[name=date]",
        value: y + "-" + m + "-" + d,
        showBottom: false
    });
    /** *************************************************************** */
    $(".combtn").on("click", function () {
        // 项目名称输入为空
        if ($("input[name=name]").val() === "") {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        // 项目名称包含/
        if ($("input[name=name]").val().indexOf("/") !== -1) {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg(tipsText2, {icon: 2});
            return false;
        }
        // 项目名称包含\
        if ($("input[name=name]").val().indexOf("\\") !== -1) {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg(tipsText3, {icon: 2});
            return false;
        }
        // 公司名称输入为空
        if ($("input[name=client]").val() === "") {
            $("input[name=client]").css("background-color", "#f00");
            layer.msg(tipsText4, {icon: 2});
            return false;
        }
        // 未选择操作人员
        if ($("select[name=operator]").val() == null) {
            $("select[name=operator]").css("background-color", "#f00");
            layer.msg(tipsText5, {icon: 2});
            return false;
        }
        /** 提交数据 */
        $("input[name=name]").val($("input[name=name]").val().trim());
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });

    // 输入框值修改事件
    $("#tab1 input[type=text]").on("input", function () {
        $(this).css("background-color", "#fff");
    });
    $("#tab1 select[name=operator]").on("focus", function () {
        $(this).css("background-color", "#fff");
    });

});
