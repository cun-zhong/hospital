/**

 @Name：医生管理
 @Author：Daniel孙宇豪
 @Site：
 @License：LPPL

 */


layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table

    //筛选
    table.render({
        elem: '#LAY-app-content-list'
        ,url: 'user/queryUser' //模拟接口
        ,contentType:'application/json'
        ,method:'post'
        ,request :{
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,where:{
            name:'',
            idCard:'',
            tel:'',
        }
        ,cols: [[
            {type: 'numbers',  fixed: true}
            ,{field: 'name', title: '名字'}
            ,{field: 'idCard', title: '身份证'}
            ,{field: 'tel', title: '联系方式'}
            ,{field: 'gender', title: '性别',templet: function(d){
                    var info = d.gender;
                    return info===1?'男':'女';
                }}
            ,{field: 'integral', title: '信用分'}
            ,{field: 'createdTime', title: '创建时间',sort: true}
            ,{title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list'}
        ]]
        ,page: true
        ,limit: 10
        ,limits: [10, 15, 20, 25, 30]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-app-content-list)', function(obj){
        var data = obj.data;
        //删除
        if(obj.event === 'del'){
            layer.confirm('确定删除吗？', function (index) {
                $.ajax({
                    url: "user/deleteById?id=" + data.id,
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        if (data.code === 0) {
                            layui.layer.msg("删除成功");
                            table.reload('LAY-app-content-list');
                        } else {
                            layui.layer.msg(data.msg);
                        }
                    }
                });
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            layer.open({
                type: 2
                ,title: '编辑用户'
                ,content: 'user/updateOrAddUser?id='+ data.id
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


    exports('userlist', {})
});