$(document).ready(function() {

	// 定义坐标转换
    var projection = new ol.proj.Projection({
        code: "EPSG:2326",
        extent: [793259.70, 799130.01, 870525.78, 848940.16],
        axisOrientation: "neu",
        units: "m",
    });
    proj4.defs("EPSG:2326", "+proj=tmerc+lat_0=22.31213333333334+lon_0=114.1785555555556+k=1+x_0=836694.05+y_0=819069.8+ellps=intl+towgs84=-162.619,-276.959,-161.764,0.067753,-2.24365,-1.15883,-1.09425+units=m +no_defs");
    ol.proj.addProjection(projection);
    ol.proj.addCoordinateTransforms("EPSG:4326", "EPSG:2326", function(coordinate) {
        return proj4("EPSG:4326", "EPSG:2326", coordinate);
    }, function(coordinate) {
        return proj4("EPSG:2326", "EPSG:4326", coordinate);
    });
    ol.proj.addCoordinateTransforms("EPSG:3857", "EPSG:2326", function(coordinate) {
        return proj4("EPSG:3857", "EPSG:2326", coordinate);
    }, function(coordinate) {
        return proj4("EPSG:2326", "EPSG:3857", coordinate);
    });
    /** ************************************************************************ */
    // 地图初始化
    var viewer = new Cesium.Viewer("main",{
        geocoder: true, //是否显示查找控件
        timeline: false, //是否显示时间控件
        animation: false, //是否显示动画控件
        baseLayerPicker: true, //是否显示图层选择控件
        sceneModePicker: true, //是否显示投影方式控件
        navigationHelpButton: true, //是否显示帮助信息控件
        infoBox: true,  //是否显示点击要素之后显示的信息
        imageryProvider: new Cesium.ArcGisMapServerImageryProvider({ // 页面展示底图
            url: "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer",
        })
    });
    viewer._cesiumWidget._creditContainer.style.display = "none"; // 不显示版权声明
    // 设置弹出框内元素可以执行操作
    var iframe = document.getElementsByClassName("cesium-infoBox-iframe")[0];
    iframe.setAttribute("sandbox", "allow-same-origin allow-scripts allow-popups allow-forms"); 
    // 设置地图的默认显示中心点和俯瞰高度
    var center = ol.proj.transform([831800.000, 830400.000], "EPSG:2326", "EPSG:4326");
    viewer.camera.flyTo({  
        destination: Cesium.Cartesian3.fromDegrees(center[0], center[1], 150000)
    });
    /** ************************************************************************ */
    $("#list1 div span").each(function(i){
    	$(this).attr("id", $(this).text());
    });
    $("#showdata").html($("#list1").html());
    /** 设置输入框修改事件 */
    var space = new RegExp(" ", "g");
    $("#textbox").on("input", function() {
    	var value = $(this).val();
    	if (value == "")
    		$("#showdata").html($("#list1").html());
    	else {
    		value = $(this).val().replace(space, "\\ ");
    		var span = $("#list1 div span[id*=" + value + "]").parent().clone();
    		$("#showdata").html(span);
    	}
    	$("#showdata span").each(function() {
    		var text = $(this).text();
    		var font = "<font color='#f00'>" + value + "</font>";
			var expr=new RegExp(value, "gm")
			$(this).html(text.replace(expr, font));
        });
    	$("#showdata").show();
    	$("#btn1").val("-");
    });
    // 显示和隐藏按钮
    $("#btn1").click(function() {
    	if ($(this).val() == "+") {
    		$("#showdata").html($("#list1").html());
    		$("#showdata").show();
    		$(this).val("-");
    	} else {
    		$("#showdata").hide();
    		$("#textbox").val("");
    		$(this).val("+");
    	}
    });
    
    /** 项目列表点击事件 */
    $("#showdata").on("click", "div", function() {
    	var x = $(this).find("span").data("x");
    	var y = $(this).find("span").data("y");
    	var center = ol.proj.transform([x, y], "EPSG:2326", "EPSG:4326");
    	viewer.camera.flyTo({  // 点击元素时页面跳转至指定坐标位置
	        destination: Cesium.Cartesian3.fromDegrees(center[0], center[1], 200)
	    });
    });
    /** ************************************************************************ */
    var scene = viewer.scene;
    var globe = scene.globe;
    globe.depthTestAgainstTerrain = true;
    var manholes = new Array();
    // 获取管道数据绘画管道
    $("#list2 span").each(function(i) {
        var id = $(this).find("a:eq(0)").text();
        var x1 = $(this).find("a:eq(1)").text();
        var y1 = $(this).find("a:eq(2)").text();
        var x2 = $(this).find("a:eq(3)").text();
        var y2 = $(this).find("a:eq(4)").text();
        var grade = $(this).find("a:eq(5)").text();
        var smanhole = $(this).find("a:eq(6)").text();
        var fmanhole = $(this).find("a:eq(7)").text();
        if (x1 == 0.0 || y1 == 0.0 || x2 == 0.0 || y2 == 0.0)
        	return true;
        var center1 = ol.proj.transform([x1, y1], "EPSG:2326", "EPSG:4326");
        var center2 = ol.proj.transform([x2, y2], "EPSG:2326", "EPSG:4326");
        // 绘画管道
        drawPipe(id, "", [center1[0], center1[1], 0, center2[0], center2[1], 0], 0.1, grade);
        if (manholes.indexOf(x1 + ":" + y1) == -1) {
        	drawManhole("s" + i, smanhole, center1[0], center1[1], -0.5);
        	manholes.push(x1 + ":" + y1);
        }
        if (manholes.indexOf(x2 + ":" + y2) == -1) {
        	drawManhole("f" + i, fmanhole, center2[0], center2[1], -0.5);
        	manholes.push(x2 + ":" + y2);
        }
    });
    /** ************************************************************************ */
    //鼠标左键单击事件
    var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
    handler.setInputAction(function(movement) {
        var pick = viewer.scene.pick(movement.position);
        if (Cesium.defined(pick) && pick.id.name == "管道") {
            var geomPipe = Ajax("findinfo", {id: pick.id.id});
            if (geomPipe == null)
            	return false;
            var context = "";
            context += "<table id='tab1' style='width:100%;font-size:16px;font-family:宋体;background-color:#E0E0E0;'>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td align='right'>开始井号：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.smanholeno + "</td>";
            context += "    <td align='right'>结束井号：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.fmanholeno + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td align='right'>起始井坐标X：</td>";
            context += "    <td align='center'>" + geomPipe.smhx + "</td>";
            context += "    <td align='right'>起始井坐标Y：</td>";
            context += "    <td align='center'>" + geomPipe.smhy + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td align='right'>终止井坐标X：</td>";
            context += "    <td align='center'>" + geomPipe.fmhx + "</td>";
            context += "    <td align='right'>终止井坐标Y：</td>";
            context += "    <td align='center'>" + geomPipe.fmhy + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td align='right'>管道材质：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.mater + "</td>";
            context += "    <td align='right'>管道形状：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.shape + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td align='right'>管道尺寸：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.hsize + " (mm)</td>";
            context += "    <td align='right'>管道长度：</td>";
            context += "    <td align='center'>" + geomPipe.pipe.testlength + " (m)</td>";
            context += "  </tr>";
            context += "</table>";
            context += "<table id='tab2' style='width:100%;margin-top:5px;font-size:16px;font-family:宋体;background-color:#E0E0E0;'>";
            context += "  <tr style='height:30px;font-weight:600;background-color:#545454;'>";
            context += "    <td colspan='10'>记录信息</td>";
            context += "  </tr>";
            context += "  <tr style='height:20px;font-weight:600;background-color:#545454;'>";
            context += "    <td width='10%' rowspan='2' align='center'>dist</td>";
            context += "    <td width='10%' rowspan='2' align='center'>Cont</td>";
            context += "    <td width='10%' rowspan='2' align='center'>Code</td>";
            context += "    <td colspan='2' align='center'>Clock</td>";
            context += "    <td colspan='2' align='center'>Intrusion</td>";
            context += "    <td width='10%' rowspan='2' align='center'>分数</td>";
            context += "    <td width='10%' rowspan='2' align='center'>等级</td>";
            context += "    <td width='10%' rowspan='2' align='center'>图片</td>";
            context += "  </tr>";
            context += "  <tr style='height:20px;font-weight:600;background-color:#545454;'>";
            context += "    <td width='10%' align='center'>At</td>";
            context += "    <td width='10%' align='center'>To</td>";
            context += "    <td width='10%' align='center'>%</td>";
            context += "    <td width='10%' align='center'>mm</td>";
            context += "  </tr>";
            for (var i = 0; i < geomPipe.pipe.items.length; i++) {
            	var item = geomPipe.pipe.items[i];
	        	context += "  <tr style='height:25px;background-color:#545454;'>";
	            context += "    <td align='center'>" + item.dist + "</td>";
	            context += "    <td align='center'>" + item.cont + "</td>";
	            context += "    <td align='center'>" + item.code + "</td>";
	            context += "    <td align='center'>" + item.clockAt + "</td>";
	            context += "    <td align='center'>" + item.clockTo + "</td>";
	            context += "    <td align='center'>" + item.percent + "</td>";
	            context += "    <td align='center'>" + item.lengths + "</td>";
	            context += "    <td align='center'>" + item.score + "</td>";
	            context += "    <td align='center'>" + item.grade + "</td>";
	            if (item.picture == null ||  item.picture == "")
	            	context += "    <td align='center'></td>";
	            else
	            	context += "    <td align='center'><a onmouseenter='showimg(this, event)' onmouseleave='hideimg(this)' id='" + item.picture+ "'>图片</a></td>";
	            context += "  </tr>";
			}
            context += "</table>";
            context += "<div style='margin:3px;float:left;'>";
            context += "  <a target='_blank' href='compare?id=" + geomPipe.id + "'>数据对比</a>";
            context += "</div>";
            context += "<img id='img'/>";
            pick.id.description = context;
        }
    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
    
    // 鼠标页面滑动事件
    handler.setInputAction(function(movement) {
    	var ellipsoid = scene.globe.ellipsoid;
		var cartesian = scene.camera.pickEllipsoid(movement.endPosition, ellipsoid);
		var cartographic = ellipsoid.cartesianToCartographic(cartesian);
		lon = Cesium.Math.toDegrees(cartographic.longitude);
		lat = Cesium.Math.toDegrees(cartographic.latitude);
		var center1 = ol.proj.transform([lon, lat], "EPSG:4326", "EPSG:2326");
		var x = center1[0].toFixed(3), y = center1[1].toFixed(3);
		$("#shownate").text("坐标：" + x + "，" + y);
    }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
    
    /** 画井函数 */
    function drawManhole(id, description, lon, lat, height) {
    	var uri = "/cctv/model/F(1).glb";
    	if (description.indexOf("S") != -1)
    		uri = "/cctv/model/S(1).glb";
    	// 确定元素的经纬度和在高度（m）
        var degree = Cesium.Cartesian3.fromDegrees(lon, lat, height);
        var color = Cesium.Color.LIME;
        var model = viewer.entities.add({
            id: id,
            name: "沙井",
            position: degree,
            model: {
                uri: uri,
                minimumPixelSize: 1,
                maximumSize: 1,
                maximumScale: 1,
                silhouetteColor: Cesium.Color.WHITE,
                debugShowBoundingVolume: false,
                debugWireframe: false,
                runAnimations: true,
                scale: 10
            }
        });
    }

    function drawPipe(id, description, position, circle, i) {
    	var color = null;
        if (i == 1.0) 
        	color = Cesium.Color.GREEN;
        else if (i == 2.0) 
        	color = Cesium.Color.ORANGE;
        else if (i == 3.0) 
        	color = Cesium.Color.YELLOW;
        else if (i == 4.0 || i == 5.0) 
        	color = Cesium.Color.RED;
    	viewer.entities.add({
            id: id,
            name: "管道",
            polylineVolume: {
                positions: Cesium.Cartesian3.fromDegreesArrayHeights(position),
                shape: computeCircle(circle),
                material: color
            }
        });
    }

    function computeCircle(radius) {
        var positions = [];
        for (var i = 0; i < 360; i++) {
            var radians = Cesium.Math.toRadians(i);
            var x = radius * Math.cos(radians);
            var y = radius * Math.sin(radians);
            var position = new Cesium.Cartesian2(x, y);
            positions.push(position);
        }
        return positions;
    }
    /** ********************************************************** */
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