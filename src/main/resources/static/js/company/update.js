layui.use(["layer", "laydate"], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;

    /** *************************************************************** */
    $("input[name=name], input[name=code]").css("background-color", "#F1F1F1");
    $("#table1 input[type=radio]:eq(2)").attr("title", "*试用版本公司信息无法进行修改！");
    $("#table1 input[type=radio]:eq(2)").attr("disabled", true);
    $("input[name=term]").attr("readonly", true);
    laydate.render({
        elem: "input[name=term]",
        showBottom: false
    });
    /** ************************************************************** */
    const reg = /^[0-9]*[1-9][0-9]*$/;

    /** 提交数据 */
    $(".combtn").on("click", function () {
        const count = $("input[name=count]").val();
        if (count === "" || !reg.test(count) || count < 1 || count > 1000) {
            $("input[name=count]").css("background-color", "#f00");
            layer.msg("请输入正确的使用人数!", {icon: 2});
            return false;
        }
        /** *********************************************************** */
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });
    /** ************************************************************** */
    /** 输入框只能输入数字 */
    $("input[name=count]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57;
    });

    /** 输入框数值修改事件 */
    $(".textbox").on("input", function () {
        $(this).css("background-color", "#ffffff");
    });

});
