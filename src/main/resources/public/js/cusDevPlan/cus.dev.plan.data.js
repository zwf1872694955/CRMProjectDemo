layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //计划项数据展示
    var  tableIns = table.render({
        elem: '#cusDevPlanList',
        url : ctx+'/cus_dev_plan/list?saleChanceId='+$("[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "cusDevPlanListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'planItem', title: '计划项',align:"center"},
            {field: 'exeAffect', title: '执行效果',align:"center"},
            {field: 'planDate', title: '执行时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#cusDevPlanListBar"}
        ]]
    });

    table.on('toolbar(cusDevPlans)',function (data){
       if(data.event=="add"){  //添加计划项

           openAddCusDevPlanDialog();


       }else if(data.event=="success"){   //开发成功项
           //开发成功是2
           updateDevStatus(2);

       }else if(data.event=="failed"){ //开发失败项
           //开发失败是3
           updateDevStatus(3);
       }


    });
    table.on("tool(cusDevPlans)",function (data){
        //要传入一个id值
        if(data.event=="edit"){
            openAddCusDevPlanDialog(data.data.id);
        }else if(data.event=="del"){
            deleteCusDevPlanMake(data.data.id);
        }
    });
       //更新营销机会开发状态
      function updateDevStatus(devStatus){
          layer.confirm('您确定要操作吗？',{icon:3,title:'营销机会管理'},function (index) {
              //获取营销机会ID
              var salChanceId=$("[name='id']").val();
              var url=ctx+"/sale_chance/updateDevStatus";
              $.post(url,{devStatus:devStatus,salChanceId:salChanceId},function (res) {
                  if(res.code==200){
                      layer.msg("开发状态更新成功！",{icon:6});
                      layer.closeAll("iframe"); //关闭所有窗口
                      layer.close(index);//关闭确认框
                      parent.location.reload();//重新加载父窗口
                  }else {
                      layer.msg(res.msg,{icon:5});

                  }
              });
          });





      }


    function openAddCusDevPlanDialog(id){
        var url=ctx+"/cus_dev_plan/goAddCusDevPlanPage?sId="+$("[name='id']").val();
        var title="添加用户开发计划！";
        //打开窗口  更新窗口
        if(id!=null&&id!=''){
            title="更新用户开发计划！";
            url+="&cusDevId="+id;
        }
        layer.open({
            type: 2,
            content: url,
            title:title,
            maxmin: true,
            area: ['500px', '300px']
        });



    }

    function deleteCusDevPlanMake(id){
        var url=ctx+"/cus_dev_plan/deleteCusDev";
        layer.confirm('你确定要删改该记录吗？',{icon:3,title:'客户开发计划管理'},function (index){
            $.post(url,{cusId:id},function (res){
                if(res.code==200){
                   layer.msg("数据删除成功！",{icon:6});
                   layer.close(index);
                   tableIns.reload();  //刷新表格数据
                }else {
                    layer.msg(res.msg,{icon:5});
                }
        });


        });
    }

});
