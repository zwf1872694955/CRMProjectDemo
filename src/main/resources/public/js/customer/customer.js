layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    //客户列表展示
    var  tableIns = table.render({
        elem: '#customerList',
        url : ctx+'/customer/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'name', title: '客户名',align:"center"},
            {field: 'fr', title: '法人',  align:'center'},
            {field: 'khno', title: '客户编号', align:'center'},
            {field: 'area', title: '地区', align:'center'},
            {field: 'cusManager', title: '客户经理',  align:'center'},
            {field: 'myd', title: '满意度', align:'center'},
            {field: 'level', title: '客户级别', align:'center'},
            {field: 'xyd', title: '信用度', align:'center'},
            {field: 'address', title: '详细地址', align:'center'},
            {field: 'postCode', title: '邮编', align:'center'},
            {field: 'phone', title: '电话', align:'center'},
            {field: 'webSite', title: '网站', align:'center'},
            {field: 'fax', title: '传真', align:'center'},
            {field: 'zczj', title: '注册资金', align:'center'},
            {field: 'yyzzzch', title: '营业执照', align:'center'},
            {field: 'khyh', title: '开户行', align:'center'},
            {field: 'khzh', title: '开户账号', align:'center'},
            {field: 'gsdjh', title: '国税', align:'center'},
            {field: 'dsdjh', title: '地税', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作', templet:'#customerListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });


    // 多条件搜索
    $(".search_btn").on("click",function () {
        table.reload("customerListTable",{
            page:{
                curr:1
            },
            where:{
                customerName:$("input[name='name']").val(),// 客户名
                khno:$("input[name='khno']").val(),// 客户编号
                level:$("#level").val()    //客户级别
            }
        })
    });

    table.on("toolbar(customers)",function (data){
         if(data.event=="add"){  //添加客户信息
             openAddOrUpdateCustomerPage();
         }else if (data.event=="link"){  //联系人管理

         }else if(data.event=="recode"){ //联系记录查看

         }else if (data.event=="order"){  //订单查看
             //获取选中的 不选或多选全部提示
             var index=table.checkStatus(data.config.id);
              openCustomerOrderPage(index.data);
         }

    });


    table.on("tool(customers)",function (data){
          if(data.event=="edit"){
              openAddOrUpdateCustomerPage(data.data.id);
          }else if(data.event=="del"){
              deleteCustomerData(data.data.id);
          }


    });

    function  openCustomerOrderPage(data){
        if(data.length==0){
            layer.msg("请选择要查询的客户！",{icon:5,title:"系统提醒"});
            return;
        }
        if (data.length>1){
            layer.msg("暂不支持批量查询！",{icon:5,title:"系统提醒"});
            return;
        }
        layer.open({
           type:2,
           title:'客户订单详情',
           content:ctx+"/customer/goOrderPage?customerId="+data[0].id,
           area: ["700px","450px"],
           maxmin:true
        });

    }

    function deleteCustomerData(id){
       layer.confirm("请确定要删除该数据吗？",{icon:3,title:"客户信息管理"},function (index){
            var url=ctx+"/customer/delete";
            $.post(url,{customerId:id},function (res){
                  if(res.code==200){
                      layer.close(index);  //关闭确认框
                      top.layer.msg("数据删除成功！",{icon:6});
                      tableIns.reload();//刷新表格

                  }else {
                      layer.close(index);  //关闭确认框
                      top.layer.msg(res.msg,{icon:5});
                  }

            });


        });
    }

    function openAddOrUpdateCustomerPage(id){
        var title="客户信息添加窗口"
        var url=ctx+"/customer/goSaveOrUpdatePage";
        if(id!=null){
            title="客户信息更新窗口";
            url+="?customerId="+id;
        }
        layer.open({
            type: 2,
            title:title,
            content:url,
            area:["650px","500px"],
            maxmin:true
        });
    }

    // // 头工具栏事件
    // table.on('toolbar(customers)',function (obj) {
    //     switch (obj.event) {
    //         case "add":
    //             openAddOrUpdateCustomerDialog();
    //             break;
    //         case "order":
    //             openOrderInfoDialog(table.checkStatus(obj.config.id).data);
    //             break;
    //     }
    // });
    //
    // table.on('tool(customers)',function (obj) {
    //     var layEvent =obj.event;
    //     if(layEvent === "edit"){
    //         openAddOrUpdateCustomerDialog(obj.data.id);
    //     }else if(layEvent === "del"){
    //         layer.confirm("确认删除当前记录?",{icon: 3, title: "客户管理"},function (index) {
    //             $.post(ctx+"/customer/delete",{id:obj.data.id},function (data) {
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
    // function openAddOrUpdateCustomerDialog(id) {
    //     var title="客户管理-客户添加";
    //     var url=ctx+"/customer/addOrUpdateCustomerPage";
    //     if(id){
    //         title="客户管理-客户更新";
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
    // function openOrderInfoDialog(data) {
    //     if(data.length==0){
    //         layer.msg("请选择查看订单对应客户!");
    //         return;
    //     }
    //     if(data.length>1){
    //         layer.msg("暂不支持批量查看!");
    //         return;
    //     }
    //     layui.layer.open({
    //         title:"客户管理-订单信息查看",
    //         type:2,
    //         area:["700px","500px"],
    //         maxmin:true,
    //         content:ctx+"/customer/orderInfoPage?cid="+data[0].id
    //     })
    //
    // }


});
