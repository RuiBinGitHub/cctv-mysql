$(document).ready(function() {

    if ($("#menuText").val() == "") {
        $("#menuBtn1").attr("disabled", true);
        $("#menuBtn1").css("background-color", "#CCC");
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == "13")
            $("#menuBtn2").click();
    });
    /** 返回查询 */
    $("#menuBtn1").click(function() {
        window.location.href = "showlist";
    });
    /** 名称查询 */
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            window.location.href = "showlist?name=" + name;
    });
    /** 新增按钮点击事件 */
    $("#append").click(function() {
        window.open("insertview");
    });
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("name");
    	$(this).find("td:eq(1) a").attr("target", "_blank");
        var name = $("#menuText").val();
        if (name.trim() != "") {
            var text = $(this).find("td:eq(1) a").text();
            var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        var cont = $(this).find("td:eq(3)").text();
        var date = $(this).find("td:eq(4)").text();
        $(this).find("td:eq(5)").html(getDate(date, cont));
        /** *********************************************************** */
        $(this).find("input:eq(0)").click(function() {
            window.open("updateview?id=" + id);
        });
        $(this).find("input:eq(1)").click(function() {
            if (!confirm("確定要刪除該數據嗎?"))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("delete", {id: id}))
            	showTips("刪除數據成功！");
            setTimeout("location.reload()", 2000);
        });
        $(this).click(function() {
            $("#tab1 tbody tr:even").find("td:eq(0)").css("background-color", "#FAFAFA");
            $("#tab1 tbody tr:odd").find("td:eq(0)").css("background-color", "#EEEEEE");
            $(this).find("td:eq(0)").css("background-color", "#FFD58D");
        });
    });
    function getDate(idate, count) {
        var temp = new Date();
        var date = new Date(idate);
        date.setFullYear(date.getFullYear() + Number(count));
        var y = date.getFullYear();
        var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var text = y + "-" + m + "-" + d;
        if (parseInt((date - temp) / 1000 / 3600 / 24) > 30)
            return html = "<font color='#1A9D60'>" + text + "</font>";
        else if (parseInt((date - temp) / 1000 / 3600 / 24) < 0)
            return html = "<font color='#FF4400'>" + text + "</font>";
        else
            return html = "<font color='#F1AA00'>" + text + "</font>";
    }
    /** *************************************************************** */
    /** 上一页 */
    $(".pagebtn:eq(0)").click(function() {
        var value = $(".value").val();
        var page = Number($("#page1").text()) - 1;
        window.location.href = "showlist?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "showlist?name=" + name + "&page=" + page;
    });
    $(".pagebtn:eq(0)").attr("disabled", false);
    $(".pagebtn:eq(1)").attr("disabled", false);
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
    /** *************************************************************** */
    /** 显示提示信息 */
    function showTips(contex) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips").text(contex);
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
