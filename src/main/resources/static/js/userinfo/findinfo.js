$(document).ready(function () {

    // const Highcharts = require("../highcharts/modules/exporting");

    let chart = null;
    const list = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"];
    /** *************************************************************** */
    const value1 = Number($("#data a:eq(0)").text()); // 未完成项目
    const value2 = Number($("#data a:eq(1)").text()); // 已完成项目
    const count1 = Math.max(1, value1 + value2);
    const loop1 = (value2 / count1 * 100).toFixed(0);
    /** *************************************************************** */
    Highcharts.chart("show1", {
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
                color: "#FC851D",
                y: value1
            }, {
                name: "未提交项目",
                color: "#DADADA",
                y: value2
            }]
        }]
    }, function (c) {
        const centerY = c.series[0].center[1];
        const titleHeight = parseInt(c.title.styles.fontSize);
        c.setTitle({
            y: centerY + titleHeight / 2
        });
    });
    /** ************************************************************ */
        // 及格项目百分比
    let value3 = Number($("#data a:eq(2)").text()); // 及格项目
    let value4 = Number($("#data a:eq(3)").text()); // 及格项目
    const count2 = Math.max(1, value3 + value4);
    const loop2 = (value4 / count2 * 100).toFixed(0);
    /** *************************************************************** */
    Highcharts.chart("show2", {
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
                name: "不合格项目",
                y: value3,
                color: "#DADADA"
            }, {
                name: "合格项目",
                y: value4,
                color: "#90ED7D"
            }]
        }]
    }, function (c) {
        const centerY = c.series[0].center[1];
        const titleHeight = parseInt(c.title.styles.fontSize);
        c.setTitle({
            y: centerY + titleHeight / 2
        });
    });
    /** ***************************************************************** */
    let map1 = new Map();
    $("#items1 div").each(function () {
        const y = $(this).text().substring(0, 4); // 获取项目年份
        const m = $(this).text().substring(5, 7);  // 获取项目月份
        if (!map1.has(y))
            map1.set(y, [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
        const array = map1.get(y);
        array[Number(m - 1)]++;
    });

    let main2List = [];
    map1.forEach(function (value, key) {
        main2List.push({name: key, data: value});
    });

    chart = new Highcharts.Chart({
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
            categories: list
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
        series: main2List,
        tooltip: {
            formatter: function () {
                return this.x + "：" + this.y + "个";
            }
        }
    });

    let list1 = [];
    let list2 = [];
    let list3 = [];
    for (let i = 0; i < $("#items2 a").length; i = i + 3) {
        list1.push($("#items2 a").eq(i).text());
        list2.push(Number($("#items2 a").eq(i + 1).text()).toFixed(2));
        list3.push(Number($("#items2 a").eq(i + 2).text()).toFixed(2));
    }

    chart = new Highcharts.Chart({
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
                text: "分数"
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
        series: [{
            name: "管线",
            data: list2
        }, {
            name: "记录",
            data: list3
        }],
        tooltip: {
            formatter: function () {
                return this.x + "：" + this.y + "分";
            }
        }
    });

    let map2 = new Map();
    $("#items2 .score1").each(function (i) {
        const score1 = Number($(this).text());
        const score2 = Number($("#items2 .score2").eq(i).text());
        const text = ((score1 + score2) / 2).toFixed(0);
        if (map2.has(text))
            map2.set(text, map2.get(text) + 1);
        else
            map2.set(text, 1);
    });
    let value;
    let main5Llist = [];
    const mapIter = map2.keys();
    while ((value = mapIter.next().value) !== undefined) {
        main5Llist.push({name: value + "分", y: map2.get(value), z: 100});
    }

    Highcharts.chart("main5", {
        chart: {
            type: "variablepie"
        },
        title: {
            text: "项目分数占比"
        },
        tooltip: {
            formatter: function () {
                return this.y + "个";
            }
        },
        series: [{
            innerSize: "20%",
            minPointSize: 10,
            data: main5Llist,
            zMin: 0
        }]
    });
    /** ***************************************************************** */
    $("#main3 .span1:eq(0)").css("background-color", "#F54545");
    $("#main3 .span1:eq(1)").css("background-color", "#FF8547");
    $("#main3 .span1:eq(2)").css("background-color", "#49BCF7");
    $(".span2").attr("target", "_blank");
    $(".span2").on("click", function () {
        const id = $(this).attr("id");
        window.open("/cctv/project/findinfo?id=" + id);
    });
    /** ***************************************************************** */
});
