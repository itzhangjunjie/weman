package com.enation.app.api.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;

import com.enation.app.api.dto.ThemeProduct;
import com.enation.app.api.model.PhoneBanner;
import com.enation.app.api.model.Theme;
import com.enation.app.api.model.ThemeContent;
import com.enation.app.api.model.ThemeTag;
import com.enation.app.api.service.BannerService;
import com.enation.app.api.service.ProductService;
import com.enation.app.shop.component.gallery.model.GoodsGallery;
import com.enation.app.shop.core.model.Goods;
import com.enation.framework.database.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
public class ProductAction extends BaseAction{

	private static final long serialVersionUID = -4833677871015547593L;
	
	
	@Resource
	private ProductService productService;
	
	
	@Resource
	private BannerService bannerService;
	
	
	
	
	/**
	 * 获取发现列表
	 */
	public void getFindList(){
		try {
			List<ThemeTag> themeTags = productService.getThemeTagList();
			JSONArray typeJa = new JSONArray();
			for(ThemeTag themetag:themeTags){
				if("身型".equals(themetag.getName())||"类型".equals(themetag.getName())){
					JSONObject typeObj = new JSONObject();
					typeObj.put("typeId", String.valueOf(themetag.getId()));
					typeObj.put("typeName", themetag.getName());
					JSONArray childrenTag = new JSONArray();
					for(ThemeTag ctag:themetag.getChildrenThemeTag()){
						JSONObject childrenTagObj = new JSONObject();
						childrenTagObj.put("id", String.valueOf(ctag.getId()));
						childrenTagObj.put("name", ctag.getName());
						if("熊".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findXiongDefault.png"));
						}else if("猴".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findHouDefault.png"));
						}else if("狒狒".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findFeiFeiDefault.png"));
						}else if("通用".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findAllDefault.png"));
						}else if("情趣".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findQingQuDefault.png"));
						}else if("护肤".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findHuFuDefault.png"));
						}else if("服装".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findFuZhuangDefault.png"));
						}else if("配饰".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findPeiShiDefault.png"));
						}else if("生活".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findShengHuoDefault.png"));
						}else{
							childrenTagObj.put("image", this.getImageUrl("123.png"));
						}
						if(ctag.getName().indexOf("双十一")<0){
							childrenTag.add(childrenTagObj);
						}
					}
					typeObj.put("typeData", childrenTag);
					if("身型".equals(themetag.getName())){
						typeObj.put("typeStyle", "1");
						typeJa.add(0,typeObj);
					}else if("类型".equals(themetag.getName())){
						typeObj.put("typeStyle", "2");
						typeJa.add(typeObj);
					}
					
				}
			}
			List<PhoneBanner> phoneBanners = bannerService.getCurrentBanners("发现banner");
			if(phoneBanners!=null&&phoneBanners.size()>0){
				JSONArray bannerJarray = new JSONArray();
				for(PhoneBanner pb:phoneBanners){
					JSONObject bannerObj = new JSONObject();
					bannerObj.put("bannerType", String.valueOf(pb.getType()));
					bannerObj.put("bannerId", String.valueOf(pb.getId()));
					bannerObj.put("bannerImage", this.getImageUrl(pb.getImage()));
					bannerObj.put("bannerData", pb.getDetails());
					if(pb.getThemeContentStyle()!=null&&!pb.getThemeContentStyle().equals("0")){
						bannerObj.put("contentStyle", pb.getThemeContentStyle());
					}
					bannerObj.put("bannerData", pb.getDetails());
					bannerJarray.add(bannerObj);
				}
				jsonObject.put("bannerList", bannerJarray);
			}
			jsonObject.put("data", typeJa);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取发现列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.print(jsonObject);
			out.close();
		}
	}
	
	/**
	 * 点击banner进入主题列表接口
	 */
	public void getBannerThemeList(){
		try {
			int pageNo = Integer.parseInt(paramObject.getString("page"));
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("bannerStatus", paramObject.getString("bannerId"));
			Page page =	productService.getThemeProducts(pageNo, 10 , maps);
			List<ThemeProduct> tps = (List<ThemeProduct>) page.getResult();
			JSONArray jsonArray = new JSONArray();
			for(ThemeProduct tp:tps){
				JSONObject theme = new JSONObject();
				theme.put("themeId", String.valueOf(tp.getTheme().getId()));
				theme.put("themeImage", this.getImageUrl(tp.getTheme().getMinorImage()));
				theme.put("contentStyle", tp.getTheme().getContentStyle());
//				theme.put("tags", tp.getTheme().getTagsImage());
				theme.put("themeTitle", tp.getTheme().getTitle());
				theme.put("themeDetails", tp.getTheme().getDetails());
				jsonArray.add(theme);
			}
			jsonObject.put("title", "双11专题");
			jsonObject.put("themeData", jsonArray);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取发现列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.print(jsonObject);
			out.close();
		}
	}
	/**
	 * 点击发现列表进入主题列表接口
	 */
	public void getFindThemeList(){
		try {
			String typeId = paramObject.getString("typeId");
			int pageNo = Integer.parseInt(paramObject.getString("page"));
			Map<String, Object> maps = new HashMap<String, Object>();
			if (typeId != null && !"0".equals(typeId)) {
				maps.put("typeId", typeId);
			}
			maps.put("findStatus", "1");
			Page page =	productService.getThemeProducts(pageNo, 10 , maps);
			List<ThemeProduct> tps = (List<ThemeProduct>) page.getResult();
			JSONArray jsonArray = new JSONArray();
			for(ThemeProduct tp:tps){
				JSONObject theme = new JSONObject();
				theme.put("themeId", String.valueOf(tp.getTheme().getId()));
				theme.put("themeImage", this.getImageUrl(tp.getTheme().getMinorImage()));
//				theme.put("tags", tp.getTheme().getTagsImage());
				theme.put("contentStyle", tp.getTheme().getContentStyle());
				theme.put("themeTitle", tp.getTheme().getTitle());
				theme.put("themeDetails", tp.getTheme().getDetails());
				jsonArray.add(theme);
			}
			ThemeTag ctag = productService.getThemeTagById(typeId);
			if(ctag!=null){
				if("熊".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeXiongDefault.png"));
				}else if("猴".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeHouDefault.png"));
				}else if("狒狒".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeFeiFeiDefault.png"));
				}else if("通用".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeAllDefault.png"));
				}else if("服装".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeFuZhuangDefault.png"));
				}else if("护肤".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeHuFuDefault.png"));
				}else if("配饰".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemePeiShiDefault.png"));
				}else if("生活".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeShengHuoDefault.png"));
				}else if("情趣".equals(ctag.getName())){
					jsonObject.put("image", this.getImageUrl("attachment/allDefaultImage/findThemeQingQuDefault.png"));
				}else{
					jsonObject.put("image", this.getImageUrl("123.png"));
				}
			}else{
				jsonObject.put("image", this.getImageUrl("123.png"));
			}
			jsonObject.put("themeData", jsonArray);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取发现列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.print(jsonObject);
			out.close();
		}
	}
	
	/**
	 * 获取商品列表
	 */
	public void getProductList(){
		try {
			String pageNo = paramObject.getString("page");
			String namekeyword = paramObject.has("namekeyword")?paramObject.getString("namekeyword"):null;
			String cat_id = paramObject.has("cat_id")?paramObject.getString("cat_id"):null;
			String search_type = paramObject.has("search_type")?paramObject.getString("search_type"):"0";
			pageNo = (pageNo == null || pageNo.equals("")) ? "1" : pageNo;
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("namekeyword", namekeyword);
			map.put("cat_id", cat_id);
			map.put("search_type", search_type);
			Page page = productService.getProductList(Integer.parseInt(pageNo), 10, map);
			jsonObject.put("totalCount", page.getTotalPageCount());
			JSONArray resJa = new JSONArray();
			JSONArray ja = JSONArray.fromObject(page.getResult());
			for(int i=0;i<ja.size();i++){
				JSONObject resObj = new JSONObject();
				JSONObject obj = (JSONObject) ja.get(i);
				resObj.put("pname", obj.get("name"));
				resJa.add(resObj);
			}
			jsonObject.put("productList", resJa);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取商品列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.print(jsonObject);
			out.close();
		}
	}

	/**
	 * 获取商品详情
	 */
	public void getProductDetails(){
		try {
			int productId = Integer.parseInt((String)paramObject.get("productId"));
			jsonObject = productService.getProductDetails(productId,jsonObject);
			String ggs = (String) jsonObject.get("productImages");
			if(ggs!=null){
				String productGallery = "";
				for(String gg:ggs.split(",")){
					productGallery = productGallery+this.getImageUrl(gg)+",";
				}
				productGallery = productGallery.substring(0, productGallery.lastIndexOf(","));
				jsonObject.put("productImages", productGallery);
			}
			jsonObject.put("productImage", this.getImageUrl((String)jsonObject.get("productImage")));
			jsonObject.put("productBrandImage", this.getImageUrl((String)jsonObject.get("productBrandImage")));
			jsonObject.put("productCatImage", this.getImageUrl((String)jsonObject.get("productCatImage")));
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取商品列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.print(jsonObject);
			out.close();
		}
	}

	
	/**
	 * 获取主题详情
	 */
	public void getThemeDetails(){
		try {
			int themeId = Integer.parseInt(paramObject.getString("themeId"));
			String accessToken = paramObject.has("accessToken")?paramObject.getString("accessToken"):null;
			int memberId = this.getMemberId(accessToken);
			Theme theme = productService.getThemeDetails(themeId,memberId);
			jsonObject.put("themeId", String.valueOf(theme.getId()));
			jsonObject.put("themeImage", this.getImageUrl(theme.getMinorImage()));
			jsonObject.put("themeTile", theme.getTitle());
			jsonObject.put("themeDate", theme.getShowDate());
			jsonObject.put("themeDetails", theme.getDetails());
			jsonObject.put("themeDetailsPosition", theme.getDetailsPosition());
			jsonObject.put("themeProductDetailsPosition", theme.getProductPosition());
			jsonObject.put("themeTitleSize", "18");
			//jsonObject.put("contentStyle", theme.getContentStyle());
			jsonObject.put("themeContentSize", "16");
			jsonObject.put("themeFontDistance", "1");//字间距
			jsonObject.put("themeCapableDistance", "8");//行间距
			jsonObject.put("themeCollectCount", theme.getLove_count());
			if(theme.getIsLove()==0){
				jsonObject.put("isLove", "no");
			}else{
				jsonObject.put("isLove", "yes");
			}
			if(theme.getThemeContent()!=null){
				JSONArray contents = new JSONArray();
				for(ThemeContent tc:theme.getThemeContent()){
					JSONObject content = new JSONObject();
					content.put("contentType", tc.getType());
					content.put("contentPosition", String.valueOf(tc.getPostion()));
					if("text".equals(tc.getType())){
						content.put("contentText", tc.getContent());
						content.put("contentFontSize", tc.getFontSize());
						content.put("contentCenter", tc.getCenter());
					}else if("image".equals(tc.getType())){
						content.put("contentImage", this.getImageUrl(tc.getImage()));
						content.put("contentWidth", String.valueOf(tc.getImagewidth()));
						content.put("contentHeight", String.valueOf(tc.getImageheight()));
					}else if("product".equals(tc.getType())){
						content.put("contentProductId", String.valueOf(tc.getGoods_id()));
						content.put("productOrigin", tc.getProductOrigin());
						content.put("contentProductName", tc.getProductName());
						content.put("contentProductImage", this.getImageUrl(tc.getProductImage()));
						content.put("contentProductCategoryImage", this.getImageUrl(tc.getProductCategoryImage()));
						content.put("contentProuctBrandName", tc.getProductBrandName());
						content.put("contentProductPrice", String.valueOf(tc.getProductPrice()));
						content.put("contentProductIntro", tc.getIntro());
						content.put("contentProductMKPrice", tc.getProductMkPrice());
						content.put("contentProductUrl", tc.getUrl());
						if("1".equals(tc.getStatus())){
							content.put("contentProductIsShowCat", "yes");
						}else{
							content.put("contentProductIsShowCat", "no");
						}
						if(tc.getIsCollect()==0){
							content.put("contentIsCollect", "no");
						}else{
							content.put("contentIsCollect", "yes");
						}
					}else if("video".equals(tc.getType())){
						content.put("contentVideoUrl", tc.getContent());
						content.put("contentVideoImage", this.getImageUrl(tc.getImage()));
						content.put("contentVideoImageWidth", String.valueOf(tc.getImagewidth()));
						content.put("contentVideoImageHeight", String.valueOf(tc.getImageheight()));
					}else if("defaultImage".equals(tc.getType())){
						content.put("defaultImage", this.getImageUrl("attachment/allDefaultImage/upArrowDefaultImage.png"));
						content.put("contentWidth", "640");
						content.put("contentHeight", "25");
					}
					contents.add(content);
				}
				jsonObject.put("themeContent", contents);
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("themeId", String.valueOf(themeId));
			map.put("recommendStatus", "1");
			Page page =	productService.getThemeProducts(1, 5 , map);
			List<ThemeProduct> tps = (List<ThemeProduct>) page.getResult();
			JSONArray jsonArray = new JSONArray();
			for(ThemeProduct tp:tps){
				JSONObject theme2 = new JSONObject();
				theme2.put("themeId", String.valueOf(tp.getTheme().getId()));
				theme2.put("themeImage", this.getImageUrl(tp.getTheme().getImage()));
				theme2.put("themeTitle", tp.getTheme().getTitle());
				theme2.put("themeDetails", tp.getTheme().getDetails());
				theme2.put("contentStyle", tp.getTheme().getContentStyle());
				theme2.put("tags", tp.getTheme().getTagsImage());
				jsonArray.add(theme2);
			}
			jsonObject.put("themeData", jsonArray);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取主题详情失败",e);
			this.logger.error(e,e);
		} finally {
			out.write(jsonObject.toString());
			out.close();
		}
	}
	
	/**
	 * 获取主题列表  首页
	 */
	public void getThemeList(){
		try {
			int pageNo = Integer.parseInt(paramObject.getString("page"));
			String version = paramObject.getString("version");
			Map<String,String> map = new HashMap<String,String>();
			map.put("indexStatus", "1");
			if(pageNo>1&&version.startsWith("1.")){
				Map<String,Object> map2=new HashMap<String,Object>();
				map2.put("time", new Date().getTime());
				Page page = productService.getProductList(pageNo-1, 5, map2);
				jsonObject.put("totalCount", page.getTotalPageCount());
				JSONArray resJa = new JSONArray();
				JSONArray ja = JSONArray.fromObject(page.getResult());
				for(int i=0;i<ja.size();i++){
					JSONObject resObj = new JSONObject();
					JSONObject obj = (JSONObject) ja.get(i);
					resObj.put("pid", String.valueOf(obj.get("goods_id")));
					resObj.put("productOrigin", obj.get("productOrigin"));
					resObj.put("pname", obj.get("name"));
					resObj.put("pimage", this.getImageUrl((String)obj.get("original")));
					resObj.put("pprice", String.valueOf(obj.get("price")));
					resObj.put("pmktprice", String.valueOf(obj.get("mktprice")));
					resObj.put("purl", obj.get("url"));
					resObj.put("ptitle", obj.get("brief"));
					resObj.put("productOrigin", obj.get("productorigin"));
					if(obj.get("isshowmkprice")!=null&&(int)obj.get("isshowmkprice")==-1){
						resObj.put("isShowMKPrice", "no");
					}else{
						resObj.put("isShowMKPrice", "yes");
					}
					resJa.add(resObj);
				}
				jsonObject.put("productData", resJa);
				return;
			}else if(version.startsWith("1.")){
				Page page =	productService.getThemeProductsAPP(pageNo, 10 , map);
				List<Map<String,Object>> tps = (List<Map<String,Object>>) page.getResult();
				JSONArray jsonArray = new JSONArray();
				for(Map<String,Object> tp:tps){
					JSONObject theme = new JSONObject();
					theme.put("themeId", String.valueOf(tp.get("id")));
					theme.put("themeImage", this.getImageUrl((String)tp.get("image")));
					theme.put("contentStyle", tp.get("contentStyle"));
					theme.put("tags", this.getImageUrl((String)tp.get("tagImage")));
					jsonArray.add(theme);
				}
				jsonObject.put("themeData", jsonArray);
			}else if(version.startsWith("2.")){
				map.put("contentStyle", "topic");
				Page page =	productService.getThemeProductsAPPVersion2(pageNo, 10 , map);
				List<Map<String,Object>> tps = (List<Map<String,Object>>) page.getResult();
				JSONArray jsonArray = new JSONArray();
				JSONObject theme = new JSONObject();
				int beforThemeId = 0;
				for(Map<String,Object> tp:tps){
					if(beforThemeId == (int)tp.get("id")){
						JSONObject productObj = new JSONObject();
						productObj.put("pid", tp.get("pid"));
						productObj.put("pname", tp.get("pname"));
						productObj.put("pimage", this.getImageUrl((String)tp.get("pimage")));
						productObj.put("pprice", String.valueOf(tp.get("pprice")));
						JSONArray ja = theme.getJSONArray("productData");
						ja.add(productObj);
						theme.put("productData", ja);
					}else{
						if(beforThemeId != 0){
							jsonArray.add(theme);
						}
						theme = new JSONObject();
						theme.put("themeId", String.valueOf(tp.get("id")));
						theme.put("themeImage", this.getImageUrl((String)tp.get("image")));
						theme.put("contentStyle", tp.get("contentStyle"));
						theme.put("tags", this.getImageUrl((String)tp.get("tagImage")));
						theme.put("productData", new JSONArray());
						beforThemeId = (int)tp.get("id");
					}
				}
				jsonArray.add(theme);
				jsonObject.put("themeData", jsonArray);
			}
			//类型列表
			List<ThemeTag> themeTags = productService.getThemeTagList();
			for(ThemeTag themetag:themeTags){
				if("类型".equals(themetag.getName())){
					JSONObject typeObj = new JSONObject();
					typeObj.put("typeId", String.valueOf(themetag.getId()));
					typeObj.put("typeName", themetag.getName());
					JSONArray childrenTag = new JSONArray();
					for(ThemeTag ctag:themetag.getChildrenThemeTag()){
						JSONObject childrenTagObj = new JSONObject();
						childrenTagObj.put("id", String.valueOf(ctag.getId()));
						childrenTagObj.put("name", ctag.getName());
						if("熊".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findXiongDefault.png"));
						}else if("猴".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findHouDefault.png"));
						}else if("狒狒".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findFeiFeiDefault.png"));
						}else if("通用".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findAllDefault.png"));
						}else if("情趣".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findQingQuDefault.png"));
						}else if("护肤".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findHuFuDefault.png"));
						}else if("服装".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findFuZhuangDefault.png"));
						}else if("配饰".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findPeiShiDefault.png"));
						}else if("生活".equals(ctag.getName())){
							childrenTagObj.put("image", this.getImageUrl("attachment/allDefaultImage/findShengHuoDefault.png"));
						}else{
							childrenTagObj.put("image", this.getImageUrl("123.png"));
						}
						if(ctag.getName().indexOf("双十一")<0){
							childrenTag.add(childrenTagObj);
						}
					}
					jsonObject.put("typeData", childrenTag);
				}
			}
			//banner获取
			List<PhoneBanner> phoneBanners = bannerService.getCurrentBanners("首页banner");
			if(phoneBanners!=null&&phoneBanners.size()>0){
				JSONArray bannerJarray = new JSONArray();
				for(PhoneBanner pb:phoneBanners){
					JSONObject bannerObj = new JSONObject();
					bannerObj.put("bannerType", String.valueOf(pb.getType()));
					bannerObj.put("bannerId", String.valueOf(pb.getId()));
					bannerObj.put("bannerImage", this.getImageUrl(pb.getImage()));
					bannerObj.put("bannerData", pb.getDetails());
					if(pb.getThemeContentStyle()!=null&&!pb.getThemeContentStyle().equals("0")){
						bannerObj.put("contentStyle", pb.getThemeContentStyle());
					}
					bannerJarray.add(bannerObj);
				}
				jsonObject.put("bannerList", bannerJarray);
			}
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("获取主题列表失败",e);
			this.logger.error(e,e);
		} finally {
			out.write(jsonObject.toString());
			out.close();
		}
	}
	
	/**
	 * 喜欢这个主题
	 */
	public void loveTheme(){
		try{
			int themeId = Integer.parseInt(paramObject.getString("themeId"));
			int memberId = Integer.parseInt(paramObject.getString("memberId"));
			productService.loveTheme(memberId, themeId);
		} catch (Exception e) {
			if("success".equalsIgnoreCase(jsonObject.getString("result"))){
				jsonObject.put("result", "FAILED");
			}
			if(!jsonObject.containsKey("reason")){
				jsonObject.put("reason", "系统错误！");
			}
			e.printStackTrace();
			this.logger.error("调用"+methodStr+"方法失败");
			this.logger.error("参数:"+requestStr);
			this.logger.error("赞主题失败",e);
			this.logger.error(e,e);
		} finally {
			out.write(jsonObject.toString());
			out.close();
		}
	}
	
	
}
