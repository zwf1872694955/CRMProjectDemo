layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    //获取表单数据  发送ajax
   form.on('submit(addOrUpdateSaleChance)',function (data){
       //数据加载层
       var index=layer.msg("数据提交中，请稍后……",{icon:16,time:false,shade:0.8});
       var  url=ctx+"/sale_chance/add";
      var id=$("input[name='id']").val();
      //405请求方式错误
       if(id!=null&&id!=''){
           url=ctx+"/sale_chance/update";
       }
       //获取表单中的id是否存在  id存在是执行更新操作 id不存在执行添加操作
       $.post(url,data.field,function (data) {
           if(data.code==200){
               top.layer.close(index);  //关闭弹出层
               top.layer.msg("操作成功！",{icon:6});
               top.layer.closeAll("iframe");//关闭iframe窗口
               //重新加载
               parent.location.reload();  //父窗口重新加载
           }else {
               top.layer.msg(data.msg,{icon:5});
           }
       });
       //阻止页面跳转
       return false;
   });
   //关闭iframe弹出层
    $('#closeButton').click(function (){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    //发送ajax请求
    $.ajax({
        url:ctx+'/user/assign',
        type:'get',
        success:function (data){
            if(data!=null&&data!=''){
                var str="";
                for (var i=0;i<data.length;i++) {
                    console.log($("#assign").val()==data[i].id);
                    if($("#assign").val()==data[i].id){
                        str = "<option value='" + data[i].id + "'selected='selected'>"+data[i].username+"</option>";
                    }else {
                        str = "<option value=" + data[i].id + ">"+data[i].username+"</option>";
                    }

                    $('#assignMan').append(str);
                }
            }
            //重新加载
            layui.form.render('select');
        }
    });

});