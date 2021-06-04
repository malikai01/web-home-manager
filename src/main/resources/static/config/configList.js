/**
 * Created by malikai on 2019-7-22.
 */
var pageSize=10;


$(function(){
    queryAll();
})

function queryAll() {
    var configKey= $("#configKey").val();
    $.ajax({
        type: "get",
        dataType: "json",
        url: '/home-web/v1/config/query', //请求的url
        cache: false,
        data: {
            "configKey": configKey,
            "rows": pageSize
        },
        success: function (data) {
            /*
             * 返回的数据根据自己需要处理
             */
            filteDataAll(data.rows);
            $('#pagination').pagination({
                pageCount: data.total,
                jump: true,
                coping: true,
                homePage: '首页',
                endPage: '末页',
                prevContent: '上页',
                nextContent: '下页',
                keepShowPN: true,
                isHide: false,
                callback: function (api) {
                    var data = {
                        page: api.getCurrent(),
                        "configKey": configKey,
                        "rows": pageSize
                    };
                    $.ajax({
                        type: "GET",
                        url: '/home-web/v1/takeNames/query', //请求的url
                        cache: false,
                        dataType: "json",
                        data: data,
                        success: function (res) {
                            filteDataAll(res.rows);
                        }
                    });
                }
            });

        }
    });
}

/* 加载表格 */
function filteDataAll(result){
    $("#configListTable").html('');
    if(result != null && result.length > 0){
        var takeNames_body = '';
        var myDate = new Date();
        $.each(result,function(i,e){
            takeNames_body += '<tr>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.configKey)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.configValue)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.memo)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.createUser)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+dateFtt("yyyy-MM-dd hh:mm:ss",new Date(e.createTime))+'</td>';
            takeNames_body += '<td align="center"><button class="btn btn-success"   type="button" onclick="editName('+e.id+')">修  改</button></td>';
            takeNames_body += '</tr>';
        });
        $("#configListTable").html(takeNames_body);
    }else{
        $("#configListTable").html("<tr><td colspan='6' align='center'>^_^暂无数据^_^</td></tr>");
    }
}

function empty(obj){
    if(obj != null && obj != '' && obj != 'undefined'){
        return obj ;
    }
    return '';
}

function nullToString(temp){
    if(temp==null){
        return "";
    }else return temp
}

function reset() {
    $("#configKey").val("");
}