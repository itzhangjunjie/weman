package com.enation.app.shop.core.service;

import java.util.List;

import com.enation.app.shop.core.model.Favorite;
import com.enation.framework.database.Page;

/**
 * 商品收藏管理
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:39:25<br/>
 *         version 1.0<br/>
 */
public interface IFavoriteManager {

	/**
	 * 列表我的收藏
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(int pageNo, int pageSize);
	
	
	
	/**
	 * 读取某个会员的收藏 
	 * @param memberid
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(int memberid,int pageNo,int pageSize);
	
	
	
	
	/**
	 * 读取会员的所有收藏商品
	 * @return
	 */
	public List list();

	/**
	 * 删除收藏
	 * 
	 * @param favorite_id
	 */
	public void delete(int favorite_id);
	
	
	
	/**
	 * 添加一个收藏
	 * @param goodsid
	 * @param memberid
	 */
	public void add(Integer goodsid);
	/**
	 * 根据商品ID和用户ID获取一个收藏
	 * @param goodsid 商品Id
	 * @param memeberid 会员Id
	 * @return 个数
	 */
	public int getCount(Integer goodsid, Integer memeberid);
	/**
	 * 获取会员收藏个数
	 * @param memberId 会员ID
	 * @return 收藏个数
	 */
	public int getCount(Integer memberId);
	
	
}
