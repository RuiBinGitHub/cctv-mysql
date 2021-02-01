layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "确定需要复制该项目吗！" : "Are you sure you want to copy this project!";
    const tipsText2 = language === "zh" ? "數據复制成功！" : "Operating successfully!";
    /** *************************************************************** */
    if ($("#menuText").val().trim() === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "tranview";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            location.href = "tranview?name=" + name;
    });
    /** *************************************************************** */
    /** 初始化表格 */
    const name = $("#menuText").val();
    $("#tab1 tbody tr").each(function () {
        const id = $(this).attr("id");
        $(this).find("td:eq(1) a").attr("target", "_blank");
        /** *********************************************************** */
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        $(this).find("input[type=button]").click(function () {
            if (!confirm(tipsText1))
                return false;
            if (Ajax("tranitem", {id: id}))
                layer.msg(tipsText2, {icon: 1});
            setTimeout("location.reload()", 2000);
        });
    });
    /** *************************************************************** */
    laypage.render({
        elem: "page",
        curr: $("#page").data("p1"),
        count: $("#page").data("p2"),
        limit: 15,
    });
    $("#page a").on("click", function () {
        let page = $(this).text();
        if (page === "上一页")
            page = Number($("#page").data("p1") - 1);
        if (page === "下一页")
            page = Number($("#page").data("p1") + 1);
        location.href = "tranview?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");

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
