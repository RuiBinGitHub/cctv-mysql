$(document).ready(function() {
    var list1 = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
    /** *************************************************************** */
    var value1 = Number($("#data a:eq(0)").text()); // 未完成项目
    var value2 = Number($("#data a:eq(1)").text()); // 已完成项目
    var value3 = value1 + value2 == 0 ? 1 : value1 + value2;
    var loop1 = (value2 / value3 * 100).toFixed(0);
    $(".value1:eq(0)").text(value1 + value2 + "（个）");
    /** *************************************************************** */
 // 完成项目百分比
    var show1 = new Highcharts.chart("show1",{
        chart: {
            spacing: [0, 0, 0, 0]
        },
        title: {
            text: loop1 + "%",
            floating: true
        },
        plotOptions: {
            pie: {
                dataLabels: {  
                    enabled: false // 不显示标签
                }
            }
        },
        exporting: {
            enabled: false // 不显示导出按钮
        },
        credits: {
            enabled: false // 不显示官网链接
        },
        series: [{
            type: "pie",
            innerSize: "40%",  // 内心圆大小
            data: [{
                name: "已提交项目",
                y: value2,
                color: "#FC851D"
            }, {
                name: "未提交项目",
                y: value1,
                color: "#DADADA"
            }]
        }]
    },function(c) {
        var centerY = c.series[0].center[1];
        var titleHeight = parseInt(c.title.styles.fontSize);
        c.setTitle({
            y: centerY + titleHeight / 2
        });
    });
    /** ************************************************************ */
    // 及格项目百分比
    var value4 = Number($("#data a:eq(2)").text()); // 全部项目
    var value5 = Number($("#data a:eq(3)").text()); // 及格项目
    var value6 = value4 == 0 ? 1 : value4;
    var loop2 = (value5 / value6 * 100).toFixed(0);
    var show2 = new Highcharts.chart("show2",{
        chart: {
            spacing: [0, 0, 0, 0]
        },
        title: {
        	text: loop2 + "%",
            floating: true
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: false
                }
            }
        },
        exporting: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: [{
            type: "pie",
            innerSize: "40%",
            data: [{
                name: "及格数量",
                y: value5,
                color: "#90ED7D"
            }, {
                name: "不及格数量",
                y: value4 - value5,
                color: "#DADADA"
            }]
        }]
    },function(c) {
        var centerY = c.series[0].center[1];
        var titleHeight = parseInt(c.title.styles.fontSize);
        c.setTitle({
            y: centerY + titleHeight / 2
        });
    });
    /** ***************************************************************** */
    // 统计项目数量
    var index = 0;
    var tArray = new Array();
    
    var map = new Map();
    $("#items1 div").each(function(i){
    	var text = $(this).text().substring(0, 4);
    	if (!map.has(text))
    		map.set(text, [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
    	var array = map.get(text);
    	text = $(this).text().substring(5, 7);
    	array[Number(text - 1)]++;
    });
	var list = new Array();
	map.forEach(function(value, key) {
		 list.push({name: key, data: value});
	});
    var main2 = new Highcharts.Chart({
        chart: {
            renderTo: "main2",
            type: "column"
        },
        title: {
            text: ""
        },
        xAxis: {
            lineWidth: 2,
            labels: {
                y: 20
            },
            categories: list1
        },
        yAxis: {
            min: 0,
            lineWidth: 2,
            title: {
                text: "数量"
            }
        },
        legend: {
            align: "right",
            layout: "vertical",
            verticalAlign: "middle"
        },
        credits: {
            enabled: false // 不显示官网链接
        },
        series: list,
        tooltip: {
            formatter: function() {
                return this.x + "：" + this.y + "个";
            }
        }
    });
    /** ***************************************************************** */
    // 统计项目分数
    var list1 = new Array();
	var list2 = new Array();
	var list3 = new Array();
	for (var i = 0; i < $("#items2 a").length; i = i + 3) {
		list1.push($("#items2 a").eq(i).text());
		var value1 = Number($("#items2 a").eq(i + 1).text()).toFixed(2);
		var value2 = Number($("#items2 a").eq(i + 2).text()).toFixed(2);
		list2.push(Number(value1));
		list3.push(Number(value2));
	}
    var charMain4 = new Highcharts.Chart({
        chart: {
            renderTo: "main4",
        },
        title: {
            text: ""
        },
        xAxis: {
            lineWidth: 2,
            labels: {
                y: 20
            },
            categories: list1
        },
        yAxis: {
            min: 0,
            max: 100,
            lineWidth: 2,
            title: {
                text: "数量"
            }
        },
        legend: {
            align: "right",
            layout: "vertical",
            verticalAlign: "middle"
        },
        credits: {
            enabled: false // 不显示官网链接
        },
        series : [ {
			name : "管线",
			data : list2
		}, {
			name : "记录",
			data : list3
		} ],
        tooltip: {
            formatter: function() {
                return this.x + "：" + this.y + "分";
            }
        }
    });
    /** ***************************************************************** */
    var map = new Map();
    $("#items2 .score1").each(function(i) {
    	var score1 = Number($(this).text());
    	var score2 = Number($("#items2 .score2").eq(i).text());
		var text = ((score1 + score2) / 2).toFixed(0);
		if (map.has(text)) 
			map.set(text, map.get(text) + 1);
		else
			map.set(text, 1);
	});
    var list = new Array();
    var mapIter = map.keys();
    while((value = mapIter.next().value) != undefined) {
    	list.push({name: value + "分", y: map.get(value), z: 100});
    }
    Highcharts.chart("main5", {
        chart: {
            type: "variablepie"
        },
        title: {
            text: "项目分数占比"
        },
        tooltip: {
            formatter: function() {
                return this.y + "个";
            }
        },
        series: [{
            innerSize: "20%",
            minPointSize: 10,
            data: list,
            zMin: 0
        }]
    });
    /** ***************************************************************** */
    $(".main3Value:eq(0) span").css("background-color", "#F54545");
    $(".main3Value:eq(1) span").css("background-color", "#FF8547");
    $(".main3Value:eq(2) span").css("background-color", "#49BCF7");
    $(".main3Value a").attr("target", "_blank");
    $(".main3Value a").each(function() {
    	var id = $(this).attr("id");
    	$(this).attr("href", "/cctv/project/findinfo?id=" + id);
    });
    /** ***************************************************************** */
});
