/**
 * Created by malikai on 2018-5-24.
 */

window.onload = function () {
    try {
        $(parent.document.getElementsByTagName('iframe')[0]).css('height', $('body').height() + 50);
    } catch (e) {
    }
};
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数
$(function () {
    if (''.length > 0) {
        Util.showMsg('');
    }
    //显示提示
    $('input').focus(function () {
        var ele = $(this);
        if (ele.attr('id') == 'ccode' || ele.attr('id') == 'securityCode') {
            ele = ele.next();
        } else if (ele.attr('id') == 'countrycode') {
            ele = ele.prev();
        }
        ele.next().next().children().eq(1).show();
        ele.next().next().children().eq(2).hide();
        ele.parent().removeClass("newhight");
        ele.parent().removeClass("warn");
    })
    //绑定上传点击事件
    $(".file").click(function () {
        $(this).next().click();
    });
    //font 实现类似 placehol der 的效果
    $('.Tabconter ul li input').focus(function () {
        if ($(this).next().attr('class') != 'file') {
            $(this).parent('li').find('font').hide();
        }
    }).blur(function () {
        if ($(this).val().length == 0) {
            $(this).parent('li').find('font').show();
        }
    })
    $('.Tabconter ul li font').on('click', function () {
        if ($(this).next().attr('class') != 'file') {
            $(this).hide();
            $(this).parent('li').find('input').focus();
        }
    })
    $('.tavlist ul li').on('click', function () {
        var index = $(this).index() + 1;
        $(this).addClass('active').siblings().removeClass("active");
        $('.conter>.Tabconter:nth-child(' + index + ')').show().siblings().hide();
    });
    //ajax 上传文件事件绑定
    //        $(":file").bind("change", ajaxUpload());
    if (false) {
        $(".mobile_validate").hide();
    }
});

function ajaxUpload(ele) {
    alert("暂未开放...");
}


function validateInputValue(ele, isCheckAll) {
    if (isCheckAll || $(ele).attr('id') == 'username') {
        if ($("#username").val().length <= 0) {
            showTips($('#username'), false, "姓名为必填项 请填写");
            return false;
        }
        showTips($('#username'), true);
    }

    //证件号码
    if (isCheckAll || $(ele).attr('id') == 'cardNumber') {
        if ($("#cardNumber").val().length <= 0) {
            showTips($('#cardNumber'), false, "身份证号码为必填项，请填写");
            return false;
        }
        showTips($('#cardNumber'), true);
    }

    //登录账号
    if (isCheckAll || $(ele).attr('id') == 'loginNickName') {
        if ($("#loginNickName").val().length <= 0) {
            showTips($('#loginNickName'), false, "登录账号为必填项");
            return false;
        }
        var reg = /^[uUvV].*$/;
        if (reg.test($('#loginNickName').val())) {
            showTips($('#loginNickName'), false, "登录账号不能以u、U、v、V开头");
            return false;
        }
        if (!isNaN($('#loginNickName').val())) {
            showTips($('#loginNickName'), false, "6-18个字符，由字母、数字或下划线组成");
            return false;
        }
        showTips($('#loginNickName'), true);
    }

    //密码
    if (isCheckAll || $(ele).attr('id') == 'password') {
        if ($("#password").val().length <= 0) {
            showTips($('#password'), false, "密码为必填项");
            return false;
        }
        showTips($('#password'), true);
    }

    //确认密码
    if (isCheckAll || $(ele).attr('id') == 'confirm-password') {
        checkPassword();
    }

    //手机号码
    if (isCheckAll || $(ele).attr('id') == 'mobile') {
        var phone = $("#mobile").val();
        if (phone == null || $.trim(phone) == '') {
            showTips($('#mobile'), false, "请输入手机号码");
            return false;
        }
        showTips($('#mobile'), true);
    }
    //图片验证码
    if ((!isCheckAll && $(ele).attr('id') == 'ccode') && true) {
        if ($("#ccode").val().length == 0) {
            showTips($('#ccode'), false, "验证码未填写");
            return false;
        } else {
            checkCode();
        }
    }
    return true;
}


function showTips(ele, validateResult, tipValue, showGreenIcon) {
    showGreenIcon = showGreenIcon == null ? true : showGreenIcon;
    if (ele.attr('id') == 'ccode') {
        showGreenIcon = false;
        ele = ele.next();
    } else if (ele.attr('class') == 'file') {
        showGreenIcon = false;
    }
    ele.next().next().children().eq(1).hide();
    if (validateResult) {
        if (showGreenIcon) {
            ele.next().show();
        }
        ele.next().next().children().eq(2).hide();
        ele.parent().removeClass("newhight");
        ele.parent().removeClass("warn");
    } else {
        if (showGreenIcon) {
            ele.next().hide();
        }
        ele.next().next().children().eq(2).children("span").html(tipValue);
        ele.next().next().children().eq(2).show();
        ele.parent().addClass("newhight");
        ele.parent().addClass("warn");
    }
}

function submitForm(e) {
    //$("#submitBtn").attr('disabled', 'true');
    if (!validateInputValue(e, true)) {
        $("#submitBtn").removeAttr('disabled');
        return false;
    }
    var code = $("#ccode").val();
    // alert(code);
    $.ajax({
        type : "POST",
        url : "/veryCode/checkCode",
        data : {"code":code},
        success : function (data) {
            if(data==0){
                alert("验证码不正确")
                return false;
            }
        }
    });
    var loginName = $("#username").val();
    var password = $("#password").val();
    var loginNickName = $("#loginNickName").val();
    var cardNumber = $("#cardNumber").val();
    var phone = $("#mobile").val();
    $.ajax({
        url :'/manager/register/add',
        type : 'POST',
        cache : false,
        async: false,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({ 'loginName' : loginName ,'password' : password ,'loginNickName' : loginNickName ,'cardNumber' : cardNumber ,'phone' : phone }),
        success : function(data) {
            if(data.status>=0){
                //alert(data.msg);
                var loginId=data.data;
                window.location="/manager/register/nextStep/"+loginId;
            }else{
                alert(data.message);
            }
        },error :function(){
            alert("网络异常，请稍后再试");
        }
    });
}


function checkPassword() {
    var password = $('#password').val();
    var confirm = $('#confirm-password').val();
    if(password != confirm){
        showTips($('#confirm-password'), false, "两次输入密码不一致");
        return false;
    }
}
// 验证码验证
function checkCode() {
    var code = $("#ccode").val();
    // alert(code);
    $.ajax({
        type : "POST",
        url : "/veryCode/checkCode",
        data : {"code":code},
        success : function (data) {
            if(data==0){
                showTips($('#ccode'), false, "验证码不正确");
                return false
            }else {
                showTips($('#ccode'), true);
                return true;
            }
        }
    });
}
