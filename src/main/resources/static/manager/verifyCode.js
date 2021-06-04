/**
 * Created by malikai on 2018-5-24.
 */

// 点击换一张验证码
function changeImg() {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", chgUrl(src));
    $("#info").html("");
}
// 时间戳
// 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function chgUrl(url) {
    var timestamp = (new Date()).valueOf();
    url = url.substring(0, 28);
    if ((url.indexOf("&") >= 0)) {
        url = url + "×tamp=" + timestamp;
    } else {
        url = url + "?timestamp=" + timestamp;
    }
    return url;
}
// 验证码验证
function isRightCode() {
    var code = $("#veryCode").val();
    // alert(code);
    $.ajax({
        type : "POST",
        url : "/veryCode/checkCode",
        data : {"code":code},
        success : callback
    });
}
// 验证以后处理提交信息或错误信息
function callback(data) {
    if (data.toString() == 1) {
        alert("温馨提示您：验证成功了！");
        $("#info").html("温馨提示您：验证成功了！");
        return;
    } else {
        alert("验证失败！");
        $("#info").html("验证失败！");
        return;
    }
}