layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "請輸入人員全名！" : "Please enter the Full Name!";
    const tipsText2 = language === "zh" ? "人員名稱已存在！" : "The Full Name already exists!";
    const tipsText3 = language === "zh" ? "請輸入人員名字！" : "Please enter the First Name!";
    const tipsText4 = language === "zh" ? "請輸入人員姓氏！" : "Please enter the Last Name!";
    const tipsText5 = language === "zh" ? "請輸入會員等級！" : "Please enter the Member Level!";
    const tipsText6 = language === "zh" ? "請輸入會員編號！" : "Please enter the Member Number!";
    if (language === "zh") {
        $("input[name=name]").attr("placeholder", "人員名稱，2-12位");
        $("input[name=firstname]").attr("placeholder", "人員名字，1-6位");
        $("input[name=lastname]").attr("placeholder", "人員姓氏，1-6位");
    } else {
        $("input[name=name]").attr("placeholder", "Operator Full Name,2-12 place");
        $("input[name=firstname]").attr("placeholder", "Operator First Name,1-6 place");
        $("input[name=lastname]").attr("placeholder", "Operator Last Name,1-6 place");
    }
    /** *************************************************************** */
    /** 提交数据 */
    $(".combtn").on("click", function () {
        if ($("input[name=name]").val() === "") {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        if (Ajax("isexistname", {name: $("input[name=name]").val()})) {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg(tipsText2, {icon: 2});
            return false;
        }
        if ($("input[name=firstname]").val() === "") {
            $("input[name=firstname]").css("background-color", "#f00");
            layer.msg(tipsText3, {icon: 2});
            return false;
        }
        if ($("input[name=lastname]").val() === "") {
            $("input[name=lastname]").css("background-color", "#f00");
            layer.msg(tipsText4, {icon: 2});
            return false;
        }
        if ($("input[name=membergrades]").val() === "") {
            $("input[name=membergrades]").css("background-color", "#f00");
            layer.msg(tipsText5, {icon: 2});
            return false;
        }
        if ($("input[name=membernumber]").val() === "") {
            $("input[name=membernumber]").css("background-color", "#f00");
            layer.msg(tipsText6, {icon: 2});
            return false;
        }
        /** 提交数据 */
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });

    /** 输入框获取焦点事件 */
    $("#tab1 input[type=text]").on("input", function () {
        $(this).css("background-color", "#FFFFFF");
    });

    /** *************************************************************** */
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
