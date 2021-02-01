layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "確定要撤回該項目嗎？" : "Are you sure you want to revoke this data?";
    const tipsText2 = language === "zh" ? "數據撤回成功！" : "Operating successfully!";
    const tipsText3 = language === "zh" ? "確定要移除該數據嗎？" : "Are you sure you want to remove this data?";
    const tipsText4 = language === "zh" ? "數據移除成功！" : "Operating successfully!";
    const btnText1 = language === "zh" ? "编辑" : "Down";
    const btnText2 = language === "zh" ? "提交" : "Rev";
    const btnText3 = language === "zh" ? "删除" : "Del";
    /** *************************************************************** */
    if ($("#menuText").val().trim() === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.keyCode === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "findlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            window.location.href = "findlist?name=" + name;
    });
    /** *************************************************************** */
    /** 项目合并 */
    $("#combin").on("click", function () {
        window.open("combineview");
    });
    /** 地图展示 */
    $("#commap").on("click", function () {
        window.open("/cctv/geominfo/showlist");
    });
    /** *************************************************************** */
    const name = $("input[name=name]").val();
    const sort = $("input[name=sort]").val();
    $("#tab1 thead th").each(function () {
        const option = $(this).data("name");
        if (option === undefined)
            return true;
        $(this).on("click", function () {
            location.href = "findlist?name=" + name + "&sort=" + option + "1";
        });
        if (sort === option)
            $(this).text($(this).text() + "↑");
        if (sort === option + "1") {
            $(this).text($(this).text() + "↓");
            $(this).off("click");
            $(this).on("click", function () {
                location.href = "findlist?name=" + name + "&sort=" + option;
            });
        }
    });
    /** *************************************************************** */
    /** 初始化表格 */
    $("#tab1 tbody tr").each(function (n) {
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
        $(this).find("img").click(function () {
            const text = $(this).prev().text();
            if ($(this).attr("src") === "/cctv/img/展开.png") {
                if ($(this).data("name") === undefined) {
                    $(this).parents("tr").after(getContext(text, id, n));
                    $(this).data("name", "已完成");
                } else
                    $("#tab1 tbody ." + n).show();
                $(this).attr("src", "/cctv/img/收起.png");
            } else {
                $("#tab1 tbody ." + n).hide();
                $(this).attr("src", "/cctv/img/展开.png");
            }
        });
    });
    /** 下载项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(1)", function () {
        const id = $(this).parents("tr").attr("id");
        window.open("/cctv/download?id=" + id);
    });
    /** 撤回项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(2)", function () {
        const id = $(this).parents("tr").attr("id");
        layer.confirm(tipsText1, {
            btn: ["确定", "取消"]
        }, function () {
            if (Ajax("revoke", {id: id}))
                layer.msg(tipsText2, {icon: 1});
            setTimeout("location.reload()", 2000);
        });
    });
    /** 移除项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(3)", function () {
        const id = $(this).parents("tr").attr("id");
        layer.confirm(tipsText3, {
            btn: ["确定", "取消"]
        }, function () {
            if (Ajax("remove", {id: id}))
                layer.msg(tipsText4, {icon: 1});
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
        location.href = "findlist?name=" + name + "&sort=" + sort + "&page=" + page;
    });
    $(".layui-disabled").off("click");

    /** *************************************************************** */
    function getContext(name, id, no) {
        const data = Ajax("viewlist2", {name: name, id: id});
        let context = "";
        for (let i = 0; data != null && i < data.length; i++) {
            context += "<tr id='" + data[i].id + "' class='" + no + "' target='_blank'>";
            context += "  <td>-</td>";
            context += "  <td><a href='checkview?id=" + data[i].id + "'>" + data[i].name + "</a></td>";
            context += "  <td>" + data[i].workorder + "</td>";
            context += "  <td>" + data[i].client + "</td>";
            context += "  <td>" + data[i].slope + "</td>";
            context += "  <td>" + data[i].standard + "</td>";
            context += "  <td>" + data[i].operator + "</td>";
            context += "  <td>" + data[i].date + "</td>";
            context += "  <td style='text-indent: 4px'>";
            context += "    <input type='button' class='layui-btn layui-btn-xs' value='" + btnText1 + "'/>";
            context += "    <input type='button' class='layui-btn layui-btn-xs layui-btn-normal' value='" + btnText2 + "'/>";
            context += "    <input type='button' class='layui-btn layui-btn-xs layui-btn-danger' value='" + btnText3 + "'/>";
            context += "  </td>";
            context += "</tr>";
        }
        if (data == null || data.length === 0) {
            context += "<tr class='" + no + "'>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "  <td>-</td>";
            context += "</tr>";
        }
        return context;
    }
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
