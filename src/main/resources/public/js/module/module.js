//先导入treetable 树表格组件
layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
        var tableIns=treeTable.render({
        treeColIndex: 1,  //图标在第一列显示
        treeSpid: -1, //最上级的父级Id
        treeIdName: 'id', //id字段属性名
        treePidName: 'parentId', //父节点ID字段属性名
        elem: '#munu-table', //表格容器id值
        url: ctx+'/module/data', //请求数据路径
        toolbar: "#toolbarDemo", //头部工具栏Id值
        treeDefaultClose:true,  //默认折叠
        page: true, //是否分页
        cols: [[   //列名属性映射
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if(d.grade==1){
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            //行内工具栏
            {templet: '#auth-state', width: 180, align: 'center', title: '操作'}
        ]],
        //数据渲染完的回调
        done: function () {
            //关闭加载
            layer.closeAll('loading');
        }
    });

     table.on("toolbar(munu-table)",function (data){
            if(data.event=="expand"){
                //展开所有的分支
                treeTable.expandAll("#munu-table");
                return;
            }else if(data.event=="fold"){
                //关闭所有的分支
                treeTable.foldAll("#munu-table");
                return;
            }else if(data.event=="add"){
                //目录的节点层级就是本身  传入父级ID和grade等级0
                openAddModulePage(-1,0);
            }
     });
       //添加子项
      table.on("tool(munu-table)",function (data){
         if(data.event=="add"){
             //当前行内记录的主键就是父节点
             openAddModulePage(data.data.id,data.data.grade+1)
         }else if(data.event=="edit"){
             openUpdateModulePage(data.data.id);
         }else if (data.event=="del"){
              openDelModuleMake(data.data.id);
         }
      });

      function openDelModuleMake(id){
          if(id==null){
              layer.msg("系统异常！",{icon:5});
              return;
          }
          layer.confirm("请确定要删除该记录吗？",{icon:3,title:"菜单模块管理"},function (index){
              var url=ctx+"/module/delete";
              $.post(url,{moduleId:id},function (res){
                 if(res.code==200){
                     parent.layer.msg("数据删除成功！",{icon:6});
                     layer.close(index);
                     parent.location.reload(); //刷新父窗口数据
                 } else {
                     parent.layer.msg(res.msg,{icon:5});
                    layer.close(index);
                 }
              });

          });
      }

   function  openAddModulePage(parentId,grade){
       if(grade>2){
           layer.msg("暂不支持添加四级菜单项！",{icon:5});
           return;
       }
       var url=ctx+"/module/goAddModulePage?grade="+grade+"&parentId="+parentId;
       layer.open({
           type: 2,  //type必须是数值型
           title:'添加层级菜单窗口',
           content: url,
           area:['500px','450px'],
           maxmin:true
       });

   }

   function openUpdateModulePage(id){
       var url=ctx+"/module/goUpdateModulePage?moduleId="+id;
       layer.open({
           type: 2,  //type必须是数值型
           title:'更新层级菜单窗口',
           content: url,
           area:['500px','450px'],
           maxmin:true
       });
   }
    // //监听工具条
    // table.on('tool(munu-table)', function (obj) {
    //     var data = obj.data;
    //     var layEvent = obj.event;
    //     if (layEvent === 'add') {
    //         if(data.grade==2){
    //             layer.msg("暂不支持四级菜单添加操作!");
    //             return;
    //         }
    //         openAddModuleDialog(data.grade+1,data.id);
    //     } else if (layEvent === 'edit') {
    //         // 记录修改
    //         openUpdateModuleDialog(data.id);
    //     } else if (layEvent === 'del') {
    //         layer.confirm('确定删除当前菜单？', {icon: 3, title: "菜单管理"}, function (index) {
    //             $.post(ctx+"/module/delete",{id:data.id},function (data) {
    //                 if(data.code==200){
    //                     layer.msg("操作成功！");
    //                     window.location.reload();
    //                 }else{
    //                     layer.msg(data.msg, {icon: 5});
    //                 }
    //             });
    //         })
    //     }
    // });
    //
    // table.on('toolbar(munu-table)', function(obj){
    //     switch(obj.event){
    //         case "expand":
    //             treeTable.expandAll('#munu-table');
    //             break;
    //         case "fold":
    //             treeTable.foldAll('#munu-table');
    //             break;
    //         case "add":
    //             openAddModuleDialog(0,-1);
    //             break;
    //     };
    // });
    //
    //
    // // 打开添加菜单对话框
    // function openAddModuleDialog(grade,parentId){
    //     var grade=grade;
    //     var url  =  ctx+"/module/addModulePage?grade="+grade+"&parentId="+parentId;
    //     var title="菜单添加";
    //     layui.layer.open({
    //         title : title,
    //         type : 2,
    //         area:["700px","450px"],
    //         maxmin:true,
    //         content : url
    //     });
    // }
    //
    // function openUpdateModuleDialog(id){
    //     var url  =  ctx+"/module/updateModulePage?id="+id;
    //     var title="菜单更新";
    //     layui.layer.open({
    //         title : title,
    //         type : 2,
    //         area:["700px","450px"],
    //         maxmin:true,
    //         content : url
    //     });
    // }
});