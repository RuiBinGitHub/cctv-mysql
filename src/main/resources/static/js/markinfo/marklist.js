$(document).ready(function() {

	var language = $("#infoTop").text().length == 4 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "確定要刪除該數據嗎？" : "Are you sure you want to delete this data?";
    var tipsText2 = language == "zh" ? "數據刪除成功！" : "Operating successfully!";
    /********************************************************************/
    if ($("#menuText").val().trim() == "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
        window.location.href = "marklist";
    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            window.location.href = "marklist?name=" + name;
    });
    /********************************************************************/
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
        /*************************************************/
        var id = $(this).find("a").attr("id");
        $(this).find("a").attr("target", "_blank");
        $(this).find("a").attr("href", "/cctv/project/findinfo?id=" + id);
        /*************************************************/
        if (name.trim() != "") {
        	var text = $(this).find("td:eq(1) a").text();
        	var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /*************************************************/
        var score1 = $(this).find("td:eq(5)").text();
        var score2 = $(this).find("td:eq(6)").text();
        $(this).find("td:eq(5)").text(Number(score1).toFixed(2));
        $(this).find("td:eq(6)").text(Number(score2).toFixed(2));
        if (score1 < 95)
            $(this).find("td:eq(5)").css("color", "#FF1000");
        else
            $(this).find("td:eq(5)").css("color", "#479911");
        if (score2 < 85)
            $(this).find("td:eq(6)").css("color", "#FF1000");
        else
            $(this).find("td:eq(6)").css("color", "#479911");
        /*************************************************/
        var id = $(this).attr("id");
        $(this).find("input:eq(0)").click(function() {
            window.open("editinfo?id=" + id);
        });
        $(this).find("input:eq(1)").click(function() {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#CCC");
            $(this).attr("disabled", true);
            if (Ajax("delete", {id: mid}))
                showTips(tipsText2);
            setTimeout("location.reload()", 2000);
        });
        $(this).click(function() {
            $("#tab1 tbody tr:even").find("td:eq(0)").css("background-color", "#FFFFFF");
            $("#tab1 tbody tr:odd").find("td:eq(0)").css("background-color", "#FAFAFA");
            $(this).find("td:eq(0)").css("background-color", "#FFD58D");
        });
    });
    /********************************************************************/
    /** 上一页 */
    $(".pagebtn:eq(0)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) - 1;
        window.location.href = "marklist?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "marklist?name=" + name + "&page=" + page;
    });
    /********************************************************************/
    var page1 = $("#page1").text();
    var page2 = $("#page2").text();
    if (page1 <= 1) {
        $(".pagebtn:eq(0)").attr("disabled", true);
        $(".pagebtn:eq(0)").css("color", "#999");
    }
    if (page1 == page2) {
        $(".pagebtn:eq(1)").attr("disabled", true);
        $(".pagebtn:eq(1)").css("color", "#999");
    }
    /********************************************************************/
    function showTips(text) {
        $("#Tip").show().delay(1800).hide(200);
        $("#Tip").text(text);
    }
    /** 执行AJAX操作 */
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
