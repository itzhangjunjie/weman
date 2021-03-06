<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="cc" uri="/tcardztaglib" %>
<script src="../adminthemes/new/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../adminthemes/new/js/jquery.pagination.js"></script>
<link href="../adminthemes/new/css/myPagination.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body{
	font-size: 12px;
}
.mr5 {
    display: block;
    margin-top: 0;
    border: 1px solid #d7d7d7;
    border-radius: 3px;
    display: inline-block;
    line-height: 14px;
    padding: 7px 0 7px 5px;
    width: 300px;
    outline: medium none;
}
.b_fr {
    text-decoration: none;
    display: block;
    margin-top: 0;
    background-color: #f7f7f7;
    border: 1px solid #d1d1d1;
    border-radius: 3px;
    color: #666666;
    cursor: pointer;
    display: inline-block;
    height: 28px;
    line-height: 28px;
    padding: 0 20px;
}
.searchAdvanced {
    background: #fffbf4 none repeat scroll 0 0;
    border: 1px solid #d1d1d1;
    box-sizing: border-box;
    color: #7c8389;
    float: left;
    margin-top: 10px;
    padding: 10px 0 5px;
    width: 100%;
}
table tr td {
    padding: 6px;
    white-space: normal;
    word-break: break-all;
}
.datagrid-header {
    height: 50px;
}
.datagrid-header td {
    background: #f7f7f7 none repeat scroll 0 0;
    border-bottom: 1px solid #ccc;
    border-right: 1px solid #ccc;
    border-top: 1px dotted #fff;
    color: #333333;
    font-family: "微软雅黑","宋体";
    font-size: 12px;
    font-weight: bold;
    text-align: center;
}
.divTr td{
	border-bottom: 1px solid #ccc;
    border-right: 1px solid #ccc;
    text-align: center;
}
tr:hover td .updateThemeA{
	background-position: -26px 0px;
	cursor: pointer;
}
.divTr{
	background-color: white;
}
.divTr:hover{
	background: #d0e5f5 none repeat scroll 0 0;
	cursor: default;
}
.updateThemeA{
    background-image: url("/b2b2cbak/adminthemes/new/images/icon21.png");
    background-position: -26px -29px;
    display: inline-block;
    float: none;
    height: 23px;
    margin: 0 5px;
    text-indent: 0;
    width: 23px;
    text-decoration: none;
}
</style>

<div class='buttonArea'>
	<div style="height:30px;margin-top:4px;">
		<span style="float:left;height:28px;">
			<a href="javascript:void(0)" class="b_fr" onclick="parent.addTab1('主题标签添加','/b2b2cbak/apiAdmin/AdminProductAction_saveThemeTag.do')">新建</a>
		</span>
		<span style="float: right;height:28px;display:none;"> 
			<input id="searchKeyword" class="mr5" type="text" value="" size="30"	placeholder="请输入模糊关键字" name="searchKeyWord"> 
			<a href="javascript:void(0)" class="b_fr" onclick="searchGoods()">搜索</a>
		</span>
	</div>
	<div style="background: #d7d7d7 none repeat scroll 0 0;margin-top:10px;">
		<div style="width:auto;font-size: 12px; border-bottom: 1px solid #ccc;border-top: 1px solid #ccc;cursor: default;">
			<table style="width:100%;font-size: 12px; " cellspacing="0" cellpadding="0" border="0">
				<tr class="datagrid-header"><td style="border-left: 1px solid #ccc;">标签类别</td><td>标签标题</td><td>主题标签图片</td><td>创建时间</td><td>删除</td></tr>
				<s:iterator value="#request.themeTagList" var="themeTagObj">
					<tr class="divTr" height="100px" ><td style="border-left: 1px solid #ccc;">${themeTagObj.name } </td>
					<td>父标签</td>
					<td><img src="/b2b2cbak/statics/${themeTagObj.image }" width="80px" height="80px"/></td>
					<td>
						<cc:dateFormat format="yyyy-MM-dd HH:mm" time="${themeTagObj.create_time }"/>
					</td>
					<td>
						<a style="text-decoration: none;" class="b_fr" onclick="updateTheme(this,${themeTagObj.id})" href="javascript:void(0);">删除</a>
					</td></tr>
					<s:iterator value="#request.themeTagObj.childrenThemeTag" var="ctobj">
						<tr class="divTr" height="100px" ><td style="border-left: 1px solid #ccc;">${themeTagObj.name }</td>
						<td>${ctobj.name }</td>
						<td><img src="/b2b2cbak/statics/${ctobj.image }" width="80px" height="80px"/></td>
						<td>
							<cc:dateFormat format="yyyy-MM-dd HH:mm" time="${ctobj.create_time }"/>
						</td>
						<td>
							<a style="text-decoration: none;" class="b_fr" onclick="updateThemeTag(this,${ctobj.id})" href="javascript:void(0);">修改</a>||
							<a style="text-decoration: none;" class="b_fr" onclick="updateTheme(this,${ctobj.id})" href="javascript:void(0);">删除</a>
						</td></tr>
					</s:iterator>
				</s:iterator>
			</table>
		</div>
	</div>
</div>
<!-- <div class="pagelist"> -->
<%-- 	<div style="line-height:46px;height:46px;width:150px;float:left"><span>共 ${page.totalPageCount} 页/${page.totalCount}条记录 </span></div> --%>
<!-- 	<div id="adminUserManagePagination" class="pagination" style="float:left;margin-top:15px"></div> -->
<!-- </div> -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#adminUserManagePagination").pagination('${page.totalCount}', {  
            'items_per_page'      : 10,  
            'num_display_entries' : 5, 
            'ellipse_text'        : "...",
            'num_edge_entries'    : 2,  
            'prev_text'           : "上一页",  
            'next_text'           : "下一页",  
            'current_page'        :'${page.currentPageNo-1}',
            'callback'            : function(page_id,jq){
//            		var type = "${type}";
//            		var page = parseInt(page_id)+1;
//            		$("#searchForm").find("input[name='page.pages']").val(page);
//            		$("#searchForm").submit();
            } 
        });
	});
	function updateThemeTag(tt,tid){
		parent.addTab1("修改主题标签","/b2b2cbak/apiAdmin/AdminProductAction_fetchThemeTag.do?ttid="+tid);
	}
	function updateTheme(tt,tid){
		if(confirm("确定要删除吗？")){
			
			var $this = $(tt);
			$.ajax({
				type:'POST',
				url:'/b2b2cbak/apiAdmin/AdminProductAction_deleThemeTag.do',
				data:{
					'ttId':tid
				},
				dataType:'json',
			    success: function(msg){
			    	if(msg.result=='yes'){
			    		$this.parent().parent().remove();
			    	}else{
			    		alert('系统错误!');
			    	}
			    }
			})
		}
		
	}
	function searchGoods(){
		var kw = $('#searchKeyword').val();
		if(kw==null||kw==''){
			return;
		}
		var url = '/b2b2cbak/apiAdmin/AdminProductAction_getThemeList.do?page=1&keywords='+kw;
		url = encodeURI(encodeURI(url));
		window.location.href= url;
	}
</script>