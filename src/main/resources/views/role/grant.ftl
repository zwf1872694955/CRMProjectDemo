<html>
<head>
	<link rel="stylesheet" href="${ctx}/js/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="icon" href="${ctx}/images/favicon.ico">
	<link rel="stylesheet" href="${ctx}/lib/layui-v2.5.5/css/layui.css" media="all">
	<link rel="stylesheet" href="${ctx}/css/layuimini.css" media="all">
	<link rel="stylesheet" href="${ctx}/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
	<link rel="stylesheet" href="${ctx}/css/formSelects-v4.css" media="all">
	<link rel="stylesheet" href="${ctx}/css/public.css" media="all">
	<script src="${ctx}/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
	<script src="${ctx}/js/lay-config.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/lib/jquery-3.4.1/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctx}/js/zTree_v3/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript">
		var ctx="${ctx}";
	</script>
</head>
<body>
<input type="hidden" name="roleId" value="${(roleId)!}"/>
<div id="test1" class="ztree"></div>

<script type="text/javascript" src="${ctx}/js/role/grant.js"></script>
</body>
</html>