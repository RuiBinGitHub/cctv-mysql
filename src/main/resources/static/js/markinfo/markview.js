$(document).ready(function() {

	var language = $("#infoTop").text().length == 4 ? "zh" : "en";
    var btnText = language == "zh" ? "編輯" : "Edit";
    /********************************************************************/
    if ($("#menuText").val() == "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
        window.location.href = "markview";
    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            window.location.href = "markview?name=" + name;
    });
    /********************************************************************/
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("id");
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
        $(this).find("td:eq(6)").mouseenter(function(e) {
            $(this).css("background-color", "#FFE793");
            if ($(this).text() == 0)
                return false;
            var context = "";
            var data = Ajax("showlist", {id: id});
            for (var i = 0; i < data.length; i++) {
                context += "<tr>";
                context += "  <td>" + Number(data[i].score1).toFixed(2) + "</td>";
                context += "  <td>" + Number(data[i].score2).toFixed(2) + "</td>";
                context += "  <td>" + data[i].date.substring(0, 10) + "</td>";
                context += "  <td><input type='button' name='" + data[i].id + "'/></td>";
                context += "</tr>";
            }
            $("#show table tbody").html(context);
            $("#show").css("top", $(this).position().top);
            $("#show").css("left", $(this).position().left + 96);
            $("#show table tbody input").attr("value", btnText);
            $("#show").show();
        });
        $(this).find("td:eq(6)").mouseleave(function(e) {
            if (i % 2 == 0)
                $(this).css("background-color", "#FAFAFA");
            else
                $(this).css("background-color", "#EEEEEE");
            $("#show").hide();
        });
        /*************************************************/
        $(this).find("input:eq(0)").click(function() {
            window.open("/cctv/downfile?id=" + id);
        });
        $(this).find("input:eq(1)").click(function() {
            window.open("insert?id=" + id);
        });
        $(this).click(function() {
            $("#tab1 tbody tr:even").find("td:eq(0)").css("background-color", "#FAFAFA");
            $("#tab1 tbody tr:odd").find("td:eq(0)").css("background-color", "#EEEEEE");
            $(this).find("td:eq(0)").css("background-color", "#FFD58D");
        });
    });
    $("#show").mouseenter(function(e) {
        $(this).show();
    });
    $("#show").mouseleave(function(e) {
        $(this).hide();
    });
    $("#show table tbody").on("click", "input", function() {
    	window.open("editinfo?id=" + $(this).attr("name"));
    })
    /********************************************************************/
    /** 上一页 */
    $(".pagebtn:eq(0)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) - 1;
        window.location.href = "markview?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "markview?name=" + name + "&page=" + page;
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
    /*******************************************************************/
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
