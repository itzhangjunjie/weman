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
			<a href="javascript:void(0)" class="b_fr" onclick="parent.addTab1('主题添加','/b2b2cbak/apiAdmin/AdminProductAction_goNewTheme.do')">新建</a>
		</span>
		<span style="float: right;height:28px;"> 
			<input id="searchKeyword" class="mr5" type="text" value="" size="30"	placeholder="请输入模糊关键字" name="searchKeyWord"> 
			<a href="javascript:void(0)" class="b_fr" onclick="searchGoods()">搜索</a>
		</span>
	</div>
	<div style="background: #d7d7d7 none repeat scroll 0 0;margin-top:10px;">
		<div style="width:auto;font-size: 12px; border-bottom: 1px solid #ccc;border-top: 1px solid #ccc;cursor: default;">
			<table style="width:100%;font-size: 12px; " cellspacing="0" cellpadding="0" border="0">
				<tr class="datagrid-header"><td style="border-left: 1px solid #ccc;">主题标题</td><td>主题图片</td><td>主题商品数</td><td>主题赞数</td><td>主题分类</td><td>创建时间</td><td>删除</td></tr>
<!-- 				<td>修改</td> -->
				<s:iterator value="#request.page.result" var="themeObj">
					<tr class="divTr" height="100px" ><td style="border-left: 1px solid #ccc;">${themeObj.theme.title } </td><td><img src="/b2b2cbak/statics/${themeObj.theme.image }" width="80px" height="80px"/></td>
					<td>${themeObj.theme.product_count }</td><td><input type="text" style="width:50px;" value="${themeObj.theme.love_count }" id="themeLoveCount" /><button onclick="updateLoveCount(${themeObj.theme.id })">修改</button></td>
					<td>
						<s:if test="#themeObj.theme.themetagList.size()>0">
							<s:iterator value="#themeObj.theme.themetagList" var="tagMap">
								<div>
									<select class="pcategory" onchange="tagSel(this)">
										<s:iterator value="#request.parentTags" var="ptag">
													<option value="${ptag.id }" <s:if test="#ptag.id == #tagMap.key">selected="selected"</s:if>>${ptag.name }</option>
										</s:iterator>
										<option value="0">未选择</option>
									</select> : 
									<select class="ccategory">
										<s:iterator value="#request.childrenTags" var="ctag">
													<option value="${ctag.id }" name="${ctag.category }" <s:if test="#ctag.id == #tagMap.value">selected="selected"</s:if>>${ctag.name }</option>
										</s:iterator>
										<option name="0">未选择</option>
									</select>
									<button onclick="updateCategroy(this,${themeObj.theme.id})">确定修改</button>
								</div>
							</s:iterator>
						</s:if><s:else>
							<div>
								<select class="pcategory" onchange="tagSel(this)">
									<s:iterator value="#request.parentTags" var="ptag">
												<option value="${ptag.id }">${ptag.name }</option>
									</s:iterator>
									<option value="0" selected="selected">未选择</option>
								</select> : 
								<select class="ccategory">
									<s:iterator value="#request.childrenTags" var="ctag">
												<option value="${ctag.id }" name="${ctag.category }">${ctag.name }</option>
									</s:iterator>
									<option name="0" selected="selected">未选择</option>
								</select>
								<button onclick="updateCategroy(this,${themeObj.theme.id})">确定修改</button>
							</div>	
						</s:else>
					</td>
					<td>
						<cc:dateFormat format="yyyy-MM-dd HH:mm" time="${themeObj.theme.create_time }"/>
					</td>
<!-- 					<td style="text-align: center;"> -->
<%-- 						<a class="updateThemeA" onclick='parent.addTab1("主题修改","/b2b2cbak/apiAdmin/AdminProductAction_goThemeDetails.do?themeId=${themeObj.theme.id}");' href="javascript:void(0);" title="修改" ></a> --%>
<!-- 					</td> -->
					<td>
						<a style="text-decoration: none;" class="b_fr" onclick="updateTheme(this,${themeObj.theme.id})" href="javascript:void(0);">删除</a>
					</td></tr>
				</s:iterator>
			</table>
		</div>
	</div>
</div>
<div class="pagelist">
	<div style="line-height:46px;height:46px;width:150px;float:left"><span>共 ${page.totalPageCount} 页/${page.totalCount}条记录 </span></div>
	<div id="adminUserManagePagination" class="pagination" style="float:left;margin-top:15px"></div>
</div>
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
	function updateLoveCount(id){
		var loveCount = $('#themeLoveCount').val();
		$.ajax({
			type:'POST',
			url:'/b2b2cbak/apiAdmin/AdminProductAction_updateThemeStatus.do',
			data:{
				'themeId':id,
				'loveCount':loveCount
			},
			dataType:'json',
		    success: function(msg){
		    	if(msg.result=='yes'){
		    		alert('修改成功！');
		    	}else{
		    		alert('系统错误!');
		    	}
		    }
		})
	}
	function updateTheme(tt,tid){
		var $this = $(tt);
		$.ajax({
			type:'POST',
			url:'/b2b2cbak/apiAdmin/AdminProductAction_deleteTheme.do',
			data:{
				'themeId':tid
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
	function tagSel(tt){
		var val=$(tt).val();
		$(tt).parent('div').children('.ccategory').find('option').hide();
		$(tt).parent('div').children('.ccategory').find('[name="'+val+'"]').show();
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
	function updateCategroy(themeid){
		var pcategory = $('#pcategory').val();
		var ccategory = $('#ccategory').val();
	}
	function updateCategroy(tt,tid){
		var keyId = $(tt).parent('div').children('.pcategory').val();
		var valueId = $(tt).parent('div').children('.ccategory').val();
		if(keyId==0||valueId==0){
			alert('选择错误!');
		}
		$.ajax({
			type:'POST',
			url:'/b2b2cbak/apiAdmin/AdminProductAction_updateThemeTag.do',
			data:{
				'themeId':tid,
				'keyId':keyId,
				'valueId':valueId
			},
			dataType:'json',
		    success: function(msg){
		    	if(msg.result=='yes'){
		    		alert('修改成功！');
		    	}else{
		    		alert('系统错误!');
		    	}
		    }
		})
	}
</script>