layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var  formSelects = layui.formSelects;

    //下拉选项框
    formSelects.config("selectId",{
     type:'post',   //必须用post请求方式
     searchUrl:ctx+"/role/lists?userId="+$('input[name="id"]').val(),
     keyName:'roleName',
     keyVal:'id'
    },true);

    //如果点击取消关闭窗口
    $("#cancel").click(function (){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });
    //如果点击确认
    form.on("submit(addOrUpdateUser)",function (data){
       var index=layer.msg("数据添加中,请稍后……",{icon:16,time:false,shade:0.8});
        var url=ctx+"/user/add";
        if($('[name="id"]').val()){
            url=ctx+"/user/update";
        }
        $.post(url,data.field,function (res){
             if(res.code==200){
                 top.layer.msg(res.msg,{icon:6});
                 top.layer.close(index);  //关闭窗口
                 layer.closeAll("iframe");
                 parent.location.reload();  //重新加载数据
             } else {
                 top.layer.msg(res.msg,{icon:5});
                 top.layer.close(index);  //关闭窗口
             }
        });
          //防止页面跳转
        return false;
    });



});