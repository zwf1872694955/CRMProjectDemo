layui.use(['element', 'layer', 'layuimini','jquery','jquery_cookie'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        $ = layui.jquery_cookie($);

    // 菜单初始化
    $('#layuiminiHomeTabIframe').html('<iframe width="100%" height="100%" frameborder="0"  src="welcome"></iframe>')
    layuimini.initTab();
     //退出登录
    $('#logout').click(function () {
        layer.confirm('你确认要退出登录？', {icon: 3, title:'系统提示'}, function(index){
           //清空cookie和跳转到登录页面
            $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
            $.removeCookie("truename",{domain:"localhost",path:"/crm"});
            $.removeCookie("username",{domain:"localhost",path:"/crm"});
            window.parent.location.href=ctx+"/index";
        });
    });

});