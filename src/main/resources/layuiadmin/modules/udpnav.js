layui.define(['layer', 'element'], function (exports) {
    // 操作对象
    var layer = layui.layer
        , element = layui.element
        , $ = layui.jquery;


    admin.req($.extend(true, {
        url: '${ctxPath}/moduleList'
        ,type: 'get'
        ,success: function (res) {
            alert(11111);
        }
    }, options.ajax));

    // 封装方法
    /*
    var mod = {
        // 添加 HTMl
        addHtml: function (addr, obj, treeStatus, data, isSub) {

            // 请求数据
            $.post(addr, data, function (res) {
                if(!res || res.length === 0)
                    return;
                var view = "";
                if(isSub){
                    obj.removeClass("layui-this");
                    var dl = obj.find("dl");
                    if(dl.length==0){
                        dl = $("<dl class=\"layui-nav-child\"></dl>");
                        obj.append(dl);
                    }else
                        return;
                    $(res).each(function (k, v) {
                        view += '<dd>';
                        if (v.target==='_blank') {
                            view += '<a href="' + v.moduleAddr + '" target="_blank">';
                        } else {
                            view += '<a href="javascript:;" href-url="' + v.moduleAddr + '">';
                        }
                        if(v.iconAddr)
                            view += '<i class="layui-icon">' + v.iconAddr + '</i>' + v.moduleName + '</a>';
                        else
                            view += v.moduleName + '</a>';
                        view += "</dd>";
                    });
                    dl.html(view);
                    obj.addClass("layui-nav-itemed");
                    obj.bind("click",function(){
                        window.addTab(obj);
                    })
                }else{
                    $(res).each(function (k, v) {
                        view += '<li id="' + v.id + '" class="layui-nav-item " lay-filter="sub_menu">';
                        if (v.target==='_blank') {
                            view += '<a href="' + v.moduleAddr + '" target="_blank">';
                        } else {
                            view += '<a href="javascript:;" href-url="' + v.moduleAddr + '">';
                        }
                        if(v.iconAddr)
                            view += '<i class="layui-icon">' + v.iconAddr + '</i>' + v.moduleName + '</a>';
                        else
                            view += v.moduleName + '</a>';
                        view += '</li>';
                    });
                    // 添加到 HTML
                    $(document).find(".layui-nav[lay-filter=" + obj + "]").html(view);
                }
                // 更新渲染
                element.init();
            },'json');
        }
        // 左侧主体菜单 [请求地址,过滤ID,是否展开,携带参数]
        , main: function (addr, obj, treeStatus, data, isSub) {
            // 添加HTML
            this.addHtml(addr, obj, treeStatus, data, isSub);
        }, top_left: function (addr, obj, treeStatus, data) {
            // 添加HTML
            this.addHtml(addr, obj, treeStatus, data);
        }
    };
*/
    // 输出
    exports('layui_nav', {});
});


