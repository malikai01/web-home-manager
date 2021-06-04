/**
 * Created by malikai on 2018-7-10.
 */
var pageSize=10;


$(function(){
    queryAll();
})

function queryAll() {
    var name= $("#name").val();
    var type=$("#type").val();
    var memo=$("#memo").val();
    $.ajax({
        type: "get",
        dataType: "json",
        url: '/v1/takeNames/query', //请求的url
        cache: false,
        data: {
            "name": name,
            "type": type,
            "memo": memo,
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
                        "name": name,
                        "type": type,
                        "memo": memo,
                        "rows": pageSize
                    };
                    $.ajax({
                        type: "GET",
                        url: '/v1/takeNames/query', //请求的url
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
    $("#takeNamesListTable").html('');
    if(result != null && result.length > 0){
        var takeNames_body = '';
        var myDate = new Date();
        $.each(result,function(i,e){
            var isShow = nullToString(e.isShow)=='1'?'是':'否';
            takeNames_body += '<tr>';
            takeNames_body+='<td>'+(i+1)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.name)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+getType(e.type)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+nullToString(e.memo)+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+isShow+'</td>';
            takeNames_body += '<td style="word-wrap:break-word;word-break:break-all;text-align:center;">'+dateFtt("yyyy-MM-dd hh:mm:ss",new Date(e.updateTime))+'</td>';
            takeNames_body += '<td align="center"><button class="btn btn-success"   type="button" onclick="editName('+e.id+')">修  改</button></td>';
            takeNames_body += '</tr>';
        });
        $("#takeNamesListTable").html(takeNames_body);
    }else{
        $("#takeNamesListTable").html("<tr><td colspan='7' align='center'>^_^暂无数据^_^</td></tr>");
    }
}

function empty(obj){
    if(obj != null && obj != '' && obj != 'undefined'){
        return obj ;
    }
    return '';
}
function getType(temp){
    if(temp==null){
        return "";
    }else if (temp=="00"){
        return "中文";
    }else if(temp=="01"){
        return "英文";
    }else {
        return "其他";
    }
}

function nullToString(temp){
    if(temp==null){
        return "";
    }else return temp
}

function reset() {
    $("#name").val("");
    $("#type").val("");
    $("#memo").val("");
}

function addName() {
    window.location='/v1/takeNames/addHtml';
}
function editName(cId) {
    window.location='/v1/takeNames/editHtml/'+cId;
}
function backList() {
    window.location='/v1/takeNames/queryHtml';
}

function saveNames(e) {
    var name=$('#name').val();
    var memo=$('#memo').val();
    var type=$('#type').val();

    var $btn = $(e);
    $btn.button('loading');
    setTimeout(function() {
        $.ajax({
            url:"/v1/takeNames/add",
            contentType: "application/json;charset=utf-8",
            clearForm : false,
            resetForm : false,
            type : 'post',
            data: JSON.stringify({ 'name' : name ,'memo' : memo,'type' : type }),
            success : function(data) {
                $btn.button('reset');
                if(data.status>=0){
                    alert(data.massage);
                }else{
                    alert(data.massage);
                }
            },error:function(data){
                alert("网络异常，请稍后再试");
                $btn.button('reset');
            }
        });
    }, 100);
}
function emptyName() {
    $('#name').val('');
    $('#memo').val('');
    $('#type').val('');
}

function editNames(e) {
    var name=$('#name').val();
    var memo=$('#memo').val();
    var type=$('#type').val();
    var id = $('#id').val();
    var $btn = $(e);
    $btn.button('loading');
    setTimeout(function() {
        $.ajax({
            url:"/v1/takeNames/edit",
            contentType: "application/json;charset=utf-8",
            clearForm : false,
            resetForm : false,
            type : 'post',
            data: JSON.stringify({ 'id':id,'name' : name ,'memo' : memo,'type' : type }),
            success : function(data) {
                $btn.button('reset');
                alert(data.massage);
            },error:function(data){
                alert("网络异常，请稍后再试");
                $btn.button('reset');
            }
        });
    }, 100);
}