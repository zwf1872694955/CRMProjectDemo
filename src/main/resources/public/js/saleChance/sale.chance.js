layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //用户列表展示
    var  tableIns = table.render({
        elem: '#saleChanceList',
        url : ctx+'/sale_chance/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "saleChanceListTable",
        cols : [[
            {type: 'checkbox', fixed:'center'},
            {field: 'id', title:'编号',fixed:'left'},
            {field: 'chanceSource', title: '机会来源',align:'center'},
            {field: 'customerName', title: '客户名称',  align:'center'},
            {field: 'cgjl', title: '成功几率', align:'center'},
            {field: 'overview', title: '概要', align:'center'},
            {field: 'linkMan', title: '联系人',  align:'center'},
            {field: 'linkPhone', title: '联系电话', align:'center'},
            {field: 'description', title: '描述', align:'center'},
            {field: 'createMan', title: '创建人', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'uname', title: '指派人', align:'center'},
            {field: 'assignTime', title: '分配时间', align:'center'},
            {field: 'state', title: '分配状态', align:'center',templet:function(d){
                    return formatterState(d.state);
                }},
            {field: 'devResult', title: '开发状态', align:'center',templet:function (d) {
                    return formatterDevResult(d.devResult);
                }},
            {title: '操作', templet:'#saleChanceListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });

    function formatterState(state){
        if(state==0){
            return "<div style='color:yellow '>未分配</div>";
        }else if(state==1){
            return "<div style='color: green'>已分配</div>";
        }else{
            return "<div style='color: red'>未知</div>";
        }
    }

    function formatterDevResult(value){
        /**
         * 0-未开发
         * 1-开发中
         * 2-开发成功
         * 3-开发失败
         */
        if(value==0){
            return "<div style='color: yellow'>未开发</div>";
        }else if(value==1){
            return "<div style='color: #00FF00;'>开发中</div>";
        }else if(value==2){
            return "<div style='color: #00B83F'>开发成功</div>";
        }else if(value==3){
            return "<div style='color: red'>开发失败</div>";
        }else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }
      $(".search_btn").click(function () {
          //这里以搜索为例
          tableIns.reload({
              where: {
                  customName:$("input[name='customerName']").val(),
                  createMan:$("input[name='createMan']").val() ,
                  state:$("#state").val()
              }
              ,page: {
                  curr: 1 //重新从第 1 页开始
              }
          });
           // console.log($("#state").val());
      });

    //打开弹出层窗口
    table.on('toolbar(saleChances)',function (data){
        if(data.event=='add'){
           //添加操作
            openSaleChanceWindow()
        }else if(data.event=='del'){
            //删除操作
            layer.confirm('你确定要删除吗？',{icon:3,title:'营销机会管理'},function (index) {
                var checkStatus = table.checkStatus('saleChanceListTable');
                var SaleData=checkStatus.data;
                deleteSaleChanceMake(SaleData);
                layer.close(index); //关闭窗口
            });


        }

    });

    function deleteSaleChanceMake(saleData){
        if(saleData.length==0||saleData==null){
             layer.msg("请选择要删除的数据！",{icon:5});
             return;
        }
           var str="ids=";
        for (var i=0;i<saleData.length;i++){
            if(i==saleData.length-1){
                str+=saleData[i].id;
            }else {
                str+=saleData[i].id+"&ids=";
            }
        }
        $.ajax({
            url:ctx+"/sale_chance/delete",
            type:'post',
            data: str,
            success:function (res){
                if(res.code==200){
                    layer.msg(res.msg,{icon:6});
                    tableIns.reload();  //重新加载数据！
                }else {
                    layer.msg(res.msg,{icon:5});
                }
            }
        })

    }

    function openSaleChanceWindow(saleChanceId){
        var title="<h3>营销机会添加窗口</h3>";
        var url=ctx+"/sale_chance/saleChancePage"
        if(saleChanceId!=null && saleChanceId!=''){
            title="<h3>营销机会更新窗口</h3>";
            url+="?saleChanceId="+saleChanceId;
        }

        layui.layer.open({
            type: 2,
            content: url,  //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            maxmin:true,
            title: title,
            area: ['500px', '500px']
        });
    }

    table.on('tool(saleChances)',function (data){
        if(data.event=="edit"){
            openSaleChanceWindow(data.data.id);
        }else if(data.event=="del"){
            layer.confirm('请确定要删除吗？', {icon: 3, title:'营销机会管理'}, function(index){
                $.ajax({
                    url:ctx+"/sale_chance/delete",
                    type:'post',
                    data:{
                        ids:data.data.id
                    },
                    success:function (res){
                        if(res.code==200){
                           layer.msg(res.msg,{icon:6});
                           tableIns.reload();
                        }else {
                            layer.msg(res.msg,{icon:5});
                        }
                    }
                });
                //关闭窗口
                layer.close(index);
            });

        }

});




});
