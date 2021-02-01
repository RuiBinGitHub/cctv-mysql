layui.use(["layer", "laypage"], function () {
    const laypage = layui.laypage;

    if ($.trim($("#menuText").val()) === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "showlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if ($.trim(name) !== "")
            window.location.href = "showlist?name=" + name;
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
            // 隐藏用户密码
        const pass = $(this).find("td:eq(3)").text();
        const cont1 = getrRepeats("$1", pass.length - 4);
        const text1 = pass.replace(/(.{2}).*(.{2})/, cont1);
        $(this).find("td:eq(3)").text(text1);
        // 隐藏用户邮箱
        const mail = $(this).find("td:eq(4)").text();
        const cont2 = getrRepeats("$1", mail.length - 13);
        const text2 = mail.replace(/(.{3}).*(.{9})/, cont2);
        $(this).find("td:eq(4)").text(text2);
        /** *********************************************************** */
        $(this).find("input[type=button]").click(function () {
            window.open("updateview?id=" + id);
        });
    });

    function getrRepeats(str, length) {
        for (let i = 0; i < length; ++i)
            str += "*";
        str += "$2";
        return str;
    }

    /********************************************************************/
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
        location.href = "showlist?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");

});
