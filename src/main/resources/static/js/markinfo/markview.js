layui.use(["layer", "laypage"], function () {
    const laypage = layui.laypage;

    /** *************************************************************** */
    if ($.trim($("#menuText").val()) === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "markview";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if ($.trim(name) !== "")
            window.location.href = "markview?name=" + name;
    });
    /** *************************************************************** */
    const name = $("#menuText").val();
    $("#tab1 tbody tr").each(function () {
        const id = $(this).attr("id");
        /** *********************************************************** */
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        $(this).find("input:eq(0)").click(function () {
            window.open("/cctv/download?id=" + id);
        });
        $(this).find("input:eq(1)").click(function () {
            window.open("markitem?id=" + id);
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
        location.href = "markview?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");

});
