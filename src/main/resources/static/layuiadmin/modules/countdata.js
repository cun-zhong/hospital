/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //数据概览
  layui.use(['carousel', 'echarts'], function(){
    var $ = layui.$
    ,carousel = layui.carousel
    ,echarts = layui.echarts;


    $.ajax({
      url: "booking/getOrderCount",
      type: "get",
      dataType: "json",
      success: function (res) {
        if (res.success) {
          var dates=res.data.dates;
          var counts=res.data.counts;
          var series=[]
          for(var i=0;i<counts.length;i++){
            var data={}
            data['name']=counts[i].name;
            data['type']='line';
            data['smooth']=true;
            data['itemStyle']={normal: {areaStyle: {type: 'default'}}};
            data['data']=counts[i].data
            series.push(data)
          }
          var echartsApp = [], options=[
            {
              title: {
                text: '挂号流量趋势',
                x: 'center',
                textStyle: {
                  fontSize: 14
                }
              },
              tooltip : {
                trigger: 'axis'
              },
              legend: {
                data:['','']
              },
              xAxis : [{
                type : 'category',
                boundaryGap : false,
                data: dates
              }],
              yAxis : [{
                type : 'value'
              }],
              series : series
            }
          ], elemDataView = $('#LAY-index-dataview').children('div')
              ,renderDataView = function(index){
            echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
            echartsApp[index].setOption(options[index]);
            window.onresize = echartsApp[index].resize;
          };

          //没找到DOM，终止执行
          if(!elemDataView[0]) return;



          renderDataView(0);

          //监听数据概览轮播
          var carouselIndex = 0;
          carousel.on('change(LAY-index-dataview)', function(obj){
            renderDataView(carouselIndex = obj.index);
          });

          //监听侧边伸缩
          layui.admin.on('side', function(){
            setTimeout(function(){
              renderDataView(carouselIndex);
            }, 300);
          });

          //监听路由
          layui.admin.on('hash(tab)', function(){
            layui.router().path.join('') || renderDataView(carouselIndex);
          });

          // echartsApp[0].setOption({
          //   xAxis: {
          //     data: dates
          //   },
          //   series: [{
          //     // 根据名字对应到相应的系列
          //     name: $("#roads").val(),
          //     type:'line',
          //     data: counts
          //   }]
          //
          // })

        } else {
          layui.layer.msg(data.msg);
        }
      }
    });


    
    

  });


  
  exports('countdata', {})
});