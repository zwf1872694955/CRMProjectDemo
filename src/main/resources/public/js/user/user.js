layui.use(['table','layer',"form"],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        elem: '#userList',
        url : ctx+'/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '手机号', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("userListTable",{
            page:{
                curr:1
            },
            where:{
                userName:$("input[name='userName']").val(),// 用户名
                email:$("input[name='email']").val(),// 邮箱
                phone:$("input[name='phone']").val()    //手机号
            }
        });
    });

     table.on("toolbar(users)",function (data){
        if(data.event=="add"){
            openSaveUserDialog();

        }else if(data.event=="del"){
            //获取表格选中项
            var index=table.checkStatus(data.config.id);
            deleteUserInfoMake(index.data);

        }


     });
     //行内工具栏
     table.on("tool(users)",function (data){
        if(data.event=="edit"){   //更新操作
            openSaveUserDialog(data.data.id);
        }else if(data.event=="del"){
            var id=[{id:data.data.id}];
            deleteUserInfoMake(id);
        }

     });

    function openSaveUserDialog(id){
        //打开 iframe窗口
        var title="用户信息添加窗口";
        var url=ctx+"/user/openSaveUserPage";
        if(id!=null && id!=''){  //更新操作
            title="用户信息更新窗口";
            url+="?userId="+id;
        }
        layer.open({
           type: 2,
            title:title,
            content:url,
            area:['500px','450px'],
            maxmin:true
        });


    }

    function  deleteUserInfoMake(data){
        if(data==null||data.length==0){
            layer.msg("请选择要删除的数据！",{icon:0});
            return;
        }

        layer.confirm("你确定要删除该数据吗？",{icon:3,title:'用户信息管理'},function (index) {
              var url=ctx+"/user/delete";
              var ids="ids="
              for(var i=0;i<data.length;i++){
                  if(i==data.length-1){
                      ids+=data[i].id;
                  }else {
                      ids+=data[i].id+"&ids=";
                  }

              }
            $.post(url,ids,function (res){
                 if(res.code==200){
                     layer.msg("数据删除成功！",{icon:6});
                     tableIns.reload();//表格数据重新加载！
                 } else {
                     layer.msg(res.msg,{icon:5});
                     parent.layer.close(index);
                 }
            });

        });


    }

});
