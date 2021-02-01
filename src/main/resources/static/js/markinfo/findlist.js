layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "確定要移除該數據嗎？" : "Are you sure you want to remove this data?";
    const tipsText2 = language === "zh" ? "數據移除成功！" : "Operating successfully!";
    /** *************************************************************** */
    if ($.trim($("#menuText").val()) === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "findlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if ($.trim(name) !== "")
            window.location.href = "findlist?name=" + name;
    });
    /** *************************************************************** */
    const name = $("#menuText").val();
    $("#tab1 tbody tr").each(function () {
        const id = $(this).attr("id");
        $(this).find("a").attr("target", "_blank");
        /** *********************************************************** */
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        const score1 = $(this).find("td:eq(4)").text();
        const score2 = $(this).find("td:eq(5)").text();
        $(this).find("td:eq(4)").text(Number(score1).toFixed(2));
        $(this).find("td:eq(5)").text(Number(score2).toFixed(2));
        if (score1 < 95)
            $(this).find("td:eq(4)").css("color", "#FF1000");
        else
            $(this).find("td:eq(4)").css("color", "#479911");
        if (score2 < 85)
            $(this).find("td:eq(5)").css("color", "#FF1000");
        else
            $(this).find("td:eq(5)").css("color", "#479911");
        /** *********************************************************** */
        $(this).find("input:eq(0)").click(function () {
            window.open("findinfo?id=" + id);
        });
        $(this).find("input:eq(1)").click(function () {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#CCC");
            $(this).attr("disabled", true);
            if (Ajax("remove", {id: id}))
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
        location.href = "findlist?name=" + name + "&page=" + page;
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
