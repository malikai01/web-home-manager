/**
 * Created by malikai on 2018/5/22.
 */

function login(e) {
    var loginName=$('#loginName').val();
    var password=$('#password').val();
    var $btn = $(e);
    $btn.button('loading');
    setTimeout(function() {
        $.ajax({
            url:"/manager/toLogin",
            contentType: "application/json;charset=utf-8",
            clearForm : false,
            resetForm : false,
            type : 'post',
            data: JSON.stringify({ 'loginName':loginName,'password' : password  }),
            success : function(data) {
                $btn.button('reset');
                if(data.status>=0){
                    window.location="/manager/index"
                    alert(data.message);
                }else{
                    alert(data.message);
                }
            },error:function(data){
                alert("网络异常，请稍后再试");
                $btn.button('reset');
            }
        });
    }, 100);
}

function register() {
    window.location="/manager/register";
}
