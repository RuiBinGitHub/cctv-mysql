$(document).ready(function() {

	var language = $("#infoTop").text().length == 4 ? "zh" : "en";
    var tipsText = language == "zh" ? "確定要合並項目嗎？" : "Are you sure you want to combine projects?";
    /** *************************************************************** */
    $("#menuBtn1").attr("disabled", true);
    $("#menuText").on("input", function() {
        $("#menuBtn1").attr("disabled", $(this).val() == "");
    });
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
    	$("#menuText").val("");
        initlist(Ajax("combinelist", null));

    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            initlist(Ajax("combinelist", {name: name}));
    });
    /** *************************************************************** */
    initlist(Ajax("combinelist", null));
    function initlist(data) {
        var context = "";
        for (var i = 0; i < data.length; i++) {
            context += "<tr align='center'>";
            context += "  <td width='50px'><input type='checkbox' value=" + data[i].id + "></td>";
            context += "  <td width='30px'>" + (i + 1) + "</td>";
            context += "  <td width='200px'><a>" + data[i].name + "</a></td>";
            context += "  <td width='240px'>" + data[i].client + "</td>";
            context += "  <td width='100px'>" + data[i].slope + "</td>";
            context += "  <td width='120px'>" + data[i].standard + "</td>";
            context += "  <td width='120px'>" + data[i].user.name + "</td>";
            context += "  <td width='120px'>" + data[i].date + "</td>";
            context += "</tr>";
        }
        $("#tab1").html(context);
        /** *********************************************************** */
        var list = $("#fieldset div").map(function() {
            return $(this).attr("id");
        }).get(); // 获取ID列表
        var name = $("#menuText").val();
        $("#tab1 tr").each(function(i) {
            var id = $(this).find("input").val();
            $(this).find("a").attr("target", "_blank");
            $(this).find("a").attr("href", "findinfo?id=" + id);
            $(this).find("input").prop("checked", list.indexOf(id) != -1);
            /** ******************************************************* */
            var text = $(this).find("td:eq(2)").text();
            var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(2) a").html(cont);
        });
    }
    /** *************************************************************** */
    // 设置复选框点击事件
    $("#tab1").on("click", "tr", function() {
    	$(this).find("input").click();
    });
    $("#tab1").on("click", "input", function(e) {
    	e.stopPropagation();
        var id = $(this).val();
        if ($(this).is(":checked") && $("#fieldset div").length < 20) {
            var name = $(this).parents("tr").find("td:eq(2)").text();
            var text = "<div id='" + id + "'>" + name + "</div>";
            $("#fieldset").append(text);
        } else
            $("div[id=" + id + "]").remove();
    });
    $("#fieldset").on("dblclick", "div", function() {
        var id = $(this).attr("id");
        $("input[value=" + id + "]").prop("checked", false);
        $(this).remove();
    });
    /** *************************************************************** */
    $(".combtn").click(function() {
        var list = $("#fieldset div").map(function() {
            return $(this).attr("id");
        }).get();
        if (list.length < 2 || !confirm(tipsText))
            return false;
        $(this).css("background-color", "#ccc");
        $(this).attr("disabled", true);
        $("#form input").val(list);
        $("#form").submit();
    });
    /** 显示提示信息 */
    function showTips(text) {
        $("#Tip").show().delay(1800).hide(200);
        $("#Tip").text(text);
    }
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
});
