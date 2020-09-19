$(document).ready(function() {

	var language = $("#infoTop").text().length == 4 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "確定要撤回該項目嗎？" : "Are you sure you want to revoke this data?";
    var tipsText2 = language == "zh" ? "數據撤回成功！" : "Operating successfully!";
    var tipsText3 = language == "zh" ? "確定要移除該數據嗎？" : "Are you sure you want to remove this data?";
    var tipsText4 = language == "zh" ? "數據移除成功！" : "Operating successfully!";
    /** *************************************************************** */
    var width = $("#infoMenu span:eq(0)").width();
    if ($("#infoMenu input[type=button]").length == 4)
        $("#infoMenu div:eq(0)").css("width", 535 - width);
    else
        $("#infoMenu div:eq(0)").css("width", 608 - width);
    /** *************************************************************** */
    if ($("#menuText").val() == "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
        window.location.href = "findlist";
    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val().trim();
        if (name.trim() != "")
            window.location.href = "findlist?name=" + name;
    });
    /** *************************************************************** */
    /** 项目合并 */
    $("#combin").click(function() {
        window.open("combineview");
    });
    /** 地图展示 */
    $("#commap").click(function() {
        window.open("/cctv/geominfo/showlist");
    });
    /** *************************************************************** */
    var name = $("input[name=name]").val();
    var sort = $("input[name=sort]").val();
    $("#tab1 thead th:eq(1)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=name1";
    	else
    		location.href = "findlist?name=" + name + "&sort=name1";
    });
    $("#tab1 thead th:eq(2)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=client1";
    	else
    		location.href = "findlist?name=" + name + "&sort=client1";
    });
    $("#tab1 thead th:eq(3)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=slope1";
    	else
    		location.href = "findlist?name=" + name + "&sort=slope1";
    });
    $("#tab1 thead th:eq(4)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=standard1";
    	else
    		location.href = "findlist?name=" + name + "&sort=standard1";
    });
    $("#tab1 thead th:eq(5)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=userid1";
    	else
    		location.href = "findlist?name=" + name + "&sort=userid1";
    });
    $("#tab1 thead th:eq(6)").click(function() {
    	if (name == null || name == "")
    		location.href = "findlist?sort=date1";
    	else
    		location.href = "findlist?name=" + name + "&sort=date1";
    });
    var sort = $("input[name=sort]").val();
    if (sort === "name") {
    	$("#tab1 thead th:eq(1)").text($("#tab1 thead th:eq(1)").text() + "↑");
    } else if (sort === "name1") {
    	$("#tab1 thead th:eq(1)").text($("#tab1 thead th:eq(1)").text() + "↓");
    	$("#tab1 thead th:eq(1)").unbind("click");
    	$("#tab1 thead th:eq(1)").click(function() {
    		if (name == null || name == "")
        		location.href = "findlist?sort=name";
        	else
        		location.href = "findlist?name=" + name + "&sort=name";
        });
    } else if (sort === "client") {
    	$("#tab1 thead th:eq(2)").text($("#tab1 thead th:eq(2)").text() + "↑");
    } else if (sort === "client1") {
    	$("#tab1 thead th:eq(2)").text($("#tab1 thead th:eq(2)").text() + "↓");
    	$("#tab1 thead th:eq(2)").unbind("click");
    	$("#tab1 thead th:eq(2)").click(function() {
        	if (name == null || name == "")
        		location.href = "findlist?sort=client";
        	else
        		location.href = "findlist?name=" + name + "&sort=client";
        });
    } else if (sort === "slope") {
    	$("#tab1 thead th:eq(3)").text($("#tab1 thead th:eq(3)").text() + "↑");
    } else if (sort === "slope1") {
    	$("#tab1 thead th:eq(3)").text($("#tab1 thead th:eq(3)").text() + "↓");
    	$("#tab1 thead th:eq(3)").unbind("click");
    	$("#tab1 thead th:eq(3)").click(function() {
    		if (name == null || name == "")
        		location.href = "findlist?sort=slope";
        	else
        		location.href = "findlist?name=" + name + "&sort=slope";
        });
    } else if (sort === "standard") {
    	$("#tab1 thead th:eq(4)").text($("#tab1 thead th:eq(4)").text() + "↑");
    } else if (sort === "standard1") {
    	$("#tab1 thead th:eq(4)").text($("#tab1 thead th:eq(4)").text() + "↓");
    	$("#tab1 thead th:eq(4)").unbind("click");
    	$("#tab1 thead th:eq(4)").click(function() {
    		if (name == null || name == "")
        		location.href = "findlist?sort=standard";
        	else
        		location.href = "findlist?name=" + name + "&sort=standard";
        });
    } else if (sort === "userid") {
    	$("#tab1 thead th:eq(5)").text($("#tab1 thead th:eq(5)").text() + "↑");
    } else if (sort === "userid1") {
    	$("#tab1 thead th:eq(5)").text($("#tab1 thead th:eq(5)").text() + "↓");
    	$("#tab1 thead th:eq(5)").unbind("click");
    	$("#tab1 thead th:eq(5)").click(function() {
    		if (name == null || name == "")
        		location.href = "findlist?sort=userid";
        	else
        		location.href = "findlist?name=" + name + "&sort=userid";
        });
    } else if (sort === "date") {
    	$("#tab1 thead th:eq(6)").text($("#tab1 thead th:eq(6)").text() + "↑");
    } else if (sort === "date1") {
    	$("#tab1 thead th:eq(6)").text($("#tab1 thead th:eq(6)").text() + "↓");
    	$("#tab1 thead th:eq(6)").unbind("click");
    	$("#tab1 thead th:eq(6)").click(function() {
    		if (name == null || name == "")
        		location.href = "findlist?sort=date";
        	else
        		location.href = "findlist?name=" + name + "&sort=date";
        });
    }
    /** *************************************************************** */
    /** 初始化表格 */
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("id");
        $(this).find("a").attr("target", "_blank");
        /** *********************************************************** */
        if (name.trim() != "") {
        	var text = $(this).find("td:eq(1) a").text();
        	var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        $(this).find("input[type=button]:eq(0)").click(function() {
            window.open("/cctv/download?id=" + id);
        });
        $(this).find("input[type=button]:eq(1)").click(function() {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("revoke", {id: id}))
                showTips(tipsText2);
            setTimeout("location.reload()", 2000);
        });
        $(this).find("input[type=button]:eq(2)").click(function() {
            if (!confirm(tipsText3))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("remove", {id: id}))
                showTips(tipsText4);
            setTimeout("location.reload()", 2000);
        });
        $(this).click(function() {
            $("#tab1 tbody tr:even").find("td:eq(0)").css("background-color", "#FAFAFA");
            $("#tab1 tbody tr:odd").find("td:eq(0)").css("background-color", "#EEEEEE");
            $(this).find("td:eq(0)").css("background-color", "#FFD58D");
        });
    });
    /** *************************************************************** */
    /** 上一页 */
    $(".pagebtn:eq(0)").click(function() {
        var page = Number($("#page1").text()) - 1;
        if (sort == null || sort == "")
        	window.location.href = "findlist?name=" + name + "&page=" + page;
        else
        	window.location.href = "findlist?name=" + name + "&sort=" + sort + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var page = Number($("#page1").text()) + 1;
        if (sort == null || sort == "")
        	window.location.href = "findlist?name=" + name + "&page=" + page;
        else
        	window.location.href = "findlist?name=" + name + "&sort=" + sort + "&page=" + page;
    });
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
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips").text(text);
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
