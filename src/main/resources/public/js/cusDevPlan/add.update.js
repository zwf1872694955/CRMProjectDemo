layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    //点击取消关闭窗口
    $(".layui-btn").click(function (){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index);
    });

    //点击确认提交参数
    form.on("submit(addOrUpdateCusDevPlan)",function (data){
       //加载层显示
        var index=layer.msg("数据提交中，请稍后……",{icon:16,time:false,shade:0.8});
        //获取表单所有元素值
        var url=ctx+"/cus_dev_plan/add";
        if($("[name='id']").val()){
            url=ctx+"/cus_dev_plan/update";
        }
     $.post(url,data.field,function (res) {
         console.log(res);
           if(res.code==200){
               //提示用户
               top.layer.msg(res.msg,{icon:6});
               //关闭弹出层
               top.layer.close(index);
               //关闭所有的iframe
               layer.closeAll("iframe");
               //重新加载数据
               parent.location.reload();
           }else {
               top.layer.msg(res.msg,{icon:5});
               // top.layer.close(index);

           }
     });// 防止页面跳转
               return false;
    });


});