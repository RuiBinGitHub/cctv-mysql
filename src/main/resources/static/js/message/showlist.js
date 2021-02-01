layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "確定要刪除該數據嗎？" : "Are you sure you want to delete this data?";
    const tipsText2 = language === "zh" ? "數據刪除成功！" : "Operating successfully!";
    const html1 = language === "zh" ? "<span style='color:#2AB673'>已读</span>" : "<span style='color:#2AB673'>Already</span>";
    const html2 = language === "zh" ? "<span style='color:#FFB000'>未读</span>" : "<span style='color:#FFB000'>Unread</span>";
    /** ********************************************** */
    if ($("input[name=choice]").val() === "")
        $("#title1 span:eq(0)").attr("id", "choice");
    else if ($("input[name=choice]").val() === "已读")
        $("#title1 span:eq(1)").attr("id", "choice");
    else if ($("input[name=choice]").val() === "未读")
        $("#title1 span:eq(2)").attr("id", "choice");

    $("#title1 span:eq(0)").on("click", function () {
        window.location.href = "showlist";
    });
    $("#title1 span:eq(1)").on("click", function () {
        window.location.href = "showlist?state=已读";
    });
    $("#title1 span:eq(2)").on("click", function () {
        window.location.href = "showlist?state=未读";
    });
    /** ********************************************** */
    const local1 = "/cctv/project/findinfo?id=";
    const local2 = "/cctv/markinfo/findinfo?id=";
    $("#mtab1 tbody tr").each(function (i) {
        const id = $(this).attr("id");
        $(this).find("a").click(function () {
            const message = Ajax("findinfo", {id: id});
            if (message == null)
                return null;
            $("#info1 span:eq(0)").text(message.title);
            $("#info1 span:eq(1)").text("系统管理员");
            $("#info1 span:eq(2)").text(message.username);
            $("#info1 span:eq(3)").text(message.date);
            $("#text1").text(message.username);
            $("#text2").text(message.itemname);
            $("#text2").attr("href", local1 + message.itemId);
            $("#text3").attr("href", local2 + message.markId);
            $("#info2 a").attr("target", "_blank");
            $("#tab1 tr").eq(i).find("td:eq(3)").html(html1);
            /** ****************************************** */
            layer.open({
                type: 1,
                title: "信息详情",
                area: ["640px", "460px"],
                content: $("#webform").html()
            });
        });
        /** ********************************************** */
        if ($(this).find("td:eq(3)").text() === "已读")
            $(this).find("td:eq(3)").html(html1);
        else
            $(this).find("td:eq(3)").html(html2);
        /** ********************************************** */
        $(this).find("input[type=button]").click(function () {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#CCC");
            $(this).attr("disabled", true);
            if (Ajax("delete", {id: id}))
                layer.msg(tipsText2, {icon: 1});
            setTimeout("location.reload()", 2000);
        });
    });
    /** ************************************************** */
    const state = $("#choice").val();
    laypage.render({
        elem: "page",
        curr: $("#page").data("p1"),
        count: $("#page").data("p2"),
        limit: 10,
    });
    $("#page a").on("click", function () {
        let page = $(this).text();
        if (page === "上一页")
            page = Number($("#page").data("p1") - 1);
        if (page === "下一页")
            page = Number($("#page").data("p1") + 1);
        location.href = "showlist?state=" + state + "&page=" + page;
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
