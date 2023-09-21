layui.use(['table','layer'],function() {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    $(function () {  //元素预加载后加载插件
        loadModuleInfo();
    });

    var zTreeObj;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        //开启选中模式
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        //获取所有的资源选中的回调函数
        callback: {
            onCheck: zTreeOnCheck
        }
    };

//加载显示roleId拥有的资源被选中
    function loadModuleInfo() {
        var url = ctx + "/module/list";
        //获取所有的
        $.post(url, {roleId: $('[name="roleId"]').val()}, function (data) {
            zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
        });
    }

    function zTreeOnCheck(event, treeId, treeNode) {
        // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
        //获取所有被选中的节点集合
        var nodeIds = zTreeObj.getCheckedNodes(true);
        //把选中的节点ModuleId和roleId进行参数绑定发送ajax请求
        var moduleIds = "moduleIds=";
        for (var i = 0; i < nodeIds.length; i++) {
            if (i == nodeIds.length - 1) {
                moduleIds += nodeIds[i].id;
            } else {
                moduleIds += nodeIds[i].id + "&moduleIds=";
            }
        }
        var params = moduleIds + "&roleId=" + $('[name="roleId"]').val();
        //发送ajax请求
        var url = ctx + "/role/grantModule";
        // console.log(params)
        $.post(url, params, function (res) {
            if (res.code == 200) {
                layer.msg(res.msg, {icon: 6});
            } else {
                layer.msg(res.msg, {icon: 5});
            }

        });

    }
});
