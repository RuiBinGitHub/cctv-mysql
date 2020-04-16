$(document).ready(function() {
	
	var path = $("input[name=path]").val();
	
    // 设置表格链接
    $("#tab1 tbody tr").each(function() {
        var id = $(this).attr("id");
        var url = "/cctv/project/findinfo?id=" + id;
        $(this).find("td:eq(1) a").attr("href", url);
        $(this).find("td:eq(1) a").attr("target", "_blank");
    });

    //绘画管道列表
    $("#items div").each(function(i) {
        var name = $("#tab1 tbody tr").eq(i).find("a").text();
        var html = "<div class='name'>" + name + "</div>";
        html += "<canvas class='pipe' width='840px' height='200px'/>";
        $("#main2").append(html);
        drawPipe(i);
    });

    /** 绘画管道 */
    function drawPipe(index) {
        var canvas = $(".pipe").eq(index)[0];
        var context = canvas.getContext("2d");
        context.font = "12px Courier New";
        //设置字体
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.beginPath();
        //开始绘画
        context.fillStyle = "#A1A1A1";
        context.strokeStyle = "#000000";
        context.rotate(Math.PI * 3 / 2);
        context.rect(-180, 40, 60, 60);
        context.rect(-165, 100, 30, 640);
        context.stroke();
        context.closePath();
        /*************************************************/
        context.fillStyle = "#000000";
        //设置笔颜色
        function Note(dist, code, path) {
            this.dist = dist;
            this.code = code;
            this.path = path;
        }
        var list = new Array();
        $("#items div").eq(index).find("a").each(function() {
            var dist = $(this).data("dist");
            var code = $(this).data("code");
            var path = $(this).data("picture");
            var note = new Note(dist, code, path);
            list.push(note);
        });
        list.sort(function(a, b) {  //对管道数据进行排序
            return Number(a.dist) - Number(b.dist);
        });
        /*************************************************/
        var i = 0;
        var tl = $("#items div").eq(index).data("size");
        tl = tl == 0 ? 1 : tl;
        var itemlength = 100;
        var linklist = new Array();
        while (i < list.length) {
        	var distance = Math.round(list[i].dist / tl * 640 + 100);
        	var location = distance - itemlength < 0 ? itemlength : distance;
        	context.moveTo(-165, distance);
        	context.lineTo(-135, distance);
            context.lineTo(-105, location);
            context.lineTo(-75, location);
            context.fillText(list[i].dist, -56, location + 4);
            context.fillText(list[i].code, -30, location + 4);
            if (list[i].path != undefined) {  //设置鼠标滑动事件
                var locaX = location - 5;
                var locaY = location + 5;
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
            for (var i = 0; i < linklist.length; i += 3) {
            	if (e.offsetY < 60 || e.offsetY > 130)
            		return false;
                if (e.offsetX >= linklist[i] && e.offsetX <= linklist[i + 1]) {
                    $("#showImg img").attr("src", path + linklist[i + 2] + ".png");
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
