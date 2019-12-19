/**

 @Name：挂号订单管理
 @Author：Daniel孙宇豪
 @Site：
 @License：LPPL

 */


layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table


    //查询挂号订单
    table.render({
        elem: '#LAY-app-content-list'
        , url: 'booking/queryBookingOrder' //模拟接口
        , contentType: 'application/json'
        , method: 'post'
        , request: {
            pageNum: 'pageNum' //页码的参数名称，默认：page
            , limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , where: {
            hospitalName: '',
            hisDepartmentName: '',
            chooseDate: ''
        }
        , cols: [[
            {type: 'numbers', fixed: true}
            // , {field: 'id', width: 100, title: '订单编号', sort: true}
            , {field: 'patientName', width: 100, title: '患者姓名', sort: true}
            , {field: 'hospitalName', title: '医院名称', width: 180}
            , {field: 'hisDepartmentName', title: '科室名称', width: 100}
            , {field: 'doctorName', title: '医生名称', width: 100}
            , {field: 'chooseDate', title: '预约日期', width: 120}
            , {
                field: 'am', title: '午别', width: 100, templet: function (d) {
                    return d.am === '0' ? '上午' : '下午';
                }
            }
            , {
                field: 'timeRange', title: '时间段', width: 120, templet: function (d) {
                    if (d.timeRange == '1') {
                        return '08:00-09:00';
                    } else if (d.timeRange == '2') {
                        return '09:00-10:00';
                    } else if (d.timeRange == '3') {
                        return '10:00-11:00';
                    } else if (d.timeRange == '4') {
                        return '11:00-12:00';
                    } else if (d.timeRange == '5') {
                        return '14:00-15:00';
                    } else if (d.timeRange == '6') {
                        return '15:00-16:00';
                    } else if (d.timeRange == '7') {
                        return '16:00-17:00';
                    } else if (d.timeRange == '8') {
                        return '17:00-18:00';
                    }
                }
            }
            , {field: 'rangeSort', title: '就诊号', width: 100}
            // , {field: 'createdTime', title: '创建时间', sort: true, width: 100}
            , {field: 'status', title: '发布状态', templet: '#buttonTpl', Width: 80, align: 'center'}
            , {title: '操作', Width: 100, align: 'center', fixed: 'right', toolbar: '#table-content-list'}
        ]]
        ,page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , text: '对不起，加载出现异常！'
    });

    // //监听工具条
    // table.on('tool(LAY-app-content-list)', function(obj){
    //     var data = obj.data;
    //     //删除
    //     if(obj.event === 'del'){
    //         layer.confirm('确定删除吗？', function (index) {
    //             $.ajax({
    //                 url: "doctor/deleteHospital?id=" + data.id,
    //                 type: "get",
    //                 dataType: "json",
    //                 success: function (data) {
    //                     if (data.code === 0) {
    //                         layui.layer.msg("删除成功");
    //                         table.reload('LAY-app-content-list');
    //                     } else {
    //                         layui.layer.msg(data.msg);
    //                     }
    //                 }
    //             });
    //             layer.close(index);
    //         });
    //     } else if(obj.event === 'edit'){
    //         layer.open({
    //             type: 2
    //             ,title: '编辑医生'
    //             ,content: 'doctor/updateOrAddDoctor?id='+ data.id
    //             ,maxmin: true
    //             ,area: ['550px', '550px']
    //             ,btn: ['确定', '取消']
    //             ,yes: function(index, layero){
    //                 //点击确认触发 iframe 内容中的按钮提交
    //                 var submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");
    //                 submit.click();
    //             }
    //         });
    //     }
    // });


    exports('bookingOrderList', {})
});