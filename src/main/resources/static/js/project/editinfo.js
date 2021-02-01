layui.use(["layer", "laydate"], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;

    // 获取当前语言
    const language = $("#memu1 div").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "數據保存成功！" : "Operating successfully!";
    const tipsText2 = language === "zh" ? "確定要删除該數據嗎？" : "Are you sure you want to delete this data?";
    const tipsText3 = language === "zh" ? "删除數據成功！" : "Operating successfully!";
    const tipsText4 = language === "zh" ? "請檢查輸入數據！" : "Please check the input data!";
    const tipsText5 = language === "zh" ? "確定移除全部圖片？" : "Are you sure you want to remove all pictures?";
    const tipsText6 = language === "zh" ? "圖片移除成功！" : "Operating successfully!";
    const tipsText7 = language === "zh" ? "文件格式僅限於：PNG和JPG！" : "File format：PNG and JPG!";
    /** ******************************************************************** */
    let itemindex = -1;
    drawPipe();
    /** ******************************************************************** */
    const id1 = $("#id1").val();  // 管道ID
    const id2 = $("#id2").val();  // 项目ID
    /** ******************************************************************** */
    $(document).scroll(function () {  // 是否显示菜单
        if ($(document).scrollTop() >= 180)
            $("#TitleMemu").show();
        else
            $("#TitleMemu").hide();
    });
    /** 输入坐标按钮 */
    $("#mainTitle input:eq(0)").on("click", function () {
        window.open("/cctv/geominfo/findinfo?id=" + id2);
    });
    /** 下载项目按钮 */
    $("#mainTitle input:eq(1)").on("click", function () {
        window.open("/cctv/download?id=" + id2);
    });
    /** ******************************************************************** */
    /** 添加管道按钮 */
    $("#memu1 input:eq(0), #TitleMemu input:eq(0)").on("click", function () {
        $("#memu1 input:eq(0),#TitleMemu input:eq(0)").css("background-color", "#CCC");
        $("#memu1 input:eq(0),#TitleMemu input:eq(0)").attr("disabled", true);
        const value = $("#tab1 tbody tr:last").attr("id");
        const index = value === undefined ? 0 : value;
        const length = $("#tab1 tbody tr").length;
        if (Ajax("/cctv/pipe/insert", {id: id2, no: index}))
            window.location.href = "editinfo?id=" + id2 + "&no=" + length;
    });
    /** 保存数据按钮 */
    $("#memu1 input:eq(1), #TitleMemu input:eq(1)").on("click", function () {
        if (!checkPipe() || !checkItem())
            return false;
        $("#tab2 tr").each(function (i) {
            $(this).find("td:eq(0) input").attr("name", "items[" + i + "].id");
            $(this).find("td:eq(1) input").attr("name", "items[" + i + "].video");
            $(this).find("td:eq(2) input").attr("name", "items[" + i + "].photo");
            $(this).find("td:eq(3) input").attr("name", "items[" + i + "].dist");
            $(this).find("td:eq(4) input").attr("name", "items[" + i + "].cont");
            $(this).find("td:eq(5) input").attr("name", "items[" + i + "].code");
            $(this).find("td:eq(6) input").attr("name", "items[" + i + "].diam");
            $(this).find("td:eq(7) input").attr("name", "items[" + i + "].clockAt");
            $(this).find("td:eq(8) input").attr("name", "items[" + i + "].clockTo");
            $(this).find("td:eq(9) input").attr("name", "items[" + i + "].percent");
            $(this).find("td:eq(10) input").attr("name", "items[" + i + "].lengths");
            $(this).find("td:eq(11) input").attr("name", "items[" + i + "].remarks");
            $(this).find("td:eq(12) input").attr("name", "items[" + i + "].picture");
        });
        $("#memu1 input:eq(1), #TitleMemu input:eq(1)").css("background-color", "#ccc");
        $("#memu1 input:eq(1), #TitleMemu input:eq(1)").attr("disabled", true);
        if (Ajax("/cctv/pipe/update", $("#form1").serialize())) {
            const no = $("#value1 input[name=no]").val() - 1;
            window.location.href = "editinfo?id=" + id2 + "&no=" + no;
        } else
            setTimeout("location.reload()", 2000);
    });
    /** ******************************************************************** */
    /** 管道列表初始化事件 */
    const no = $("#no").val() === "" ? 0 : $("#no").val();
    const index = Math.min(no, $("#tab1 tbody tr").length - 1);
    $("#tab1 tbody tr").eq(index).find("td:eq(0)").css("background-color", "#FFD58D");
    $("#tab1 tbody tr").eq(index).find("td:eq(0)").text("▶");
    // 管理列表初始化事件
    $("#tab1 tbody tr").each(function (no) {
        const id = $(this).attr("id");
        $(this).find("input:eq(0)").click(function () {
            window.location.href = "editinfo?id=" + id2 + "&no=" + no;
        });
        $(this).find("input:eq(1)").click(function () {
            layer.confirm(tipsText2, {
                btn: ["确定", "取消"]
            }, function () {
                $(this).attr("disabled", true);
                $(this).css("background-color", "#CCC");
                if (Ajax("/cctv/pipe/delete", {id: id}))
                    layer.msg(tipsText3, {icon: 1});
                setTimeout("location.reload()", 2000);
            });
        });
    });
    /** 鼠标靠近事件 */
    $("#tab1 tbody tr").mouseenter(function () {
        $(this).find("input").show();
    });
    /** 鼠标远离事件 */
    $("#tab1 tbody tr").mouseleave(function () {
        $(this).find("input").hide();
    });
    /** 列表滾動至當前數據 */
    $("#showpipe").scrollTop(index * 24);
    /** ******************************************************************** */
    // 啟用日期控件
    $("input[name=date]").attr("readonly", true);
    laydate.render({
        elem: "input[name=date]",
        showBottom: false
    });
    $("input[name=time]").attr("placeholder", "HH:MM");
    /** ******************************************************************** */
    const inteReg = /^[+]{0,1}(\d+)$/; // 正整数正则表达式
    const timeReg = /^(([0-1][0-9])|2[0-3]):([0-5][0-9])$/; // 时间正则表达式
    $("#value1 input[name=reference]").css("background-color", "#EBEBE4");
    /** 數據第壹行第四行必須輸入 */
    $("#value1 input, #value4 input").on("input", function () {
        if ($(this).val() === "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // Pipe Length Reference
    $("#value1 input[name=reference]").on("input", function () {
        if ($(this).val() === "")
            $(this).css("background-color", "#FF0000");
        else
            $(this).css("background-color", "#EBEBE4");
    });
    // Survey ID和Year Laid只能輸入數字
    $("input[name=no], input[name=yearlaid]").on("input", function () {
        if ($(this).val() === "" || !inteReg.test($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=no], input[name=yearlaid]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57;
    });

    // 是否斜坡修改事件
    $("select[name=slope]").change(function () {
        if ($(this).val() === "N") {
            $("input[name=sloperef]").css("background-color", "#fff");
            $("input[name=sloperef]").val("N/A");
        } else {
            $("input[name=sloperef]").css("background-color", "#f00");
            $("input[name=sloperef]").val("");
        }
    });

    // 限制時間輸入格式
    $("input[name=time]").bind("input", function () {
        if ($(this).val().length === 2)
            $(this).val($(this).val() + ":");
        if (timeReg.test($(this).val()))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
    });

    // Start MH
    $("input[name=smanholeno]").attr("title", "*建議字符長度<10，長度過長可能會造成數據丟失！");
    $("input[name=smanholeno]").on("input", function () {
        $(this).val($(this).val().toUpperCase());
        const value = $(this).val();
        if (value == null || value === "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
        if ($("select[name=dire]").val() === "D" && value !== "") {
            $("input[name=reference]").css("background-color", "#EBEBE4");
            $("input[name=reference]").val(value + "X");
        }
        if ($("select[name=dire]").val() === "D" && value === "") {
            $("input[name=reference]").css("background-color", "#FF0000");
            $("input[name=reference]").val("");
        }
        $("#tab2 tbody tr").each(function () {
            const dist = $(this).find("td:eq(3) input").val();
            const code = $(this).find("td:eq(5) input").val();
            if (dist === "0.00" && code === "MH")
                $(this).find("td:eq(11) input").val(value);
        });
    });

    // Finish MH
    $("input[name=fmanholeno]").attr("title", "*建議字符長度<10，長度過長可能會造成數據丟失！");
    $("input[name=fmanholeno]").on("input", function () {
        $(this).val($(this).val().toUpperCase());
        const value = $(this).val();
        if (value == null || value === "")
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
        if ($("select[name=dire]").val() === "U" && value !== "") {
            $("input[name=reference]").css("background-color", "#EBEBE4");
            $("input[name=reference]").val(value + "X");
        }
        if ($("select[name=dire]").val() === "U" && value === "") {
            $("input[name=reference]").css("background-color", "#FF0000");
            $("input[name=reference]").val("");
        }
        const len = $("input[name=testlength]").val();
        $("#tab2 tbody tr").each(function () {
            const dist = $(this).find("td:eq(3) input").val();
            const code = $(this).find("td:eq(5) input").val();
            if (dist === len && code === "MH")
                $(this).find("td:eq(11) input").val(value);
        });
    });

    $("input[name=smanholeno], input[name=fmanholeno]").on("keydown", function (event) {
        if ($(this).val() !== "" && event.keyCode === 13) {
            const data1 = Ajax("https://www.map.gov.hk/arcgis2/rest/services/rGeoInfoMap/rgeo_highlight_d00/MapServer/19/query?f=json&returnExtentOnly=true&returnCountOnly=true&outFields=", {where: "FEAT_NUM='" + $(this).val() + "'"});
            if (data1 != null) {
                const data2 = Ajax("/cctv/manhole/findinfo", {x: data1.extent.xmin, y: data1.extent.ymin});
                if (data2 != null) {
                    $("select[name=district1]").val(data2.district1);
                    $("select[name=district2]").val(data2.district2);
                    $("select[name=district3]").val(data2.district3);
                }
            }
        }
    });

    // 流向改變事件
    $("select[name=dire]").change(function () {
        if ($(this).val() === "D")
            $("input[name=smanholeno]").trigger("input");
        if ($(this).val() === "U")
            $("input[name=fmanholeno]").trigger("input");
    });

    // Size(Dia)H
    $("input[name=hsize]").on("input", function () {
        if ($(this).val() === "" || isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // Size(Dia)W
    $("input[name=wsize]").on("input", function () {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=hsize], input[name=wsize]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });

    // Pipe Length和Total Length
    $("input[name=pipelength], input[name=testlength]").on("input", function () {
        if ($(this).val() === "" || isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    $("input[name=pipelength],input[name=testlength]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });

    // Invert Level=Cover Level-Start Depth
    $("input[name=sdepth], input[name=scoverlevel]").attr("title", "*不確定具體數值時請輸入：--");
    $("input[name=sdepth], input[name=scoverlevel]").on("input", function () {
        if ($(this).val() !== "" && (!isNaN($(this).val()) || $(this).val() === "--"))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
        const num1 = $("input[name=sdepth]").val();
        const num2 = $("input[name=scoverlevel]").val();
        if (!isNaN(num1) && !isNaN(num2)) {
            $("input[name=sinvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=sinvertlevel]").val((num2 - num1).toFixed(2));
        }
        if (num1 === "" || num2 === "" || num1 === "--" || num2 === "--") {
            $("input[name=sinvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=sinvertlevel]").val("--");
        }
    });

    // Invert Level=Cover Level-Start Depth
    $("input[name=fdepth], input[name=fcoverlevel]").attr("title", "*不確定具體數值時請輸入：--");
    $("input[name=fdepth], input[name=fcoverlevel]").on("input", function () {
        if ($(this).val() !== "" && (!isNaN($(this).val()) || $(this).val() === "--"))
            $(this).css("background-color", "#fff");
        else
            $(this).css("background-color", "#f00");
        const num1 = $("input[name=fdepth]").val();
        const num2 = $("input[name=fcoverlevel]").val();
        if (!isNaN(num1) && !isNaN(num2)) {
            $("input[name=finvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=finvertlevel]").val((num2 - num1).toFixed(2));
        }
        if (num1 === "" || num2 === "" || num1 === "--" || num2 === "--") {
            $("input[name=finvertlevel]").css("background-color", "#EBEBE4");
            $("input[name=finvertlevel]").val("--");
        }
    });
    $("input[name=sinvertlevel], input[name=finvertlevel]").attr("readonly", true);
    $("input[name=sinvertlevel], input[name=finvertlevel]").attr("tabIndex", -1);

    /** ******************************************************************** */
    function checkPipe() {
        let result = true;
        // 第一行
        let value = $("input[name=no]").val();
        if (value === "" || !inteReg.test(value)) {
            $("input[name=no]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=workorder]").val() === "") {
            $("input[name=workorder]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=reference]").val() === "") {
            $("input[name=reference]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=purposes]").val() === "") {
            $("input[name=purposes]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=sloperef]").val() === "") {
            $("input[name=sloperef]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=yearlaid]").val();
        if (value === "" || !inteReg.test(value)) {
            $("input[name=yearlaid]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=date]").val() === "") {
            $("input[name=date]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=time]").val();
        if (value === "" || !timeReg.test(value)) {
            $("input[name=time]").css("background-color", "#f00");
            result = false;
        }
        // 第三行
        if ($("input[name=smanholeno]").val() === "") {
            $("input[name=smanholeno]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=smanholeno]").val() === "") {
            $("input[name=fmanholeno]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=hsize]").val();
        if (value === "" || isNaN(value)) {
            $("input[name=hsize]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=pipelength]").val();
        if (value === "" || isNaN(value)) {
            $("input[name=pipelength]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=testlength]").val();
        if (value === "" || isNaN(value)) {
            $("input[name=testlength]").css("background-color", "#f00");
            result = false;
        }
        // 第四行
        value = $("input[name=sdepth]").val();
        if (value === "" || (isNaN(value) && value !== "--")) {
            $("input[name=sdepth]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=scoverlevel]").val();
        if (value === "" || (isNaN(value) && value !== "--")) {
            $("input[name=scoverlevel]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=fdepth]").val();
        if (value === "" || (isNaN(value) && value !== "--")) {
            $("input[name=fdepth]").css("background-color", "#f00");
            result = false;
        }
        value = $("input[name=fcoverlevel]").val();
        if (value === "" || (isNaN(value) && value !== "--")) {
            $("input[name=fcoverlevel]").css("background-color", "#f00");
            result = false;
        }

        if ($("input[name=category]").val() === "") {
            $("input[name=category]").css("background-color", "#f00");
            result = false;
        }
        if ($("input[name=videono]").val() === "") {
            $("input[name=videono]").css("background-color", "#f00");
            result = false;
        }
        if (!result)
            layer.msg(tipsText4, {icon: 3});
        return result;
    }

    /** ******************************************************************** */
    /** *********************** 管道数据输入检测完成 *********************** */
    /** ******************************************************************** */
    // 视频播放器点击事件
    $("#video").on("click", function () {
        $(this).focus();
    });
    // 视频播放器双击事件
    $("#video").on("dblclick", function () {
        $("#file1").click();
    });

    // 图片预览框双击事件
    $("#image1").on("dblclick", function () {
        $("#file2").click();
    });

    // 设置截频保存快捷键
    $(document, "#video").on("keydown", function (e) {
        if (e.altKey && e.which === 65) // 截屏
            keydownAlt_A();
        if (e.altKey && e.which === 83) // 保存
            keydownAlt_S();
    });

    // 截取视频
    function keydownAlt_A() {
        if ($("#video").attr("src") === undefined)
            return false;
        const video = $("#video")[0];
        const canvas1 = $("#canvas1")[0];
        const context = canvas1.getContext("2d");
        $(canvas1).attr("width", video.videoWidth);
        $(canvas1).attr("height", video.videoHeight);
        context.drawImage(video, 0, 0);
        $("#image1").attr("src", canvas1.toDataURL("image/png"));
    }

    // 保存图片
    function keydownAlt_S() {
        if (itemindex === -1 || $("#image1").attr("src") === undefined)
            return false;
        const canvas1 = $("#canvas1")[0];
        const imgData = canvas1.toDataURL("image/png");
        $("#image2").attr("src", $("#image1").attr("src"));
        $("#tab2 tr").eq(itemindex).find("td:eq(2) input").val("#待保存#");
        $("#tab2 tr").eq(itemindex).find("td:eq(12) input").val(imgData);
    }

    /** ****************************************************************************** */
    // 快捷键控制视频播放
    $(document, "#video").on("keydown", function (e) {
        const video = $("#video")[0];
        if (e.ctrlKey && e.which === 37)
            video.currentTime = video.currentTime - 0.03;
        if (e.ctrlKey && e.which === 39)
            video.currentTime = video.currentTime + 0.03;
    });
    /** ****************************************************************************** */
    /** 視頻文件選擇器 */
    $("#file1").change(function () {
        if (this.files.length === 0)
            return false;
        const url = getURL(this.files[0]);
        $("#video").attr("src", url);
        $("#video").attr("poster", "");
        const name = this.files[0].name;
        const text = name.substring(0, name.lastIndexOf("."));
        $("input[name=videono]").val(text);
        this.value = "";
    });

    /** 圖片文件選擇器 */
    $("#file2").change(function () {
        if (this.files.length === 0)
            return false;
        const url = getURL(this.files[0]);
        $("#image1").attr("src", url);
        const img = new Image();
        img.src = $("#image1").attr("src");
        img.onload = function () {
            const canvas1 = $("#canvas1")[0];
            $(canvas1).attr("width", img.width);
            $(canvas1).attr("height", img.height);
            const context = canvas1.getContext("2d");
            context.drawImage($("#image1")[0], 0, 0);
            this.value = "";
        };
    });

    /** 根據文件獲取路徑 */
    function getURL(file) {
        let url = null;
        if (window.createObjectURL !== undefined)
            url = window.createObjectURL(file);
        else if (window.URL !== undefined)
            url = window.URL.createObjectURL(file);
        else if (window.webkitURL !== undefined)
            url = window.webkitURL.createObjectURL(file);
        return url;
    }

    /** ******************************************************************** */
    // 添加记录
    $("#memu2 input[type=button]:eq(0)").on("click", function () {
        $("#tab2").append(getContext());
        const length = $("#tab2 tr").length - 1;
        initItemList(length);
    });
    // 插入记录
    $("#memu2 input[type=button]:eq(1)").on("click", function () {
        if (itemindex === -1)
            return false;
        $("#tab2 tr").eq(itemindex).before(getContext());
        initItemList(itemindex);
    });
    // 删除记录
    $("#memu2 input[type=button]:eq(2)").on("click", function () {
        if (itemindex === -1)
            return false;
        layer.confirm(tipsText2, {
            btn: ["确定", "取消"]
        }, function () {
            const id = $("#tab2 tr").eq(itemindex).find("[type=hidden]").val();
            if (id !== "" && id !== "0")
                Ajax("/cctv/item/remove", {id: id});
            $("#tab2 tr").eq(itemindex).remove();
            initItemList(itemindex);
            const list = $("#tab2 tbody tr").map(function () {
                return $(this).find("td:eq(3) input").val();
            }).get();
            const max = Math.max.apply(null, list);
            const length = max === "-Infinity" ? 0.0 : max;
            $("input[name=testlength]").val(length);
            layer.closeAll();
        });
    });

    // 导入图片
    $("#memu2 input[type=button]:eq(3)").on("click", function () {
        $("input[name=files]").click();
    });
    // 删除图片
    $("#memu2 input[type=button]:eq(4)").on("click", function () {
        layer.confirm(tipsText5, {
            btn: ["确定", "取消"]
        }, function () {
            if (Ajax("removeimage", {id: id2}))
                layer.msg(tipsText6, {icon: 1});
            setTimeout("location.reload()", 2000);
        });
    });
    $("input[name=files]").attr("webkitdirectory", true);
    $("input[name=files]").change(function () {
        if (this.files.length === 0)
            return false;
        for (let i = 0; i < this.files.length; i++) {
            const name = this.files[i].name;
            const loca = name.lastIndexOf(".");
            const type = name.substr(loca + 1).toUpperCase();
            if (type !== "PNG" && type !== "JPG") {
                layer.msg(tipsText7, {icon: 3});
                $(this).val("");
                return false;
            }
        }
        $("#form3").attr("action", "importimages");
        $("#form3").submit();
    });

    /***********************************************************************/
    /** 獲取Context */
    function getContext() {
        let context = "<tr>";
        context += "  <td><input type='hidden' value='0'/><a></a></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/><img/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='text'/></td>";
        context += "  <td><input type='hidden'/></td>";
        context += "</tr>";
        return context;
    }

    /**************************************************************************/
    const codelist1 = [];
    const codelist2 = [];
    const codelist3 = [];
    const codelist4 = [];
    const codelist5 = [];
    const codelist6 = [];
    const codelist7 = [];
    const codelist8 = [];
    const codelist9 = [];
    const codelist10 = [];
    const codelist11 = [];
    const codelist12 = [];
    const codelist13 = [];
    const codelist14 = [];
    const codelist15 = [];
    const codelist16 = [];
    const codelist17 = [];
    const codelist18 = [];
    $("#codes option").each(function () {
        if ($(this).data("value") === 1)
            codelist1.push($(this).val());
        if ($(this).data("value") === 2)
            codelist2.push($(this).val());
        if ($(this).data("value") === 3)
            codelist3.push($(this).val());
        if ($(this).data("value") === 4)
            codelist4.push($(this).val());
        if ($(this).data("value") === 5)
            codelist5.push($(this).val());
        if ($(this).data("value") === 12)
            codelist6.push($(this).val());
        if ($(this).data("value") === 14)
            codelist7.push($(this).val());
        if ($(this).data("value") === 23)
            codelist8.push($(this).val());
        if ($(this).data("value") === 25)
            codelist9.push($(this).val());
        if ($(this).data("value") === 35)
            codelist10.push($(this).val());
        if ($(this).data("value") === 123)
            codelist11.push($(this).val());
        if ($(this).data("value") === 125)
            codelist12.push($(this).val());
        if ($(this).data("value") === 134)
            codelist13.push($(this).val());
        if ($(this).data("value") === 145)
            codelist14.push($(this).val());
        if ($(this).data("value") === 235)
            codelist15.push($(this).val());
        if ($(this).data("value") === 1234)
            codelist16.push($(this).val());
        if ($(this).data("value") === 1235)
            codelist17.push($(this).val());
        if ($(this).data("value") === 12345)
            codelist18.push($(this).val());
    });

    const contlist = [];
    $("#conts option").each(function () {
        contlist.push($(this).val());
    });
    const codelist = [];
    $("#codes option").each(function () {
        codelist.push($(this).val());
    });
    // 行点击事件
    $("#tab2").on("click", "tr", function () {
        itemindex = $(this).parent().find("tr").index($(this));
        $("#tab2 tbody tr").find("td:eq(0) a").text("");
        $(this).find("td:eq(0) a").text("▶");
        $("#image2").attr("src", "/cctv/img/blank.png");
        const value = $(this).find("input:last").val();
        if (value !== "" && value.length < 40)
            $("#image2").attr("src", "/image/" + value + ".png");
        if (value !== "" && value.length > 40)
            $("#image2")[0].src = value;
    });
    // 单元格获取焦点事件
    $("#tab2").on("focus", "tr td, tr td input", function (event) {
        event.stopPropagation();
        $(this).parents("tr").click();
        $(this).select();
    });
    // 单元格键盘按下事件
    $("#tab2").on("keydown", "tr td", function (event) {
        const low = $(this).parents("tr").index();
        const row = $(this).index();
        if (event.keyCode === 9) {
            $(this).focus();
        } else if (event.keyCode === 37) {
            $(this).prev().focus();
        } else if (event.keyCode === 39) {
            $(this).next().focus();
        } else if (event.keyCode === 38 && low !== 0) {
            $("#tab2 tbody tr").eq(low - 1).find("td").eq(row).focus();
            return false;
        } else if (event.keyCode === 40) {
            if (low === $("#tab2 tbody tr").length - 1)
                $("#memu2 input[type=button]:eq(0)").click();
            $("#tab2 tbody tr").eq(low + 1).find("td").eq(row).focus();
            return false;
        } else
            $(this).find("input").focus();
        if (event.altKey && event.which === 65)
            keydownAlt_A();
        if (event.altKey && event.which === 83)
            keydownAlt_S();
    });
    // 输入框键盘按下事件
    $("#tab2").on("keydown", "tr td input", function (event) {
        event.stopPropagation();
        const low = $(this).parents("tr").index();
        const row = $(this).parents("td").index();
        if (event.keyCode === 9) {
            $(this).parent().focus();
        } else if (event.keyCode === 37 && $(this).val() === "") {
            $(this).parent().prev().focus();
        } else if (event.keyCode === 39 && $(this).val() === "") {
            $(this).parent().next().focus();
        } else if (event.keyCode === 38 && low > 0) {
            $("#tab2 tbody tr").eq(low - 1).find("td").eq(row).focus();
            return false;
        } else if (event.keyCode === 40 || event.keyCode === 13) {
            if (low === $("#tab2 tbody tr").length - 1)
                $("#memu2 input[type=button]:eq(0)").click();
            $("#tab2 tbody tr").eq(low + 1).find("td").eq(row).focus();
            return false;
        }
        if (event.altKey && event.which === 65)
            keydownAlt_A();
        if (event.altKey && event.which === 83)
            keydownAlt_S();
    });
    // 第三个单元格Photo No
    $("#tab2").on("mouseenter", "tr td:nth-child(3)", function () {
        const value = $(this).find("input").val();
        if (value === "" || value === "#已移除#") {
            $(this).find("img").attr("src", "/cctv/img/append.png");
            $(this).find("img").click(function () {
                $(this).prev().val("*插入图片")
            });
        } else if (value === "*插入图片") {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function () {
                $(this).prev().val("");
            });
        } else if (value === "#待保存#") {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function () {
                $(this).prev().val("");
                $(this).parents("tr").find("td:eq(12) input").val("");
            });
        } else {
            $(this).find("img").attr("src", "/cctv/img/remove.png");
            $(this).find("img").click(function () {
                $(this).prev().val("#已移除#");
                $(this).parents("tr").find("td:eq(12) input").val("");
            });
        }
        $(this).find("img").show();
    });
    $("#tab2").on("mouseleave", "tr td:nth-child(3)", function () {
        $(this).find("img").hide();
    });
    // 第四个单元格Distance
    $("#tab2").on("input", "tr td:nth-child(4) input", function () {
        if ($(this).val() === "" || isNaN($(this).val())) {
            $(this).css("background-color", "#f00");
            return false;
        }
        $(this).css("background-color", "#fff");
        const list = $("#tab2 tbody tr").map(function () {
            return $(this).find("td:eq(3) input").val();
        }).get();

        const max = Math.max.apply(null, list);
        const value = max === "-Infinity" ? 0.0 : max;
        $("input[name=testlength]").val(value);
        $("#tab2 tbody tr").each(function () {
            const color1 = $(this).find("td:eq(3) input").css("background-color");
            const color2 = $(this).find("td:eq(5) input").css("background-color");
            if (color1 != null && color1 === "rgb(255, 255, 0)")
                $(this).find("td:eq(3) input").css("background-color", "#fff");
            if (color2 != null && color2 === "rgb(255, 255, 0)")
                $(this).find("td:eq(5) input").css("background-color", "#fff");
        });
    });
    $("#tab2").on("blur", "tr td:nth-child(4) input", function () {
        if ($(this).val() != null && $(this).val() !== "") {
            const value = parseFloat($(this).val()).toFixed(2);
            $(this).val(value);
        }
    });
    // 第五个单元格Cont Def
    $("#tab2").on("input", "tr td:nth-child(5) input", function () {
        if ($(this).val() !== "" && contlist.indexOf($(this).val()) === -1)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第六个单元格Code
    const space = new RegExp(" ", "g");
    $("#tab2").on("input", "tr td:nth-child(6) input", function () {
        $(this).val($(this).val().toUpperCase());
        if ($(this).val() === "")
            $(this).attr("list", "codes");
        else {
            const value = $(this).val().replace(space, "\\ ");
            $("#icode").html($("#codes option[value^=" + value + "]").clone());
            $(this).attr("list", "icode");
        }
        $(this).parents("tr").find("td:eq(11) input").attr("list", null);
        $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFFFF");
        $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFFFF");
        if (codelist.indexOf($(this).val()) === -1)
            $(this).css("background-color", "#FF0000");
        else {
            $(this).css("background-color", "#FFFFFF");
            $(this).parents("tr").find("td:eq(11) input").attr("list", $(this).val());
            $("#tab2 tbody tr").each(function () {
                const color1 = $(this).find("td:eq(3) input").css("background-color");
                const color2 = $(this).find("td:eq(5) input").css("background-color");
                if (color1 != null && color1 === "rgb(255, 255, 0)")
                    $(this).find("td:eq(3) input").css("background-color", "#fff");
                if (color2 != null && color2 === "rgb(255, 255, 0)")
                    $(this).find("td:eq(5) input").css("background-color", "#fff");
            });

            if ($(this).val() === "MH") {
                const dist = Number($(this).parents("tr").find("td:eq(3) input").val());
                const test = Number($("input[name=testlength]").val());
                if (dist === test)
                    $(this).parents("tr").find("td:eq(11) input").val($("input[name=fmanholeno]").val());
            }

            // 设置颜色提醒
            if (codelist1.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
            } else if (codelist2.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist3.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            } else if (codelist4.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist5.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist6.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
            } else if (codelist7.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist8.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            } else if (codelist9.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist10.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist11.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
            } else if (codelist12.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist13.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist14.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist15.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist16.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
            } else if (codelist17.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            } else if (codelist18.indexOf($(this).val()) !== -1) {
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(9) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(10) input").css("background-color", "#FFFF00");
                $(this).parents("tr").find("td:eq(11) input").css("background-color", "#FFFF00");
            }
        }
        if ($("#tab2 tbody tr").eq(itemindex).find("td:eq(11) input").val() !== "")
            return false;
        const dist = $(this).parents("tr").find("td:eq(3) input").val();
        const length = $("input[name=testlength]").val();
        if ($(this).val() === "MH" && Number(dist) === 0.0) {
            const smh = $("input[name=smanholeno]").val();
            $(this).parents("tr").find("td:eq(11) input").val(smh);
        }
        if ($(this).val() === "MH" && Number(dist) === length) {
            const fmh = $("input[name=fmanholeno]").val();
            $(this).parents("tr").find("td:eq(11) input").val(fmh);
        }
    });
    // 第八个单元格
    $("#tab2").on("input", "tr td:nth-child(8) input", function () {
        const reg = /^(0[1-9]|1[0-2])$/;
        if ($(this).val() === "" || reg.test($(this).val())) {
            $(this).css("background-color", "#fff");
            const color = $(this).parents("tr").find("td:eq(8) input").css("background-color");
            if (color != null && color === "rgb(255, 255, 0)")
                $(this).parents("tr").find("td:eq(8) input").css("background-color", "#fff");
        } else
            $(this).css("background-color", "#f00");
    });
    // 第九个单元格
    $("#tab2").on("input", "tr td:nth-child(9) input", function () {
        const reg = /^(0[0-9]|1[0-2])(0[1-9]|1[0-2])$/;
        if ($(this).val() === "" || reg.test($(this).val())) {
            $(this).css("background-color", "#fff");
            const color = $(this).parents("tr").find("td:eq(7) input").css("background-color");
            if (color != null && color === "rgb(255, 255, 0)")
                $(this).parents("tr").find("td:eq(7) input").css("background-color", "#fff");
        } else
            $(this).css("background-color", "#f00");
    });
    // 第十个单元格
    $("#tab2").on("input", "tr td:nth-child(10) input", function () {
        if (isNaN($(this).val()) || $(this).val() > 100)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第十一个单元格
    $("#tab2").on("input", "tr td:nth-child(11) input", function () {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    // 第十二个单元格
    $("#tab2").on("input", "tr td:nth-child(12) input", function () {
        if ($(this).val() !== "")
            $(this).css("background-color", "#fff");
    });
    $("#tab2").on("focus click", "tr td:nth-child(12) input", function () {
        const value = $(this).parents("tr").find("td:eq(5) input").val();
        $(this).attr("list", value);
    });
    $("#tab2").on("keypress", "tr td:nth-child(4) input", function (event) {
        return (event.which >= 48 && event.which <= 57) || event.which === 46;
    });
    $("#tab2").on("keypress", "tr td:nth-child(8) input", function (event) {
        return event.which >= 48 && event.which <= 57;
    });
    $("#tab2").on("keypress", "tr td:nth-child(9) input", function (event) {
        return event.which >= 48 && event.which <= 57;
    });
    $("#tab2").on("keypress", "tr td:nth-child(10) input", function (event) {
        return event.which >= 48 && event.which <= 57;
    });
    $("#tab2").on("keypress", "tr td:nth-child(11) input", function (event) {
        return event.which >= 48 && event.which <= 57;
    });

    initItemList(0);

    function initItemList(index) {
        itemindex = -1;
        $("#tab2 tbody tr").each(function (i) {
            $(this).find("td:eq(2) input[type=text]").css("width", "75%");
            $(this).find("td:eq(2) input[type=text]").css("float", "left");
            $(this).find("td:eq(1) input[type=text]").attr("readonly", true);
            $(this).find("td:eq(2) input[type=text]").attr("readonly", true);
            $(this).find("td:eq(1) input[type=text]").attr("tabIndex", -1);
            $(this).find("td:eq(2) input[type=text]").attr("tabIndex", -1);
            $(this).find("td:eq(1) input[type=text]").css("cursor", "pointer");
            $(this).find("td:eq(2) input[type=text]").css("cursor", "pointer");
            $(this).find("td:eq(4) input[type=text]").attr("list", "conts");
            $(this).find("td:eq(5) input[type=text]").attr("list", "codes");
            $(this).find("td:eq(12) ").css("display", "none");
            /***************************************************************************/
            $(this).find("td").each(function (j) {
                $(this).attr("tabindex", i * 12 + j + 1);
            });
            let value = $(this).find("td:eq(3) input").val();
            if (value != null && value !== "") {
                value = parseFloat(value).toFixed(2);
                $(this).find("td:eq(3) input").val(value);
            }
        });
        if (index > $("#tab2 tbody tr").length - 1)
            index = $("#tab2 tbody tr").length - 1;
        $("#tab2 tbody tr").eq(index).click();
    }

    /**************************************************************************/
    if (sessionStorage.control === "隐藏") {
        $("#showItem").css("height", "765px");
        $("#ishow").text("＋");
        $("#media").hide();
    }
    $("#ishow").on("click", function () {
        if ($(this).text() === "－") {
            sessionStorage.control = "隐藏";
            $("#showItem").css("height", "765px");
            $("#ishow").text("＋");
            $("#media").hide();
        } else {
            sessionStorage.control = "展开";
            $("#showItem").css("height", "217px");
            $("#ishow").text("－");
            $("#media").show();
        }
    });

    function checkItem() {
        let result = true;
        const showText = language === "zh" ? "請輸入完整數據！" : "Please check the input data!";
        for (let i = 0; i < $("#tab2 tbody tr").length; i++) {
            const line = $("#tab2 tbody tr").eq(i);
            const dist = line.find("td:eq(3) input").val();
            const cont = line.find("td:eq(4) input").val();
            const code = line.find("td:eq(5) input").val();
            const clockAt = line.find("td:eq(7) input").val();
            const clockTo = line.find("td:eq(8) input").val();
            const intrusion1 = line.find("td:eq(9) input").val();
            const intrusion2 = line.find("td:eq(10) input").val();
            const remarks = line.find("td:eq(11) input").val();

            if (dist === "" || isNaN(dist)) {
                line.find("td:eq(3) input").css("background-color", "#f00");
                result = false;
            }
            if (cont !== "" && contlist.indexOf(cont) === -1) {
                line.find("td:eq(4) input").css("background-color", "#f00");
                result = false;
            }
            if (code === "" || codelist.indexOf(code) === -1) {
                line.find("td:eq(5) input").css("background-color", "#f00");
                result = false;
            }
            let reg = /^(0[1-9]|1[0-2])$/;
            if (clockAt !== "" && !reg.test(clockAt)) {
                line.find("td:eq(7) input").css("background-color", "#f00");
                result = false;
            }
            reg = /^(0[0-9]|1[0-2])(0[1-9]|1[0-2])$/;
            if (clockTo !== "" && !reg.test(clockTo)) {
                line.find("td:eq(8) input").css("background-color", "#f00");
                result = false;
            }
            if (intrusion1 !== "" && (isNaN(intrusion1) || intrusion1 < 0 || intrusion1 > 100)) {
                line.find("td:eq(9) input").css("background-color", "#f00");
                result = false;
            }
            if (intrusion2 !== "" && isNaN(intrusion2)) {
                line.find("td:eq(10) input").css("background-color", "#f00");
                result = false;
            }
            /***********************************************************************/
            if (codelist1.indexOf(code) !== -1 && clockAt === "") {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist2.indexOf(code) !== -1 && clockTo === "") {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist3.indexOf(code) !== -1 && intrusion1 === "") {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist4.indexOf(code) !== -1 && intrusion2 === "") {
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist5.indexOf(code) !== -1 && remarks === "") {
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist6.indexOf(code) !== -1 && (clockAt === "" || clockTo === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist7.indexOf(code) !== -1 && (clockAt === "" || intrusion2 === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist8.indexOf(code) !== -1 && (clockTo === "" || intrusion1 === "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist9.indexOf(code) !== -1 && (clockTo === "" || remarks === "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist10.indexOf(code) !== -1 && (intrusion1 === "" || remarks === "")) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist11.indexOf(code) !== -1 && ((clockAt === "" && clockTo === "") || intrusion1 === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist12.indexOf(code) !== -1 && ((clockAt === "" && clockTo === "") || remarks === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist13.indexOf(code) !== -1 && (clockAt === "" || intrusion1 === "" || intrusion2 === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist14.indexOf(code) !== -1 && (clockAt === "" || intrusion2 === "" || remarks === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist15.indexOf(code) !== -1 && (clockTo === "" || intrusion1 === "" || remarks === "")) {
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist16.indexOf(code) !== -1 && ((clockAt === "" && clockTo === "") || intrusion1 === "" || intrusion2 === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist17.indexOf(code) !== -1 && ((clockAt === "" && clockTo === "") || intrusion1 === "" || remarks === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            } else if (codelist18.indexOf(code) !== -1 && ((clockAt === "" && clockTo === "") || intrusion1 === "" || intrusion2 === "" || remarks === "")) {
                line.find("td:eq(7) input").css("background-color", "#FFFF00");
                line.find("td:eq(8) input").css("background-color", "#FFFF00");
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                line.find("td:eq(10) input").css("background-color", "#FFFF00");
                line.find("td:eq(11) input").css("background-color", "#FFFF00");
                result = false;
            }
        }
        if (result === false) {
            layer.msg(showText, {icon: 3});
            return false;
        }
        for (let i = 0; i < $("#tab2 tbody tr").length; i++) {
            const line = $("#tab2 tbody tr").eq(i);
            const code = line.find("td:eq(5) input").val();
            const intrusion = line.find("td:eq(9) input").val();
            if ((code === "EL" || code === "ELJ" || code === "ESL") && intrusion >= 5) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                layer.msg("Intrusion % < 5%", {icon: 3});
                return false;
            } else if ((code === "EM" || code === "EMJ" || code === "ESM") && (intrusion < 5 || intrusion > 20)) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                layer.msg("Intrusion %： 5%～20%", {icon: 3});
                return false;
            } else if ((code === "EH" || code === "EHJ" || code === "ESH") && intrusion < 20) {
                line.find("td:eq(9) input").css("background-color", "#FFFF00");
                layer.msg("Intrusion % > 20%", {icon: 3});
                return false;
            }
        }
        for (let i = 0; i < $("#tab2 tbody tr").length; i++) {
            const line = $("#tab2 tbody tr").eq(i);
            const dist = line.find("td:eq(3) input").val();
            const cont = line.find("td:eq(4) input").val();
            const code = line.find("td:eq(5) input").val();
            if (cont != null && cont !== "")
                continue;
            if (code !== "DEC" && code !== "DES")
                continue;
            for (let j = i + 1; j < $("#tab2 tbody tr").length; j++) {
                const temp = $("#tab2 tbody tr").eq(j);
                const idist = temp.find("td:eq(3) input").val();
                const icode = temp.find("td:eq(5) input").val();
                const length = idist - dist;
                if (code !== icode || length > 1)
                    continue;
                line.find("td:eq(3) input").css("background-color", "#ff0");
                line.find("td:eq(5) input").css("background-color", "#ff0");
                temp.find("td:eq(3) input").css("background-color", "#ff0");
                temp.find("td:eq(5) input").css("background-color", "#ff0");
                layer.msg("間隔需要大於1m", {icon: 3});
                return false;
            }
        }
        $("#tab2 tbody tr td:nth-child(5) input").each(function () {
            $(this).attr("value", $(this).val());
        });
        for (let i = 1; i <= 9; i++) {
            const text1 = "S" + i;
            const text2 = "F" + i;
            if ($("#tab2 tbody tr td:nth-child(5) input[value=" + text1 + "]").length > 1) {
                $("#tab2 tbody tr td:nth-child(5) input[value=" + text1 + "]").css("background-color", "#f00");
                layer.msg("不允許出現多個：" + text1, {icon: 3});
                return false;
            }
            if ($("#tab2 tbody tr td:nth-child(5) input[value=" + text2 + "]").length > 1) {
                $("#tab2 tbody tr td:nth-child(5) input[value=" + text2 + "]").css("background-color", "#f00");
                layer.msg("不允許出現多個：" + text2, {icon: 3});
                return false;
            }
        }
        return true;
    }

    /** ******************************************************************** */
    /** 設置最大值 */
    const list = $("#tab2 tbody tr").map(function () {
        return $(this).find("td:eq(3) input").val();
    }).get();
    const value = Math.max.apply(null, list);
    const length = value === "-Infinity" ? 0 : value;
    $("input[name=testlength]").val(length);

    /** ***************************************************************************** */
    function drawPipe() {
        const canvas = $("#showpipeimg")[0];
        const context = canvas.getContext("2d");
        context.font = "12px Courier New";
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();
        context.fillStyle = "#A1A1A1";
        context.strokeStyle = "#000000";
        context.rect(30, 100, 30, 640);
        context.fillRect(31, 101, 28, 638);
        context.fillStyle = "#A0A0A0";
        context.strokeStyle = "#606060";

        let tl = 0.0;
        const distlist = [];
        const joinlist = [];
        $("#tab2 tbody tr").each(function () {
            if (Number($(this).find("td:eq(3) input").val()) > tl)
                tl = $(this).find("td:eq(3) input").val();
            if ($(this).find("td:eq(5) input").val() === "MH")
                distlist.push($(this).find("td:eq(3) input").val());
            if ($(this).find("td:eq(5) input").val() === "JN")
                joinlist.push($(this).find("td:eq(3) input").val());
        });
        // var tl=Math.max.apply(null,distlist);
        tl = (tl === 0.0 ? 1 : tl);
        const use = $("#value3 select:eq(0)").val();
        for (let i = 0; i < distlist.length; i++) {
            if (use === "F") {
                const distance = i > 0 ? 100 : 40;
                const location = distlist[i] / tl * 640 + distance;
                context.fillRect(15, location, 60, 60);
            } else {
                const distance = i > 0 ? 130 : 70;
                const location = distlist[i] / tl * 640 + distance;
                context.moveTo(75, location);
                context.arc(45, location, 30, 0, Math.PI * 2);
                context.fill();
            }
        }
        for (let i = 0; i < joinlist.length; i++) {
            const location = joinlist[i] / tl * 640 + 90;
            context.fillRect(10, location, 19, 10);
        }

        function Note(dist, code) {
            this.dist = dist;
            this.code = code;
        }

        const list = [];
        $("#tab2 tbody tr").each(function () {
            const dist = $(this).find("input:eq(3)").val();
            const code = $(this).find("input:eq(5)").val();
            const note = new Note(dist, code);
            list.push(note);
        });

        let i = 0;
        let itemlength = 100;
        context.fillStyle = "#000000";
        while (i < list.length) {
            const distance = Math.round(list[i].dist / tl * 640 + 100);
            const location = distance - itemlength < 0 ? itemlength : distance;
            itemlength = location + 15;
            context.moveTo(30, distance);
            context.lineTo(60, distance);
            context.moveTo(60, distance);
            context.lineTo(110, location);
            context.lineTo(125, location);
            context.fillText(list[i].dist, 130, location + 4);
            context.fillText(list[i].code, 170, location + 4);
            i++;
        }
        context.stroke();
        context.closePath();
    }

    /** 返回頂部 */
    $("#toTop").on("click", function () {
        $("body, html").animate({
            scrollTop: 0
        }, 100);
    });

    /** ***************************************************************** */
    /** 執行AJAX操作 */
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

    /** ***************************************************************** */
    $("select[name=district1]").change(function () {
        let context = "";
        if ($(this).val() === "Kowloon") {
            context += "<option>Kowloon City District</option>";
            context += "<option>Kwun Tong District</option>";
            context += "<option>Sham Shui Po District</option>";
            context += "<option>Wong Tai Sin District</option>";
            context += "<option>Yau Tsim Mong District</option>";
        } else if ($(this).val() === "Hong Kong Island") {
            context += "<option>Central and Western District</option>";
            context += "<option>Eastern District</option>";
            context += "<option>Southern District</option>";
            context += "<option>Wan Chai District</option>";
        } else if ($(this).val() === "New Territories") {
            context += "<option>North District</option>";
            context += "<option>Tai Po District</option>";
            context += "<option>Islands District</option>";
            context += "<option>Kwai Tsing District</option>";
            context += "<option>Sai Kung District</option>";
            context += "<option>Sha Tin District</option>";
            context += "<option>Tsuen Wan District</option>";
            context += "<option>Tuen Mun District</option>";
            context += "<option>Yuen Long District</option>";
        }
        $("select[name=district2]").html(context);
        $("select[name=district2] option").each(function () {
            $(this).val($(this).text());
        });
        $("select[name=district2]").change();
    });
    $("select[name=district2]").change(function () {
        let context = "";
        if ($(this).val() === "North District") {
            context += "<option>Luen Wo Hui</option>";
            context += "<option>Fanling Town</option>";
            context += "<option>Cheung Wah</option>";
            context += "<option>Wah Do</option>";
            context += "<option>Wah Ming</option>";
            context += "<option>Yan Shing</option>";
            context += "<option>Fanling South</option>";
            context += "<option>Shing Fuk</option>";
            context += "<option>Ching Ho</option>";
            context += "<option>Yu Tai</option>";
            context += "<option>Sheung Shui Rural</option>";
            context += "<option>Choi Yuen</option>";
            context += "<option>Shek Wu Hui</option>";
            context += "<option>Tin Ping West</option>";
            context += "<option>Fung Tsui</option>";
            context += "<option>Sha Ta</option>";
            context += "<option>Tin Ping East</option>";
            context += "<option>Queen's Hill</option>";
        } else if ($(this).val() === "Tai Po District") {
            context += "<option>Tai po Hui</option>";
            context += "<option>Chung Ting</option>";
            context += "<option>Tai Po Central</option>";
            context += "<option>Tai Yuen</option>";
            context += "<option>Fu Heng</option>";
            context += "<option>Yee Fu</option>";
            context += "<option>Fu Ming Sun</option>";
            context += "<option>Kwong Fuk & Plover Cove</option>";
            context += "<option>Wang Fuk</option>";
            context += "<option>Tai Po Kau</option>";
            context += "<option>Wan Tan Tong</option>";
            context += "<option>San Fu</option>";
            context += "<option>Lam Tsuen Valley</option>";
            context += "<option>Po Nga</option>";
            context += "<option>Tai Wo</option>";
            context += "<option>Old Maket & Serenity</option>";
            context += "<option>Hong Lok Yuen </option>";
            context += "<option>Shuen Wan</option>";
            context += "<option>Sai Kung North</option>";
        } else if ($(this).val() === "Islands District") {
            context += "<option>Lantau</option>";
            context += "<option>Mun Yat</option>";
            context += "<option>Yat Tung Estate North</option>";
            context += "<option>Tung Chung South</option>";
            context += "<option>Tung Chung Central</option>";
            context += "<option>Tung Chung North</option>";
            context += "<option>Discovery Bay</option>";
            context += "<option>Peng Chau & Hei Ling Chau</option>";
            context += "<option>Lamma & Po Toi</option>";
            context += "<option>Cheung Chau</option>";
        } else if ($(this).val() === "Kwai Tsing District") {
            context += "<option>Kwai Hing</option>";
            context += "<option>Kwai Luen</option>";
            context += "<option>Kwai Shing East Estate</option>";
            context += "<option>Upper Tai Wo Hau</option>";
            context += "<option>Lower Tai Wo Hau</option>";
            context += "<option>Kwai Chung Estate South</option>";
            context += "<option>Kwai Chung Estate North</option>";
            context += "<option>Shek Yam</option>";
            context += "<option>Tai Pak Tin West</option>";
            context += "<option>Tai Pak Tin East</option>";
            context += "<option>On Yam</option>";
            context += "<option>Shek Lei North</option>";
            context += "<option>Shek Lei South</option>";
            context += "<option>Kwai Fong</option>";
            context += "<option>Hing Fong</option>";
            context += "<option>Wah Lai</option>";
            context += "<option>Lai Wah</option>";
            context += "<option>Cho Yiu</option>";
            context += "<option>Lai King</option>";
            context += "<option>Kwai Shing West Estate</option>";
            context += "<option>On Ho</option>";
            context += "<option>Wai Ying</option>";
            context += "<option>Tsing Yi Estate</option>";
            context += "<option>Greenfield</option>";
            context += "<option>Cheung Ching</option>";
            context += "<option>Cheung Hong</option>";
            context += "<option>Shing Hong</option>";
            context += "<option>Tsing Yi South</option>";
            context += "<option>Cheung Hang</option>";
            context += "<option>Ching Fat</option>";
            context += "<option>Cheung On</option>";
        } else if ($(this).val() === "Sai Kung District") {
            context += "<option>Sai Kung Central</option>";
            context += "<option>Pak Sha Wan</option>";
            context += "<option>Sai Kung Islands</option>";
            context += "<option>Hang Hau East</option>";
            context += "<option>Hang Hau West</option>";
            context += "<option>Choi Kin</option>";
            context += "<option>Kin Ming</option>";
            context += "<option>Do Shin</option>";
            context += "<option>Wai King</option>";
            context += "<option>Hoi Chun</option>";
            context += "<option>Po Yee</option>";
            context += "<option>Fu Kwan</option>";
            context += "<option>O Tong</option>";
            context += "<option>Sheung Tak</option>";
            context += "<option>Kwong Ming</option>";
            context += "<option>Hong King</option>";
            context += "<option>Tsui Lam</option>";
            context += "<option>Po Lam</option>";
            context += "<option>Yan Ying</option>";
            context += "<option>Wai Yan</option>";
            context += "<option>Wan Hang</option>";
            context += "<option>King Lam</option>";
            context += "<option>Hau Tak</option>";
            context += "<option>Fu Nam</option>";
            context += "<option>Tak Ming</option>";
            context += "<option>Nam On</option>";
            context += "<option>Kwan Po</option>";
            context += "<option>Wan Po North</option>";
            context += "<option>Wan Po South</option>";
        } else if ($(this).val() === "Sha Tin District") {
            context += "<option>Sha Tin Town Centre</option>";
            context += "<option>Lek Yuen</option>";
            context += "<option>Wo Che Estate</option>";
            context += "<option>City One</option>";
            context += "<option>Yue Shing</option>";
            context += "<option>Wong Uk</option>";
            context += "<option>Sha Kok</option>";
            context += "<option>Pok Hong</option>";
            context += "<option>Shui Chuen O</option>";
            context += "<option>Jat Chuen</option>";
            context += "<option>Chun Fung</option>";
            context += "<option>Sun Tin Wai</option>";
            context += "<option>Chui Tin</option>";
            context += "<option>Hin Ka</option>";
            context += "<option>Lower Shing Mun</option>";
            context += "<option>Wan Shing</option>";
            context += "<option>Keng Hau</option>";
            context += "<option>Tin Sum</option>";
            context += "<option>Chui Ka</option>";
            context += "<option>Tai Wai</option>";
            context += "<option>Chung Tin</option>";
            context += "<option>Sui Wo</option>";
            context += "<option>Fo Tan</option>";
            context += "<option>Chun Ma</option>";
            context += "<option>Hoi Nam</option>";
            context += "<option>Chung On</option>";
            context += "<option>Kam To</option>";
            context += "<option>Ma On Shan Town Centre</option>";
            context += "<option>Wu Kai Sha</option>";
            context += "<option>Lee On</option>";
            context += "<option>Fu Lung</option>";
            context += "<option>Kam Ying</option>";
            context += "<option>Yiu On</option>";
            context += "<option>Heng On</option>";
            context += "<option>Tai Shui Hang</option>";
            context += "<option>On Tai</option>";
            context += "<option>Yu Yan</option>";
            context += "<option>Di Yee</option>";
            context += "<option>Bik Woo</option>";
            context += "<option>Kwong Hong</option>";
            context += "<option>Kwong Yuen</option>";
        } else if ($(this).val() === "Tsuen Wan District") {
            context += "<option>Tak Wah</option>";
            context += "<option>Yeung Uk Road</option>";
            context += "<option>Tsuen Wan South</option>";
            context += "<option>Hoi Bun</option>";
            context += "<option>Tsuen Wan West</option>";
            context += "<option>Clague Garden</option>";
            context += "<option>Tsuen Wan Centre</option>";
            context += "<option>Discovery Park</option>";
            context += "<option>Fuk Loi</option>";
            context += "<option>Luk Yeung</option>";
            context += "<option>Ma Wan</option>";
            context += "<option>Tsuen Wan Rural</option>";
            context += "<option>Ting Sham</option>";
            context += "<option>Lai To</option>";
            context += "<option>Allway</option>";
            context += "<option>Cheung Shek</option>";
            context += "<option>Shek Wai Kok</option>";
            context += "<option>Lei Muk Shue West</option>";
            context += "<option>Lei Muk Shue East</option>";
        } else if ($(this).val() === "Tuen Mun District") {
            context += "<option>Tuen Mun Town Centre</option>";
            context += "<option>Siu Chi</option>";
            context += "<option>On Ting</option>";
            context += "<option>Siu Tsui</option>";
            context += "<option>Yau Oi South</option>";
            context += "<option>Yau Oi North</option>";
            context += "<option>Tsui Hing</option>";
            context += "<option>Shan King</option>";
            context += "<option>King Hing</option>";
            context += "<option>Hing Tsak</option>";
            context += "<option>San Hui</option>";
            context += "<option>So Kwun Wat</option>";
            context += "<option>Sam Shing</option>";
            context += "<option>Hanford</option>";
            context += "<option>Yuet Wu</option>";
            context += "<option>Siu Hei</option>";
            context += "<option>Wu King</option>";
            context += "<option>Butterfly</option>";
            context += "<option>Fu Sun</option>";
            context += "<option>Lok Tsui</option>";
            context += "<option>Lung Mun</option>";
            context += "<option>San King</option>";
            context += "<option>Leung King</option>";
            context += "<option>Tin King</option>";
            context += "<option>Po Tin</option>";
            context += "<option>Kin Sang</option>";
            context += "<option>Siu Hong</option>";
            context += "<option>Yan Tin</option>";
            context += "<option>Tuen Mun Rural</option>";
            context += "<option>Fu Tai</option>";
            context += "<option>Prime View</option>";
        } else if ($(this).val() === "Yuen Long District") {
            context += "<option>Fung Nin</option>";
            context += "<option>Yuen Long Centre</option>";
            context += "<option>Fung Cheung</option>";
            context += "<option>Yuen Lung</option>";
            context += "<option>Shap Pat Heung Central</option>";
            context += "<option>ShuiPin</option>";
            context += "<option>Nam Ping</option>";
            context += "<option>Pek Long</option>";
            context += "<option>Yuen Long Tung Tau</option>";
            context += "<option>Shap Pat Heung North</option>";
            context += "<option>Shap Pat Heung East</option>";
            context += "<option>Shap Pat Heung West</option>";
            context += "<option>Ping Shan South</option>";
            context += "<option>Hung Fuk</option>";
            context += "<option>Ha Tsuen</option>";
            context += "<option>Pilg Shan Central</option>";
            context += "<option>Shing Yan</option>";
            context += "<option>Tin Shing</option>";
            context += "<option>Tin Yau</option>";
            context += "<option>Yiu Yau</option>";
            context += "<option>Tsz Yau</option>";
            context += "<option>Kingswood South</option>";
            context += "<option>Shui Oi</option>";
            context += "<option>Shui Wah</option>";
            context += "<option>Chung Wah</option>";
            context += "<option>Chung Pak</option>";
            context += "<option>Kingswood North</option>";
            context += "<option>Yuet Yan</option>";
            context += "<option>Ching King</option>";
            context += "<option>Fu Yan</option>";
            context += "<option>Yat Chak</option>";
            context += "<option>Tin Heng</option>";
            context += "<option>Wang Yat</option>";
            context += "<option>Phg Shan North</option>";
            context += "<option>Fairview Park</option>";
            context += "<option>San Tin</option>";
            context += "<option>Kam Tin</option>";
            context += "<option>Pat Heung North</option>";
            context += "<option>Pat Heung South</option>";
        } else if ($(this).val() === "Kowloon City District") {
            context += "<option>Ma Tau Wai</option>";
            context += "<option>Sung Wong Toi</option>";
            context += "<option>Ma Hang Chung</option>";
            context += "<option>Ma Tau Kok</option>";
            context += "<option>Lok Man</option>";
            context += "<option>Sheung Lok</option>";
            context += "<option>Ho Man Tin</option>";
            context += "<option>Kadoorie</option>";
            context += "<option>Prince</option>";
            context += "<option>Kowloon Tong</option>";
            context += "<option>Lung Shing</option>";
            context += "<option>Kai Tak North</option>";
            context += "<option>Kai Tak East</option>";
            context += "<option>Kai Tak Central & South</option>";
            context += "<option>Hoi Sham</option>";
            context += "<option>To Kwa Wan North</option>";
            context += "<option>To Kwa Wan South</option>";
            context += "<option>Hok Yuen Laguna Verde</option>";
            context += "<option>Whampoa East</option>";
            context += "<option>Whampoa West</option>";
            context += "<option>Hung Hom Bay</option>";
            context += "<option>Hung Hom</option>";
            context += "<option>Ka Wai</option>";
            context += "<option>Oi Man</option>";
            context += "<option>Oi Chun</option>";
        } else if ($(this).val() === "Kwun Tong District") {
            context += "<option>Kwun Tong Central</option>";
            context += "<option>Kowloon Bay</option>";
            context += "<option>Kai Yip</option>";
            context += "<option>Lai Ching</option>";
            context += "<option>Ping Shek</option>";
            context += "<option>Choi Tak</option>";
            context += "<option>Jordan Valley</option>";
            context += "<option>Shun Tin</option>";
            context += "<option>Sheung Shun</option>";
            context += "<option>On Lee</option>";
            context += "<option>Kwun Tong On Tai</option>";
            context += "<option>Sau Mau Ping North</option>";
            context += "<option>Sau Mau Ping Central</option>";
            context += "<option>On Tat</option>";
            context += "<option>Sau Mau Ping South</option>";
            context += "<option>Po Tat</option>";
            context += "<option>Kwong Tak</option>";
            context += "<option>Hing Tin</option>";
            context += "<option>Lam Tin</option>";
            context += "<option>Ping Tin</option>";
            context += "<option>Pak Nga</option>";
            context += "<option>Chun Cheung</option>";
            context += "<option>Yau Tong East</option>";
            context += "<option>Yau Chui</option>";
            context += "<option>Yau Lai</option>";
            context += "<option>Yau Tong West</option>";
            context += "<option>Laguna City</option>";
            context += "<option>King Tin</option>";
            context += "<option>Tsui Ping</option>";
            context += "<option>Hiu Lai</option>";
            context += "<option>Po Lok</option>";
            context += "<option>Yuet Wah</option>";
            context += "<option>Hip Hong</option>";
            context += "<option>Lok Wah South</option>";
            context += "<option>Lok Wah North</option>";
            context += "<option>Hong Lok</option>";
            context += "<option>Ting On</option>";
            context += "<option>Upper Ngau Tau Kok Estate</option>";
            context += "<option>Lower Ngau Tau Kok Estate</option>";
            context += "<option>To Tai</option>";
        } else if ($(this).val() === "Sham Shui Po District") {
            context += "<option>Po Lai</option>";
            context += "<option>Cheung Sha Wan</option>";
            context += "<option>Nam Cheong North</option>";
            context += "<option>Shek Kip Mei</option>";
            context += "<option>Nam Cheong East</option>";
            context += "<option>Nam Cheong South</option>";
            context += "<option>Nam Cheong Central</option>";
            context += "<option>Nam Cheong West</option>";
            context += "<option>Fu Cheong</option>";
            context += "<option>Lai Kok</option>";
            context += "<option>Fortune</option>";
            context += "<option>Pik Wui</option>";
            context += "<option>Lai Chi Kok Central</option>";
            context += "<option>Lai Chi Kok South</option>";
            context += "<option>Mei Foo South</option>";
            context += "<option>Mei Foo Central</option>";
            context += "<option>Mei Foo North</option>";
            context += "<option>Lai Chi Kok North</option>";
            context += "<option>Un Chau</option>";
            context += "<option>So Uk</option>";
            context += "<option>Lei Cheng Uk</option>";
            context += "<option>Lung Ping & Sheung Pak Tin</option>";
            context += "<option>Ha Pak Tin</option>";
            context += "<option>Yau Yat Tsuen</option>";
            context += "<option>Nam Shan,Tai Hang Tung & Tai Hang Sai</option>";
        } else if ($(this).val() === "Wong Tai Sin District") {
            context += "<option>Lung Tsui</option>";
            context += "<option>Lung Ha</option>";
            context += "<option>Lung Sheung</option>";
            context += "<option>Fung Wong</option>";
            context += "<option>Fung Tak</option>";
            context += "<option>Lung Sing</option>";
            context += "<option>San Po Kong</option>";
            context += "<option>Tung Tau</option>";
            context += "<option>Tung Mei</option>";
            context += "<option>Lok Fu</option>";
            context += "<option>Wang Tau Hom</option>";
            context += "<option>Tin Keung</option>";
            context += "<option>Tsui Chuk & Pang Ching</option>";
            context += "<option>Chuk Yuen South</option>";
            context += "<option>Chuk Yuen North</option>";
            context += "<option>Tsz Wan West</option>";
            context += "<option>Ching Oi</option>";
            context += "<option>Ching On</option>";
            context += "<option>Tsz Wan East</option>";
            context += "<option>King Fu</option>";
            context += "<option>Choi Wan East</option>";
            context += "<option>Choi Wan South</option>";
            context += "<option>Choi Wan West</option>";
            context += "<option>Chi Choi</option>";
            context += "<option>Choi Hung</option>";
        } else if ($(this).val() === "Yau Tsim Mong District") {
            context += "<option>Tsim Sha Tsui West</option>";
            context += "<option>Kowloon Station</option>";
            context += "<option>Jordan West</option>";
            context += "<option>Yau Ma Tei South</option>";
            context += "<option>Charming</option>";
            context += "<option>Mong Kok West</option>";
            context += "<option>Fu Pak</option>";
            context += "<option>Olympic</option>";
            context += "<option>Cherry</option>";
            context += "<option>Tai Kok Tsui South</option>";
            context += "<option>Tai Kok Tsui North</option>";
            context += "<option>Tai Nan</option>";
            context += "<option>Mong Kok North</option>";
            context += "<option>Mong Kok East</option>";
            context += "<option>Mong Kok South</option>";
            context += "<option>Yau Ma Tei North</option>";
            context += "<option>East Tsim Sha Tsui & King's Park</option>";
            context += "<option>Jordan North</option>";
            context += "<option>Jordan South</option>";
            context += "<option>Tsim Sha Tsui Central</option>";
        } else if ($(this).val() === "Central and Western District") {
            context += "<option>Chung Wan</option>";
            context += "<option>Mid Levels East</option>";
            context += "<option>Castle Road</option>";
            context += "<option>Peak</option>";
            context += "<option>University</option>";
            context += "<option>Kwun Lung</option>";
            context += "<option>Kennedy Town & Mount Davis</option>";
            context += "<option>Sai Wan</option>";
            context += "<option>Belcher</option>";
            context += "<option>Shek Tong Tsui</option>";
            context += "<option>Sai Ying Pun</option>";
            context += "<option>Sheung Wan</option>";
            context += "<option>Tung Wah</option>";
            context += "<option>Centre Street</option>";
            context += "<option>Water Street</option>";
        } else if ($(this).val() === "Eastern District") {
            context += "<option>Tai Koo Shing West</option>";
            context += "<option>Tai Koo Shing East</option>";
            context += "<option>Lei King Wan</option>";
            context += "<option>Sai Wan Ho</option>";
            context += "<option>Adrich Bay</option>";
            context += "<option>Shaukeiwan</option>";
            context += "<option>A Kung Ngam</option>";
            context += "<option>Heng Fa Chuen</option>";
            context += "<option>Tsui Wan</option>";
            context += "<option>Yan Lam</option>";
            context += "<option>Siu Sai Wan</option>";
            context += "<option>King Yee</option>";
            context += "<option>Wan Tsui</option>";
            context += "<option>Fei Tsui</option>";
            context += "<option>Mount Parker</option>";
            context += "<option>Braemar Hill</option>";
            context += "<option>Fortress Hill</option>";
            context += "<option>Ctiy Garden</option>";
            context += "<option>Provident</option>";
            context += "<option>Fort Street</option>";
            context += "<option>Kam Ping</option>";
            context += "<option>Tanner</option>";
            context += "<option>Healthy Village</option>";
            context += "<option>Quarry Bay</option>";
            context += "<option>Nam Fung</option>";
            context += "<option>Kornhill</option>";
            context += "<option>Kornhill Garden</option>";
            context += "<option>Hing Tung</option>";
            context += "<option>Lower Yiu Tung</option>";
            context += "<option>Upper Yiu Tung</option>";
            context += "<option>Hing Man</option>";
            context += "<option>Lok Hong</option>";
            context += "<option>Tsui Tak</option>";
            context += "<option>Yue Wan</option>";
            context += "<option>Kai Hiu</option>";
        } else if ($(this).val() === "Southern District") {
            context += "<option>Aberdeen</option>";
            context += "<option>AP Lei Chau Estate</option>";
            context += "<option>AP Lei Chau North</option>";
            context += "<option>Lei Tung Ⅰ</option>";
            context += "<option>Lei Tung Ⅱ</option>";
            context += "<option>South Horizons East</option>";
            context += "<option>South Horizons West</option>";
            context += "<option>Wah Kwai</option>";
            context += "<option>Wah Fu South</option>";
            context += "<option>Wah Fu North</option>";
            context += "<option>Pokfulam</option>";
            context += "<option>Chi Fu</option>";
            context += "<option>Tin Wan</option>";
            context += "<option>Shek Yue</option>";
            context += "<option>Wong Chuk Hang</option>";
            context += "<option>Bays Area</option>";
            context += "<option>Stanley & Shek O</option>";
        } else if ($(this).val() === "Wan Chai District") {
            context += "<option>Hennessy</option>";
            context += "<option>Oi Kwan</option>";
            context += "<option>Canal Road</option>";
            context += "<option>Causeway Bay</option>";
            context += "<option>Victoria Park</option>";
            context += "<option>Tin Hau</option>";
            context += "<option>Tai Hang</option>";
            context += "<option>Jardine's Lookout</option>";
            context += "<option>Broadwood</option>";
            context += "<option>Happy Valley</option>";
            context += "<option>Stubbs Road</option>";
            context += "<option>Southorn</option>";
            context += "<option>Tai Fat Hau</option>";
        }
        $("select[name=district3]").html(context);
        $("select[name=district3] option").each(function () {
            $(this).val($(this).text());
        });
    });
    let context = "";
    context += "<option>Kowloon</option>";
    context += "<option>Hong Kong Island</option>";
    context += "<option>New Territories</option>";
    $("select[name=district1]").html(context);
    $("select[name=district1] option").each(function () {
        $(this).val($(this).text());
    });
    const dist1 = $("select[name=district1]").attr("id");
    $("select[name=district1]").val(dist1);
    $("select[name=district1]").change();
    const dist2 = $("select[name=district2]").attr("id");
    $("select[name=district2]").val(dist2);
    $("select[name=district2]").change();
    const dist3 = $("select[name=district3]").attr("id");
    $("select[name=district3]").val(dist3);

});
