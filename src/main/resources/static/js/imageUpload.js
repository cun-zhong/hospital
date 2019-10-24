layui.use('upload', function(){
    var $ = layui.jquery
        ,upload = layui.upload;
    var uploadurl="http://孙宇豪.online:8080/upload/upload/uploadImage";
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#fileUpload'
        ,url: uploadurl
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#img').attr('src', result); //图片链接（base64）
                $("#img").show();
            });
        }
        //请求接口成功
        ,done: function(res){
            console.log(res);

            //如果上传失败
            if(res.errorCode < 0){
                //失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
                //返回失败原因
                return layer.msg(res.errorMessage);
            }

            //上传成功
            //向后台返回图片地址
            $('#hospitalHeadImage').attr('value', res.result);
            return layer.msg('上传成功');

        }
        //请求接口失败
        ,error: function(){
            //失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
});