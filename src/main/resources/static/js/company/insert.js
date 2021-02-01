layui.use(["layer", "laydate"], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;

    if ($("#tips").val() !== "")
        layer.msg($("#tips").val(), {icon: 2});
    /** *************************************************************** */
    const myDate = new Date();
    let y = myDate.getFullYear() + 1;
    let m = myDate.getMonth() + 1;
    let d = myDate.getDate();
    m = m < 10 ? "0" + m : m;
    d = d < 10 ? "0" + d : d;

    /** 设置使用人数 */
    if ($("input[name=count]").val() === "") {
        $("input[name=count]").val(10);
    }

    /** 设置输入格式 */
    $("input[name=count]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57;
    });

    /** 启用日期控件 */
    laydate.render({
        elem: "input[name=term]",
        showBottom: false
    });
    /** 设置使用期限 */
    if ($("input[name=term]").val() === "")
        $("input[name=term]").val(y + "-" + m + "-" + d);

    $("input[name=name]").attr("placeholder", "请输入公司名称");
    $("input[name=code]").attr("placeholder", "账号前缀，如：MSDI");
    $("input[name=term]").attr("readonly", true);
    if ($("input[name=level]:checked").val() === undefined)
        $("#table1 input[type=radio]:eq(0)").prop("checked", true);
    /** *************************************************************** */
    const reg = /^[0-9]*[1-9][0-9]*$/;

    /** 提交数据 */
    $(".combtn").on("click", function () {
        const name = $("input[name=name]").val();
        if (name.length < 2 || name.length > 16) {
            $("input[name=name]").css("background-color", "#f00");
            layer.msg("请输入正确的公司名称!", {icon: 2});
            return false;
        }
        const code = $("input[name=code]").val();
        if (code.length < 2 || code.length > 12) {
            $("input[name=code]").css("background-color", "#f00");
            layer.msg("请输入正确的账号前缀!", {icon: 2});
            return false;
        }
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
    /** *************************************************************** */
    /** 输入框数值修改事件 */
    $(".textbox").on("input", function () {
        $(this).css("background-color", "#ffffff");
    });

});