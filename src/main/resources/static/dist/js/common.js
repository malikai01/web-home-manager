
/**
 * 将表单数据转为json
 * @param id from表单id
 * @param addParam 还需额外添加的参数（json格式）
 */
function form2Json(id, addParam) {
    var arr = $("#" + id+" .form-control").serializeArray();
    var jsonStr = "";
    jsonStr += '{';
    if (typeof(addParam) != 'undefined' && addParam != '') {
        jsonStr += addParam;
    }
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].value.trim() != null && arr[i].value.trim() != '') {
            jsonStr += '"' + arr[i].name + '":"' + arr[i].value.trim() + '",';
        } else {
            jsonStr += '"' + arr[i].name + '":"",';
        }
    }
    if (jsonStr.length > 1) {
        jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
    }
    jsonStr += '}';
    var json = JSON.parse(jsonStr);
    return json;
}

/**
 * 字段显示超长处理，若超长使用bootstrap popover显示
 * @param value
 * @param cutLength
 * @returns {string}
 */
function lengthFormatter(value, cutLength) {
    var content = "";
    var abValue = value;
    if (value != null && value != '') {
        if (value.length > cutLength) {
            abValue = abValue.substr(0, cutLength);
            abValue = abValue + "..";
            content = "<div onmouseover=\"$(this).popover('show');\" onmouseout=\"$(this).popover('hide');\" data-toggle='popover' data-trigger='click|hover' data-container='body' data-content='" + value + "' data-placement='top'>" + abValue + "</div>";
        } else {
            content = value;
        }
    } else {
        content = "";
    }
    return content;
}


/**
 * ajax提交表单数据
 */
function saveForm(formId) {
    $('#' + formId).ajaxSubmit({
        success: function (data) {
            if (data.status == 'success') {
                $('#modalSuccess').modal('show');
            } else {
                $.zui.messager.show('操作失败！', {placement: 'center', type: 'danger', icon: 'icon-exclamation-sign'});
            }
        }
    });
    return false; //不自动提交表单
}