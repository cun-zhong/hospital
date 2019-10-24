/**

 @Name：医院管理
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
        ,url: 'hospital/queryHospital' //模拟接口
        ,contentType:'application/json'
        ,method:'post'
        ,request :{
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,where:{
            hospitalCode:'',
            hospitalName:''
        }
        ,cols: [[
            {type: 'numbers',  fixed: true}
            ,{field: 'id', width: 100, title: '医院编号', sort: true}
            ,{field: 'hospitalName', title: '医院名称', minWidth: 100}
            ,{field: 'hospitalCode', title: '医院编码'}
            ,{field: 'hospitalHeadImage', title: '医院Logo',width: 100, templet: '#imgTpl'}
            ,{field: 'address', title: '地址'}
            ,{field: 'tel', title: '联系方式'}
            ,{field: 'hospitalInfo', title: '简介'}
            ,{field: 'level', title: '医院级别'}
            ,{field: 'createTime', title: '创建时间',sort: true}
            // ,{field: 'status', title: '发布状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            ,{title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list'}
        ]]
        ,page: true
        ,limit: 10
        ,limits: [10, 15, 20, 25, 30]
        ,text: '对不起，加载出现异常！'
    });

    exports('hospitalist', {})
});