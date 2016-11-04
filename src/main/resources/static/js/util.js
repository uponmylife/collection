function ajaxGet(url) {
    var finalResult = '';
    $.ajax({
        type: "GET",
        url: url,
        success: function(result) {
            finalResult = result;
        },
        async: false
    });
    return finalResult;
}

function ajaxPostJson(url, data, success) {
    ajaxPost(url, JSON.stringify(data), "application/json; charset=utf-8", success);
}

function ajaxPostForm(url, data, success) {
    ajaxPost(url, data, "application/x-www-form-urlencoded; charset=UTF-8", success);
}

function ajaxPost(url, data, contentType, success) {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        contentType: contentType,
        success: success,
        error: function(result) {
            alert(result);
        }
    });
}

function ajaxPutJson(url, data, success) {
    $.ajax({
        type: "PUT",
        url: url,
        data: data,
        contentType: "application/json; charset=utf-8",
        success: success,
        error: function(result) {
            alert(result);
        }
    });
}

function ajaxDelete(url, success) {
    $.ajax({
        type: "DELETE",
        url: url,
        success: success,
        error: function(result) {
            alert(result);
        }
    });
}
