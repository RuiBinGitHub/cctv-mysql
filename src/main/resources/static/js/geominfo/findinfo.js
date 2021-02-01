layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    // 获取当前语言
    const language = $(".title:eq(0) span").text().length === 4 ? "zh" : "en";
    const tipsTest1 = language === "zh" ? "请输入正确的管道坐标！" : "Please check the input data!";
    const tipsTest2 = language === "zh" ? "数据保存成功！" : "Operating successfully!";
    /** *************************************************************** */
    // 表头按钮点击事件
    $("#import").on("click", function () {
        $("#file").click();
    });
    $("#file").change(function () {
        $("#form3").submit();
    });
    /** *************************************************************** */
    $("#tab1 tbody tr td input").on("click", function () {
        const row = $(this).parents("tr");
        $("#form1 input[name=id]").val(row.attr("id"));
        $("#form1 .pipeid").val($(this).attr("id"));
        $(".lable:eq(0)").text("Start MH：" + row.find("td:eq(2)").text());
        $("#form1 input[name=smhx]").val(row.find("td:eq(3)").text());
        $("#form1 input[name=smhy]").val(row.find("td:eq(4)").text());
        $("#form1 input[name=smhh]").val(row.find("td:eq(5)").text());
        $(".lable:eq(1)").text("Finish MH：" + row.find("td:eq(6)").text());
        $("#form1 input[name=fmhx]").val(row.find("td:eq(7)").text());
        $("#form1 input[name=fmhy]").val(row.find("td:eq(8)").text());
        $("#form1 input[name=fmhh]").val(row.find("td:eq(9)").text());

        $("#form2 input[name=id]").val(row.attr("id"));
        $("#form2 .pipeid").val($(this).attr("id"));
        $("#form2 input[name=smhGradeA]").val(row.data("smha"));
        $("#form2 input[name=smhGradeB]").val(row.data("smhb"));
        $("#form2 input[name=fmhGradeA]").val(row.data("fmha"));
        $("#form2 input[name=fmhGradeB]").val(row.data("fmhb"));

        /** 设置样式 */
        $("#tab1 tbody tr").find("td:eq(0)").text("");
        row.find("td:eq(0)").text("▶");
    });
    $("#tab1 tr input[type=button]").eq(0).click();
    /** *************************************************************** */
    /** 设置管道坐标输入框只能输入数字 */
    $(".table input[type=text]").on("input", function () {
        if ($(this).val() === "" || isNaN($(this).val()))
            $(this).css("background-color", "#FF0000");
        else
            $(this).css("background-color", "#F3F3F3");
    });
    $(".table input[type=text]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });
    /** *************************************************************** */
    /** 输入管道坐标 */
    $("#common").on("click", function () {
        for(let i = 0; i < $(".table input").length; i++){
            const value = $(".table input").eq(i).val();
            if (value === "" || isNaN(value)) {
                layer.msg(tipsTest1, {icon: 2});
                return false;
            }
        }
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        if (Ajax("inputvalue", $("#form1").serialize()))
            layer.msg(tipsTest2, {icon: 1});
        setTimeout("location.reload()", 2000);
    });
    /** *************************************************************** */
    /** 输入管道等级 */
    $("#submit").on("click", function () {
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        if (Ajax("inputgrade", $("#form2").serialize()))
            layer.msg(tipsTest2, {icon: 1});
        setTimeout("location.reload()", 2000);
    });

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
