layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    //登录操作
    form.on('submit(login)', function(data){
       // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        //console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
       //console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        $.ajax({
            url:ctx+"/user/login",
            data:{
                username:data.field.username,
                password:data.field.password
            },
            type:"post",
            success:function (result) {
                if(result.code==200){
                    //弹出提示框  笑脸
                    layer.msg("登录成功！",{icon:1},function () {
                        if($("#rememberMe").prop("checked")){
                            //存储在cookie中不会因为关闭服务器而失效 7天保存
                            $.cookie("username",result.result.userName,{expires:7});
                            $.cookie("truename",result.result.userName,{expires:7});
                            $.cookie("userIdStr",result.result.userIdStr,{expires:7});
                        }else {
                            //存储在cookie中不会因为关闭服务器而失效
                            $.cookie("username",result.result.userName);
                            $.cookie("truename",result.result.userName);
                            $.cookie("userIdStr",result.result.userIdStr);
                        }
                        window.location.href=ctx+"/main";
                    });

                }else {
                    layer.msg(result.msg,{icon:5});
                }
                // console.log(result);


            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


});

