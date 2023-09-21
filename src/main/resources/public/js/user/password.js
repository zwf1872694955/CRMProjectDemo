layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    form.on('submit(saveBtn)', function (data) {
         $.ajax({
             type:"post",
             url:ctx+"/user/updatePwd",
             data:{
                 oldPassword:data.field.old_password,
                 newPassword:data.field.new_password,
                 confirmPassword:data.field.again_password
             },
             success:function (res){
                   console.log(res);
                   if(res.code==200){
                       $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                       $.removeCookie("truename",{domain:"localhost",path:"/crm"});
                       $.removeCookie("username",{domain:"localhost",path:"/crm"});
                       layer.msg("密码修改成功,等待5秒进行跳转……",{icon:1},function () {
                           window.parent.location.href=ctx+"/index";
                       });
                   }else {
                       layer.msg(res.msg,{icon:5});
                   }
             }
         });
        // console.log(data);

    });
});