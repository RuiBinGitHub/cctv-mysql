$(document).ready(function() {
    // 获取当前语言
    var language = $(".title:eq(0) span").text().length == 4 ? "zh" : "en";
    var tipsTest1 = language == "zh" ? "请输入正确的项目范围！" : "Please check the input data!";
    var tipsTest2 = language == "zh" ? "数据保存成功！" : "Operating successfully!";
    var tipsTest3 = language == "zh" ? "请输入正确的管道坐标！" : "Please check the input data!";
    var tipsTest4 = language == "zh" ? "数据保存成功！" : "Operating successfully!";
    var tipsTest5 = language == "zh" ? "数据保存成功！" : "Operating successfully!";
    /********************************************************************/
    var id = $("input[name=id]").val();
    $(".title:eq(0) input").css("background-color", "#D13F43");
    $(".title:eq(1) input").css("background-color", "#2AB673");
    // 表头按钮点击事件
    $(".title:eq(0) input[type=button]").click(function() {
    	$("#scope input[type=text]:last").remove();
    });
    $(".title:eq(1) input[type=button]").click(function() {
        $("#file").click();
    });
    $("#file").change(function() {
        if (this.files.length == 0)
            return false;
        $("#form4").attr("action", "importvalue");
        $("#form4").submit();
    });
    /********************************************************************/
    initTextBox();
    /** 初始化项目范围输入框 */
    var place = "000000.000 000000.000";
    function initTextBox() {
        $("#scope input[type=text]").attr("name", "scope");
        $("#scope input[type=text]").attr("placeholder", place);
    }
    /**  */
    $("#scope").on("input", "input[type=text]", function() {
    	$(this).css("background-color", "#F3F3F3")
    });
    $("#scope").on("keypress", "input[type=text]", function(e) {
    	if (e.which >= 48 && e.which <= 57)
            return true;
    	if (e.which == 32 || e.which == 46)
    		return true;
        return false;
    });
    /** 添加项目范围输入框 */
    $("#append").click(function() {
        $(this).before("<input type='text'/>");
        initTextBox();
    });
    /** 项目范围输入 */
    $("#commit").click(function() {
        var length = $("#scope input[type=text]").length;
        if (length == 1 || length == 2) {
            showTips(tipsTest1);
            return false;
        }
        var result = true;
        $("#scope input[type=text]").each(function() {
        	var list = $(this).val().split(" ");
        	if (list ==null || list.length != 2) {
        		$(this).css("background-color", "#f00");
        		result = false;
        	}
        });
        if (result == false) {
        	showTips(tipsTest1);
			return false;
        }
        $(this).attr("disabled", true);
        $(this).css("background-color", "#ccc");
        if (Ajax("inputscope", $("#form1").serialize()))
            showTips(tipsTest2);
        setTimeout("location.reload()", 2000);
    });
    /********************************************************************/
    $("#tab1 tr input[type=button]").each(function(index) {
        $(this).click(function() {
            /** 显示坐标值 */
        	var row = $(this).parents("tr");
            $("#form2 input[type=hidden]:eq(0)").val(row.attr("id"));
            $(".lable:eq(0)").text("Start MH：" + row.find("td:eq(2)").text());
            $(".tab:eq(0) input[type=text]:eq(0)").val(row.find("td:eq(3)").text());
            $(".tab:eq(0) input[type=text]:eq(1)").val(row.find("td:eq(4)").text());
            $(".tab:eq(0) input[type=text]:eq(2)").val(row.find("td:eq(5)").text());
            $(".lable:eq(1)").text("Finish MH：" + row.find("td:eq(6)").text());
            $(".tab:eq(1) input[type=text]:eq(0)").val(row.find("td:eq(7)").text());
            $(".tab:eq(1) input[type=text]:eq(1)").val(row.find("td:eq(8)").text());
            $(".tab:eq(1) input[type=text]:eq(2)").val(row.find("td:eq(9)").text());

            $("#form3 input[type=hidden]").val(row.attr("id"));
            $("#form3 input[type=text]:eq(0)").val(row.find("td:eq(11)").text());
            $("#form3 input[type=text]:eq(1)").val(row.find("td:eq(12)").text());
            $("#form3 input[type=text]:eq(2)").val(row.find("td:eq(13)").text());
            $("#form3 input[type=text]:eq(3)").val(row.find("td:eq(14)").text());
            /** 设置样式 */
            $("#tab1 tr").find("td:eq(0)").text("");
            row.find("td:eq(0)").text("▶");
        });
    });
    $("#tab1 tr input[type=button]").eq(0).click();
    /********************************************************************/
    /** 设置管道坐标输入框只能输入数字 */
    $(".tab input[type=text]").on("input", function() {
        if ($(this).val() == "" || isNaN($(this).val()))
            $(this).css("background-color", "#FF0000");
        else
            $(this).css("background-color", "#F3F3F3");
    });
    $(".tab input[type=text]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });
    /********************************************************************/
    /** 提交数据 */
    $("#common").click(function() {
    	var result = true;
    	$(".tab input[type=text]").each(function() {
    		if ($(this).val() == "" || isNaN($(this).val())) {
    			$(this).css("background-color", "#f00");
        		result = false;
    		}
    	});
    	if (result == false) {
        	showTips(tipsTest3);
			return false;
        }
        $(this).attr("disabled", true);
        $(this).css("background-color", "#ccc");
        if (Ajax("inputvalue", $("#form2").serialize()))
            showTips(tipsTest4);
        setTimeout("location.reload()", 2000);
    });
    /********************************************************************/
    $("#linklist").click(function() {
        $("#page").show();
    });
    $("#bar span").click(function() {
        $("#page").hide();
    });
    $("#webform input[type=button]").click(function() {
    	$(this).attr("disabled", true);
        $(this).css("background-color", "#ccc");
        if (Ajax("updategrade", $("#form3").serialize()))
        	showTips(tipsTest8);
        setTimeout("location.reload()", 2000);
    });
    /********************************************************************/
    /** 显示提示信息 */
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
