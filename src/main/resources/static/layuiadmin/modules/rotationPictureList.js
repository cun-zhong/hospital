/**

 @Name：轮播图
 @Author：Daniel孙宇豪
 @Site：
 @License：LPPL

 */


layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    //医院筛选
    table.render({
        elem: '#LAY-app-content-list'
        ,url: 'rotationPicture/findAll' //模拟接口
        ,method:'get'
        ,cols: [[
            {type: 'numbers',  fixed: true}
            ,{field: 'id', width: 100, title: '图片编号', sort: true}
            ,{field: 'hospitalName', title: '医院名称', minWidth: 100}
            ,{field: 'pictureName', title: '图片描述'}
            ,{field: 'pictureUrl', title: '图片',width: 100, templet: '#imgTpl'}
            ,{title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list'}
        ]]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-app-content-list)', function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
            layer.open({
                type: 2
                ,title: '编辑图片'
                ,content: 'rotationPicture/updateOrAddPage?id='+ data.id
                ,maxmin: true
                ,area: ['550px', '550px']
                ,btn: ['确定', '取消']
                ,yes: function(index, layero){
                    //点击确认触发 iframe 内容中的按钮提交
                    var submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");
                    submit.click();
                }
            });
        }
    });
    exports('rotationPictureList', {})
});