layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "最少选择两个项目！" : "Select at least 2 items!";
    const tipsText2 = language === "zh" ? "確定要合並項目嗎？" : "Are you sure you want to combine projects?";
    /** *************************************************************** */
    $("#menuBtn1").attr("disabled", true);
    $("#menuText").on("input", function () {
        $("#menuBtn1").attr("disabled", $(this).val() === "");
    });
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        $("#menuText").val("");
        initlist(Ajax("combinelist", null));
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            initlist(Ajax("combinelist", {name: name}));
    });
    /** *************************************************************** */
    initlist(Ajax("combinelist", null));

    function initlist(data) {
        let context = "";
        for (let i = 0; i < data.length; i++) {
            context += "<tr>";
            context += "    <td width='50px'><input type='checkbox' value=" + data[i].id + "></td>";
            context += "    <td width='30px'>" + (i + 1) + "</td>";
            context += "    <td width='200px'><a>" + data[i].name + "</a></td>";
            context += "    <td width='240px'>" + data[i].client + "</td>";
            context += "    <td width='100px'>" + data[i].slope + "</td>";
            context += "    <td width='120px'>" + data[i].standard + "</td>";
            context += "    <td width='120px'>" + data[i].user.name + "</td>";
            context += "    <td>" + data[i].date + "</td>";
            context += "</tr>";
        }
        $("#tab1 tbody").html(context);
        /** *********************************************************** */
        const list = $("#fieldset div").map(function () {
            return $(this).attr("id");
        }).get(); // 获取已选项目ID列表

        const name = $("#menuText").val();
        $("#tab1 tbody tr").each(function () {
            const id = $(this).find("input").val();
            $(this).find("a").attr("target", "_blank");
            $(this).find("a").attr("href", "findinfo?id=" + id);
            $(this).find("input").prop("checked", list.indexOf(id) !== -1);
            /** ******************************************************* */
            const text = $(this).find("td:eq(2)").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(2) a").html(cont);
        });
    }

    /** *************************************************************** */
    // 设置复选框点击事件
    $("#tab1").on("click", "tbody tr td input", function () {
        const id = $(this).val();
        if ($(this).is(":checked") && $("#fieldset div").length < 20) {
            const name = $(this).parents("tr").find("a").text();
            $("#fieldset").append("<div id='" + id + "'>" + name + "</div>");
        } else
            $("#fieldset div[id=" + id + "]").remove();
    });
    // 设置双击标签事件
    $("#fieldset").on("dblclick", "div", function () {
        const id = $(this).attr("id");
        $("input[value=" + id + "]").prop("checked", false);
        $(this).remove();
    });
    /** *************************************************************** */
    $(".combtn").on("click", function () {
        const list = $("#fieldset div").map(function () {
            return $(this).attr("id");
        }).get();
        /** 至少选择两个项目 */
        if (list != null && list.length < 2) {
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        /** 确定合并项目 */
        $("input[name=list]").val(list);
        if (!confirm(tipsText2))
            return false;
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        $("#form1").submit();
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
