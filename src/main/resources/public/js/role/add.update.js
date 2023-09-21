layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

   form.on("submit(addOrUpdateRole)",function (data){
    var index=layer.msg("数据处理中，请稍后……",{icon:16,time:false,shade:0.8});
       var url=ctx+"/role/add";
       if($('[name="id"]').val()){
           url=ctx+"/role/update";
       }
    $.post(url,data.field,function (res) {
            if(res.code==200){
                top.layer.msg(res.msg,{icon:6});
                top.layer.close(index);
                layer.closeAll("iframe");
                parent.location.reload();//刷新父页面
            }else {
                top.layer.msg(res.msg,{icon:5});
                top.layer.close(index);
            }

     });
       return false;
   });

   //点击取消关闭窗口
    $("#closebtn").click(function (){
        //获取当前窗口索引
      var index=parent.layer.getFrameIndex(window.name);
      //关闭窗口
        parent.layer.close(index);
    });




    // form.on('submit(addOrUpdateRole)',function (data) {
    //     var index= top.layer.msg("数据提交中,请稍后...",{icon:16,time:false,shade:0.8});
    //     var url = ctx+"/role/save";
    //     if($("input[name='id']").val()){
    //         url=ctx+"/role/update";
    //     }
    //     $.post(url,data.field,function (res) {
    //         if(res.code==200){
    //             top.layer.msg("操作成功");
    //             top.layer.close(index);
    //             layer.closeAll("iframe");
    //             // 刷新父页面
    //             parent.location.reload();
    //         }else{
    //             layer.msg(res.msg);
    //         }
    //     });
    //     return false;
    // });

});