layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    initList();
    drawPipe();
    const language = $("#title").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "請輸入正確的分數！" : "Please check the input score!";
    const tipsText2 = language === "zh" ? "保存數據成功！" : "Operating successfully!";
    /** *************************************************************** */
    const id = $("#id").val();
    const no = $("#no").val() === "" ? 0 : $("#no").val();
    const index = Math.min(no, $("#tab1 tbody tr").length - 1);
    $("#tab1 tbody tr").eq(index).find("td:eq(0)").css("background-color", "#FFD58D");
    $("#tab1 tbody tr").eq(index).find("td:eq(0)").text("▶");
    /** *************************************************************** */
    $(document).scroll(function () {
        if ($(document).scrollTop() >= 200)
            $("#TitleMemu").show();
        else
            $("#TitleMemu").hide();
    });
    /**  确定评分*/
    $("#TitleMemu input, #mainTitle input").on("click", function () {
        const value1 = $("input[name=score1]").val();
        if (value1 === "" || isNaN(value1) || value1 < 0 || value1 > 100) {
            $("input[name=score1]").css("background-color", "#f00");
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        const value2 = $("input[name=score2]").val();
        if (value2 === "" || isNaN(value2) || value2 < 0 || value2 > 100) {
            $("input[name=score2]").css("background-color", "#f00");
            layer.msg(tipsText1, {icon: 2});
            return false;
        }
        $("#TitleMemu input, #mainTitle input").css("background-color", "#CCC");
        $("#TitleMemu input, #mainTitle input").attr("disabled", true);
        if (Ajax("commit", $("#form1").serialize()))
            layer.msg(tipsText2, {icon: 1});
        setTimeout("location.reload()", 2000);
    });
    /********************************************************************/
    $("#tab1 tbody tr").each(function (i) {
        const score1 = $(this).find("td:eq(4)").text();
        const score2 = $(this).find("td:eq(5)").text();
        $(this).find("td:eq(4)").text(Number(score1).toFixed(2));
        $(this).find("td:eq(5)").text(Number(score2).toFixed(2));
        if (score1 !== "" && score1 < 95)
            $(this).find("td:eq(4)").css("color", "#FF1000");
        else
            $(this).find("td:eq(4)").css("color", "#479911");
        if (score2 !== "" && score2 < 85)
            $(this).find("td:eq(5)").css("color", "#FF1000");
        else
            $(this).find("td:eq(5)").css("color", "#479911");

        $(this).mouseenter(function () {
            $(this).find("input").show();
        });
        $(this).mouseleave(function () {
            $(this).find("input").hide();
        });
        $(this).find("input").click(function () {
            window.location.href = "editinfo?id=" + id + "&no=" + i;
        });
    });
    $("#tab1 tbody tr .stars").each(function () {
        $(this).html(getHTML($(this).data("level")));
    });

    function getHTML(level) {
        let hrml = "";
        for (let i = 0; i < level; i++)
            hrml += "<img src='/cctv/img/星星2.png' alt=''/>";
        for (let i = level; i < 5; i++)
            hrml += "<img src='/cctv/img/星星1.png' alt=''/>";
        return hrml;
    }

    $("#showpipe").scrollTop(30 * index);
    $("#main2 input").attr("readonly", true);
    $("#showstar1").html(getHTML($("#showstar1").data("level")));
    $("#showstar2").html(getHTML($("#showstar2").data("level")));
    let value1 = $("input[name=level1]").val() - 1;
    let value2 = $("input[name=level2]").val() - 1;

    /** 鼠标靠近事件 */
    $("#showstar1").on("mouseenter", "img", function () {
        const value = $(this).index();
        $("#showstar1 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar1 img:gt(" + value + ")").attr("src", "/cctv/img/星星1.png");
    });
    /** 鼠标远离事件 */
    $("#showstar1").on("mouseleave", "img", function () {
        $("#showstar1 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar1 img:gt(" + value1 + ")").attr("src", "/cctv/img/星星1.png");
    });
    /** 鼠标点击事件 */
    $("#showstar1").on("click", "img", function () {
        const value = $(this).index();
        $("input[name=level1]").val(value + 1);
        $("#showstar1 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar1 img:gt(" + value + ")").attr("src", "/cctv/img/星星1.png");
        value1 = value;
    });

    /** 鼠标靠近事件 */
    $("#showstar2").on("mouseenter", "img", function () {
        const value = $(this).index();
        $("#showstar2 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar2 img:gt(" + value + ")").attr("src", "/cctv/img/星星1.png");
    });
    /** 鼠标远离事件 */
    $("#showstar2").on("mouseleave", "img", function () {
        $("#showstar2 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar2 img:gt(" + value2 + ")").attr("src", "/cctv/img/星星1.png");
    });
    /** 鼠标点击事件 */
    $("#showstar2").on("click", "img", function () {
        const value = $(this).index();
        $("input[name=level2]").val(value + 1);
        $("#showstar2 img").attr("src", "/cctv/img/星星2.png");
        $("#showstar2 img:gt(" + value + ")").attr("src", "/cctv/img/星星1.png");
        value2 = value;
    });

    /********************************************************************/
    $("#tab2 tbody tr td:nth-child(1)").css("font-size", "14px");
    $("#tab2 tbody tr").each(function () {
        const text = $(this).find("td:eq(11)").text();
        $(this).find("td:eq(11)").attr("title", text);
        // 设置Code显示
        setCode($(this).find("td:eq(5)"), $(this).find("td:eq(5)").text());
        // 设置点击事件
        $(this).on("click", function () {
            $("#image").attr("src", "/cctv/img/blank-plus.png");
            const value = $(this).data("path");
            if (value != null && value.length > 0)
                $("#image").attr("src", "/image/" + value + ".png");
            else
                $("#image").attr("src", "/cctv/img/blank-plus.png");
            $("#tab2 tbody tr").find("td:eq(0)").text("");
            $(this).find("td:eq(0)").text("▶");
        });
    });
    $("#tab2 tbody tr:eq(0)").click();
    /********************************************************************/
    $("#video").on("click", function () {
        // TODO
    });
    $("#video").on("dblclick", function () {
        $("#file").click();
    });
    /** 视频文件选择器 */
    $("#file").change(function () {
        if (this.files === 0)
            return false;
        const url = getURL(this.files[0]);
        $("#video").attr("poster", "");
        $("#video").attr("src", url);
        this.value = "";
    });

    /** 根据文件获取路径 */
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

    /********************************************************************/
    $("#bvalue input[type=text]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });
    $("#bvalue input[type=text]").on("input", function () {
        const value = $(this).val();
        if (value === "" || isNaN(value) || value < 0 || value > 100)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });

    /********************************************************************/
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
        let distlist = [];
        let joinlist = [];
        $("#tab2 tbody tr").each(function () {
            if (Number($(this).find("td:eq(3)").text()) > tl)
                tl = $(this).find("td:eq(3)").text();
            if ($(this).find("td:eq(5)").text() === "MH")
                distlist.push($(this).find("td:eq(3)").text());
            if ($(this).find("td:eq(5)").text() === "JN")
                joinlist.push($(this).find("td:eq(3)").text());
        });
        tl = tl <= 0.0 ? 1 : tl;
        let use = $("#value3 input:eq(2)").val();
        for (let i = 0; i < distlist.length; i++) {
            if (use === "Foul") {
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
            if ($(this).find("td:eq(3)").text().length !== 0) {
                const dist = $(this).find("td:eq(3)").text();
                const code = $(this).find("td:eq(5)").text();
                const note = new Note(dist, code);
                list.push(note);
            }
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

    /** ***************************************************************** */
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

    /** 格式化显示信息 */
    function initList() {
        let value = $("#value3 input:eq(2)").val();
        if (value === "F")
            $("#value3 input:eq(2)").val("Foul");
        else if (value === "S")
            $("#value3 input:eq(2)").val("Surface water");
        else if (value === "C")
            $("#value3 input:eq(2)").val("Combined");
        else if (value === "T")
            $("#value3 input:eq(2)").val("Trade effluent");
        else if (value === "W")
            $("#value3 input:eq(2)").val("Watercourse");
        else if (value === "O")
            $("#value3 input:eq(2)").val("Others");
        else if (value === "U")
            $("#value3 input:eq(2)").val("Unknown");
        /***********************************************************/
        value = $("#value3 input:eq(3)").val();
        if (value === "U")
            $("#value3 input:eq(3)").val("Upstream");
        else if (value === "D")
            $("#value3 input:eq(3)").val("Downstream");
        /***********************************************************/
        value = $("#value3 input:eq(6)").val();
        if (value === "A")
            $("#value3 input:eq(6)").val("Arched with Flat Bottom");
        else if (value === "B")
            $("#value3 input:eq(6)").val("Barrel e.g. Beer Barrel Shape");
        else if (value === "C")
            $("#value3 input:eq(6)").val("Circular");
        else if (value === "E")
            $("#value3 input:eq(6)").val("Egg Shape");
        else if (value === "H")
            $("#value3 input:eq(6)").val("Horseshoe");
        else if (value === "O")
            $("#value3 input:eq(6)").val("Oval");
        else if (value === "K")
            $("#value3 input:eq(6)").val("Kerb Block");
        else if (value === "R")
            $("#value3 input:eq(6)").val("Rectangular");
        else if (value === "S")
            $("#value3 input:eq(6)").val("Square");
        else if (value === "T")
            $("#value3 input:eq(6)").val("Trapezoidal");
        else if (value === "U")
            $("#value3 input:eq(6)").val("U-shaped with Flat Top");
        else if (value === "Z")
            $("#value3 input:eq(6)").val("Other");
        /***********************************************************/
        value = $("#value3 input:eq(7)").val();
        if (value === "AC")
            $("#value3 input:eq(7)").val("Asbestos Cement");
        else if (value === "BL")
            $("#value3 input:eq(7)").val("Bitumen Lining");
        else if (value === "BR")
            $("#value3 input:eq(7)").val("Brick");
        else if (value === "CI")
            $("#value3 input:eq(7)").val("Cast Iron");
        else if (value === "CL")
            $("#value3 input:eq(7)").val("Cement Mortar Lining");
        else if (value === "CO")
            $("#value3 input:eq(7)").val("Concrete");
        else if (value === "CS")
            $("#value3 input:eq(7)").val("Concrete Segments");
        else if (value === "DI")
            $("#value3 input:eq(7)").val("Ductile Iron");
        else if (value === "EP")
            $("#value3 input:eq(7)").val("Epoxy");
        else if (value === "FC")
            $("#value3 input:eq(7)").val("Fibre Cement");
        else if (value === "FRP")
            $("#value3 input:eq(7)").val("Fibre Reinforced Plastics");
        else if (value === "GI")
            $("#value3 input:eq(7)").val("Galvanised Iron");
        else if (value === "MAC")
            $("#value3 input:eq(7)").val("Masonry in Regular Courses");
        else if (value === "MAR")
            $("#value3 input:eq(7)").val("Masonry in Randomly Coursed");
        else if (value === "PVC")
            $("#value3 input:eq(7)").val("Polyvinyl Chloride");
        else if (value === "PE")
            $("#value3 input:eq(7)").val("Polyethylene");
        else if (value === "PF")
            $("#value3 input:eq(7)").val("Pitch Fibre");
        else if (value === "PP")
            $("#value3 input:eq(7)").val("Polypropylene");
        else if (value === "PS")
            $("#value3 input:eq(7)").val("Polyester");
        else if (value === "RC")
            $("#value3 input:eq(7)").val("Reinforced Concrete");
        else if (value === "SPC")
            $("#value3 input:eq(7)").val("Sprayed Concrete");
        else if (value === "ST")
            $("#value3 input:eq(7)").val("Steel");
        else if (value === "VC")
            $("#value3 input:eq(7)").val("Vitrified Clay");
        else if (value === "X")
            $("#value3 input:eq(7)").val("Unidentified Material");
        else if (value === "XI")
            $("#value3 input:eq(7)").val("Unidentified Type of Iron or Steel");
        else if (value === "XP")
            $("#value3 input:eq(7)").val("Unidentified Type of Plastics");
        else if (value === "Z")
            $("#value3 input:eq(7)").val("Other");
        else if (value === "")
            $("#value3 input:eq(7)").val("--");
        /***********************************************************/
        value = $("#value3 input:eq(8)").val();
        if (value === "CF")
            $("#value3 input:eq(8)").val("Close Fit Lining");
        else if (value === "CIP")
            $("#value3 input:eq(8)").val("Cured In Place Lining");
        else if (value === "CP")
            $("#value3 input:eq(8)").val("Lining With Continuous Pipes");
        else if (value === "DP")
            $("#value3 input:eq(8)").val("Lining With Discrete Pipes");
        else if (value === "M")
            $("#value3 input:eq(8)").val("Lining Inserted During Manufacture");
        else if (value === "N")
            $("#value3 input:eq(8)").val("No Lining");
        else if (value === "SEG")
            $("#value3 input:eq(8)").val("Segmental Linings");
        else if (value === "SP")
            $("#value3 input:eq(8)").val("Sprayed Lining");
        else if (value === "SW")
            $("#value3 input:eq(8)").val("Spirally Wound Lining");
        else if (value === "Z")
            $("#value3 input:eq(8)").val("Other");
        else if (value === "")
            $("#value3 input:eq(8)").val("--");
        /***********************************************************/
        value = $("#value4 input:eq(7)").val();
        if (value === "")
            $("#value4 input:eq(7)").val("--");
        else if (value === "Y")
            $("#value4 input:eq(7)").val("Y");
        else if (value === "N")
            $("#value4 input:eq(7)").val("N");
        /***********************************************************/
        value = $("#value4 input:eq(8)").val();
        if (value === "")
            $("#value4 input:eq(8)").val("--");
        else if (value === "1")
            $("#value4 input:eq(8)").val("Dry");
        else if (value === "2")
            $("#value4 input:eq(8)").val("Heavy Rain");
        else if (value === "3")
            $("#value4 input:eq(8)").val("Light Rain");
        else if (value === "4")
            $("#value4 input:eq(8)").val("Showers");
    }

    function setCode(obj, code) {
        if (code === "DE E-1") {
            obj.attr("title", "Encrustation Medium, >20% cross section area loss");
            obj.text("DE E");
        } else if (code === "DE E-2") {
            obj.attr("title", "Encrustation Light, 5%-20% cross section area loss");
            obj.text("DE E");
        } else if (code === "DE E-3") {
            obj.attr("title", "Encrustation Heavy, <5% cross section area loss");
            obj.text("DE E");
        } else if (code === "H-1") {
            obj.attr("title", "Hole Above");
            obj.text("H");
        } else if (code === "H-2") {
            obj.attr("title", "Hole Below");
            obj.text("H");
        } else if (code === "MM-1") {
            obj.attr("title", "Mortar loss Large (>50mm)");
            obj.text("MM");
        } else if (code === "MM-2") {
            obj.attr("title", "Mortar loss Medium (15-50mm)");
            obj.text("MM");
        } else if (code === "MM-3") {
            obj.attr("title", "Mortar loss Slight (<15mm)");
            obj.text("MM");
        } else if (code === "SS-1") {
            obj.attr("title", "Surface Damage - Spalling Large (Deep voids)");
            obj.text("SS");
        } else if (code === "SS-2") {
            obj.attr("title", "Surface Damage - Spalling Medium (Aggregate)");
            obj.text("SS");
        } else if (code === "SS-3") {
            obj.attr("title", "Surface Damage - Spalling Small (Surface exposed)");
            obj.text("SS");
        } else if (code === "SW-1") {
            obj.attr("title", "Surface Damage - Wear, Increased Roughness. Large (Surface exposed)");
            obj.text("SW");
        } else if (code === "SW-2") {
            obj.attr("title", "Surface Damage - Wear, Increased Roughness. Media (Aggregate)");
            obj.text("SW");
        } else if (code === "SW-3") {
            obj.attr("title", "Surface Damage - Wear, Increased Roughness. Small (Deep voids)");
            obj.text("SW");
        } else if (code === "#4-1") {
            obj.attr("title", "Survey Abandoned - Out of Survey Area");
            obj.text("#4");
        } else if (code === "#4-2") {
            obj.attr("title", "Survey Abandoned - Complete from next side");
            obj.text("#4");
        } else if (code === "#4-3") {
            obj.attr("title", "Survey Abandoned - Defect");
            obj.text("#4");
        } else if (code === "#4-4") {
            obj.attr("title", "Survey Abandoned - Seal Pipe");
            obj.text("#4");
        } else if (code === "#4-5") {
            obj.attr("title", "Survey Abandoned - U-Trap");
            obj.text("#4");
        }
    }

});
