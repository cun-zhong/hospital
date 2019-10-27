/**

 @Name：科室管理
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
        ,url: 'department/queryDepartment' //模拟接口
        ,contentType:'application/json'
        ,method:'post'
        ,request :{
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,where:{
            hospitalName:'',
            hisDepartmentName:''
        }
        ,cols: [[
            {type: 'numbers',  fixed: true}
            ,{field: 'id', width: 100, title: '科室编号', sort: true}
            ,{field: 'hospitalName', title: '医院名称'}
            ,{field: 'hisDepartmentName', title: '科室名称', minWidth: 100}
            ,{field: 'introduction', title: '科室介绍'}
            ,{field: 'createdTime', title: '创建时间',sort: true}
            // ,{field: 'status', title: '发布状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
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
                    url: "department/deleteById?id=" + data.id,
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
                ,title: '编辑文章'
                ,content: 'department/updateOrAddDepartment?id='+ data.id
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


    exports('departmentlist', {})
});