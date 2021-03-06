package com.enation.app.shop.component.groupbuy.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.shop.component.groupbuy.service.IGroupBuyManager;
import com.enation.framework.context.webcontext.ThreadContextHolder;
import com.enation.framework.database.Page;
import com.enation.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 团购列表标签
 * @author kingapex
 *2015-1-12下午6:05:34
 */
@Component
@Scope("prototype")
public class GroupBuyListTag extends BaseFreeMarkerTag {
	
	private IGroupBuyManager groupBuyManager;
	/**
	 * 获取团购商品数据标签
	 * @param catid 团购分类Id
	 * @param minprice 最小价格
	 * @param maxprice 最大价格
	 * @param sort_key 排序类型
	 * @param sort_type 团购类型
	 * @param area_id 地区Id
	 * @param page 团购商品分页列表
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		Map result=new HashMap();
		
		String pageNo = request.getParameter("page");
		pageNo = (pageNo == null || pageNo.equals("")) ? "1" : pageNo;
		int pageSize = 12;
	
		
		pageNo = (pageNo == null || pageNo.equals("")) ? "1" : pageNo;
		String catid_str = request.getParameter("catid")==""?"0":request.getParameter("catid");	
		String minprice_str=request.getParameter("minprice")==""?"0":request.getParameter("minprice");
		String maxprice_str=request.getParameter("maxprice")==""?"0":request.getParameter("maxprice");
		String sort_key_str=request.getParameter("sort_key")==""?"0":request.getParameter("sort_key");		//排序方式
		String sort_type_str=request.getParameter("sort_type")==""?"1":request.getParameter("sort_type");	//团购类型 0.往期团购.1.当前团购.2.之后的团购
		String area_id_str=request.getParameter("area_id")==""?"0":request.getParameter("area_id");
		
		Integer catid = Integer.valueOf(catid_str);
		Integer sort_key = Integer.valueOf(sort_key_str);
		Integer sort_type = Integer.valueOf(sort_type_str);	
		Double minprice = Double.valueOf(minprice_str);
		Double maxprice = Double.valueOf(maxprice_str);
		Integer area_id=Integer.valueOf(area_id_str);
		
		
		Page page=this.groupBuyManager.search(this.getPage(), this.getPageSize(), catid, minprice, maxprice, sort_key, sort_type,area_id, area_id);
		
		result.put("page", page);
		result.put("catid", catid);
		result.put("sort_key", sort_key);
		result.put("sort_type", sort_type);
		result.put("minprice", minprice);
		result.put("maxprice", maxprice);
		result.put("area_id", area_id);
		result.put("totalCount", page.getTotalCount());
		result.put("pageSize", pageSize);
		result.put("pageTotalCount", page.getTotalPageCount());
		return result;
	}
	
	public IGroupBuyManager getGroupBuyManager() {
		return groupBuyManager;
	}
	public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
		this.groupBuyManager = groupBuyManager;
	}
	
	

}
