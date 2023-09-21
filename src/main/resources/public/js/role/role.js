layui.use(['table','layer'],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //角色列表展示
    var  tableIns = table.render({
        elem: '#roleList',
        url : ctx+'/role/params',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'roleName', title: '角色名', minWidth:50, align:"center"},
            {field: 'roleRemark', title: '角色备注', minWidth:100, align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#roleListBar',fixed:"right",align:"center"}
        ]]
    });

    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("roleListTable",{
            page:{
                curr:1
            },
            where:{
                // 角色名
                roleName:$("input[name='roleName']").val()
            }
        })
    });
     //添加和授权操作
     table.on("toolbar(roles)",function (data){
        if(data.event=="add"){

            openAddOrUpdateRoleDialog();
            //进行授权操作
        }else if(data.event=="grant"){
            // console.log(data);
            //获取选中的id
            var index=table.checkStatus(data.config.id);
            var dataArray=index.data;
            // console.log(index);
            openModuleGrantPageDialog(dataArray);

        }

     });
     //行内工具栏  进行行内数据的删除和编辑操作
    table.on("tool(roles)",function (data){
          if(data.event=="edit"){
              // console.log(data);
              openAddOrUpdateRoleDialog(data.data.id);
          }else if(data.event=="del"){
               //进行删除操作
              deleteRoleInfoMake(data.data.id);
          }

    });

      function openModuleGrantPageDialog(data){
          // console.log(data);
          if(data.length==0){
              layer.msg("请选择要授权的角色名！",{icon:5});
              return;
          }
          if(data.length>1){
              layer.msg("暂不支持多个角色绑定资源！",{icon:5});
              return;
          }
          var url=ctx+"/role/goModulePage?roleId="+data[0].id;
          var title="角色分配资源管理";
          layer.open({
              type:2,
              content:url,
              title:title,
              area:['500px','450px'],
              maxmin:true
          });
      }
      //删除用户信息
    function deleteRoleInfoMake(roleId){
        //确认框提示用户是否要删除
        layer.confirm("你确定要删除记录吗？",{icon:3,title:"角色信息管理"},function (index){
             var url=ctx+"/role/delete";
             $.post(url,{roleId:roleId},function (res){
                 if(res.code==200){
                    parent.layer.close(index);
                    parent.layer.msg("用户信息删除成功！",{icon:6});
                    tableIns.reload(); //刷新表格
                 }else{
                     parent.layer.close(index);
                     parent.layer.msg(res.msg,{icon:5});
                 }
             });


        });
    }

     function  openAddOrUpdateRoleDialog(id){
         var title="用户信息添加窗口";
         var url=ctx+"/role/goAddOrUpdateDialog";
         if(id!=null&&id!=''){
          title="用户信息更新窗口";
          url+="?roleId="+id;
         }
         layer.open(
             {
                 type: 2,
                 title:title,
                 content:url,
                 maxmin:true,
                 area:["500px","430px"]
             }
         )

     }


    // // 头工具栏事件
    // table.on('toolbar(roles)',function (obj) {
    //     switch (obj.event) {
    //         case "add":
    //             openAddOrUpdateRoleDialog();
    //             break;
    //         case "grant":
    //             openAddGrantDialog(table.checkStatus(obj.config.id).data);
    //             break;
    //     }
    // });
    //
    //
    // table.on('tool(roles)',function (obj) {
    //     var layEvent =obj.event;
    //     if(layEvent === "edit"){
    //         openAddOrUpdateRoleDialog(obj.data.id);
    //     }else if(layEvent === "del"){
    //         layer.confirm("确认删除当前记录?",{icon: 3, title: "角色管理"},function (index) {
    //             $.post(ctx+"/role/delete",{id:obj.data.id},function (data) {
    //                 if(data.code==200){
    //                     layer.msg("删除成功");
    //                     tableIns.reload();
    //                 }else{
    //                     layer.msg(data.msg);
    //                 }
    //             })
    //         })
    //     }
    // });
    //
    //
    //
    //
    // function openAddOrUpdateRoleDialog(id) {
    //     var title="角色管理-角色添加";
    //     var url=ctx+"/role/addOrUpdateRolePage";
    //     if(id){
    //         title="角色管理-角色更新";
    //         url=url+"?id="+id;
    //     }
    //     layui.layer.open({
    //         title:title,
    //         type:2,
    //         area:["700px","500px"],
    //         maxmin:true,
    //         content:url
    //     })
    // }
    //
    //
    // function openAddGrantDialog(datas) {
    //     if(datas.length==0){
    //         layer.msg("请选择待授权的角色记录!",{icon:5});
    //         return;
    //     }
    //     if(datas.length>1){
    //         layer.msg("暂不支持批量角色授权!",{icon:5});
    //         return;
    //     }
    //
    //     layui.layer.open({
    //         title:"角色管理-角色授权",
    //         type:2,
    //         area:["700px","500px"],
    //         maxmin:true,
    //         content:ctx+"/role/toAddGrantPage?roleId="+datas[0].id
    //     })
    //
    // }
    //


});
