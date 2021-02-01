$(document).ready(function() {
	
    // 设置表格链接
    $("#tab1 tbody tr").each(function() {
        const id = $(this).attr("id");
        const url = "/cctv/project/findinfo?id=" + id;
        $(this).find("td:eq(1) a").attr("href", url);
        $(this).find("td:eq(1) a").attr("target", "_blank");
    });

    //绘画管道列表
    $("#items div").each(function(i) {
        let name = $("#tab1 tbody tr").eq(i).find("a").text();
        let html = "<div class='name'>" + name + "</div>";
        html += "<canvas class='pipe' width='840px' height='200px'/>";
        $("#main2").append(html);
        drawPipe(i);
    });

    /** 绘画管道 */
    function drawPipe(index) {
        const canvas = $(".pipe").eq(index)[0];
        const context = canvas.getContext("2d");
        context.font = "12px Courier New";
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();
        context.fillStyle = "#A1A1A1";
        context.strokeStyle = "#000000";
        context.rotate(Math.PI * 3 / 2);
        context.fillRect(-165, 100, 30, 640);
        /*************************************************/
        const distlist = [];
        const joinlist = [];

        function Note(dist, code, path) {
            this.dist = dist;
            this.code = code;
            this.path = path;
        }
        let list = [];
        $("#items div").eq(index).find("a").each(function() {
            const dist = $(this).data("dist");
            const code = $(this).data("code");
            const path = $(this).data("picture");
            const note = new Note(dist, code, path);
            list.push(note);
            if (code === "MH")
                distlist.push(dist);
            if (code === "JN")
                joinlist.push(dist);
        });
        list.sort(function(a, b) {  //对管道数据进行排序
            return Number(a.dist) - Number(b.dist);
        });
        /*************************************************/
        let i = 0;
        let size = $("#items div").eq(index).data("size");
        let uses = $("#items div").eq(index).data("uses");
        size = (size === 0.0 ? 1 : size);
        for (let i = 0; i < distlist.length; i++) {
            if (uses === "F") {
                const distance = i > 0 ? 100 : 40;
                const location = distlist[i] / size * 640 + distance;
                context.fillRect(-180, location, 60, 60);
            } else {
                const distance = i > 0 ? 130 : 70;
                const location = distlist[i] / size * 640 + distance;
                context.moveTo(-120, location);
                context.arc(-150, location, 30, 0, Math.PI * 2);
                context.fill();
            }
        }
        for (let i = 0; i < joinlist.length; i++) {
            const location = joinlist[i] / size * 640 + 90;
            context.fillRect(-180, location, 15, 10);
        }

        let itemlength = 100;
        let linklist = [];
        context.fillStyle = "#000000";
        while (i < list.length) {
            let distance = Math.round(list[i].dist / size * 640 + 100);
            let location = distance - itemlength < 0 ? itemlength : distance;
        	context.moveTo(-165, distance);
        	context.lineTo(-135, distance);
            context.lineTo(-105, location);
            context.lineTo(-75, location);
            context.fillText(list[i].dist, -70, location + 4);
            context.fillText(list[i].code, -30, location + 4);
            if (list[i].path !== undefined) {  //设置鼠标滑动事件
                let locaX = location - 5;
                let locaY = location + 5;
                linklist.push(locaX);
                linklist.push(locaY);
                linklist.push(list[i].path);
            }
        	itemlength = location + 15;
        	i++;
        }
        context.stroke();
        context.closePath();
        //结束绘画
        /*************************************************************/
        //设置鼠标靠近显示图片
        $(".pipe").eq(index).mousemove(function(e) {
            $("#showImg").hide();
            for (let i = 0; i < linklist.length; i += 3) {
            	if (e.offsetY < 60 || e.offsetY > 130)
            		return false;
                if (e.offsetX >= linklist[i] && e.offsetX <= linklist[i + 1]) {
                    $("#showImg img").attr("src", "/image/" + linklist[i + 2] + ".png");
                    $("#showImg").css({
                    	"top": index * 200 + 116,
                        "left": linklist[i] - 165
                    });
                    $("#showImg").show();
                }
            }
        });
    }
    $("#showImg").mouseleave(function() {
    	$(this).hide();
    });
});
