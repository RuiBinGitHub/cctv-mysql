$(document).ready(function() {
	
    var date = $("#table1 tr:eq(2) td:eq(1) span").text();
	var cont = $("#table1 tr:eq(2) td:eq(3) span").text();
    $("#table1 tr:eq(2) td:eq(3) span").text(getDate(date, cont));
    function getDate(idate, count) {
        var temp = new Date();
        var date = new Date(idate);
        date.setFullYear(date.getFullYear() + Number(count));
        var y = date.getFullYear();
        var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return text = y + "-" + m + "-" + d;
    }
    
});