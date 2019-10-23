function openSubmitLayer(url, title, width, height) {
    var settings={
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            //点击确认触发 iframe 内容中的按钮提交
            var submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");
            submit.click();
        }
    }
    openExtendLayer(url, title, width, height, settings)
}

function openCloseLayer(url, title, width, height) {
    var settings={
        btn: ['关闭']
    }
    openExtendLayer(url, title, width, height, settings)
}
function openExtendLayer(url, title, width, height, settings) {
    layer.open($.extend(settings, {
        type: 2,
        title: title,
        maxmin: true,
        area: [width, height],
        offset: ['10px'],
        content: url
    }));
}
function openCustomLayer(myLayer, settings) {
    myLayer.open($.extend(settings, {
        type: 2,
        maxmin: true,
        offset: ['10px', '100px']
    }));
}