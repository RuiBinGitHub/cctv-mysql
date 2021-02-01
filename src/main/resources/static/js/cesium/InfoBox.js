function showimg(obj, event) {
    $(obj).css("cursor", "pointer");
    $(obj).css("text-decoration", "underline");
    $("#img").css("width", "400px");
    $("#img").css("height", "300px");
    $("#img").css({
        "left": 20,
        "top": event.pageY - 300
    });
    $("#img").css("position", "fixed");
    $("#img").css({
        "display": "none",
        "z-index": 9
    });
    const name = $(obj).attr("id");
    $("#img").attr("src", "/image/" + name + ".png");
    $("#img").show();
}

function hideimg(obj) {
    $(obj).css("text-decoration", "none");
    $("#img").hide();
}
