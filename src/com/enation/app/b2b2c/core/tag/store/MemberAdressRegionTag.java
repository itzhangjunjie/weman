package com.enation.app.b2b2c.core.tag.store;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.enation.app.b2b2c.core.model.member.StoreMember;
import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
import com.enation.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 查询默认会员地址
 * @author xulipeng
 * 2014年12月13日16:33:53
 *
 */
@Component
public class MemberAdressRegionTag extends BaseFreeMarkerTag {

	private IStoreMemberManager storeMemberManager;
	private IStoreMemberAddressManager storeMemberAddressManager;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		StoreMember storeMember = storeMemberManager.getStoreMember();
		Integer	regionid = storeMemberAddressManager.getRegionid(storeMember.getMember_id());
		return regionid;
	}
	
	public IStoreMemberAddressManager getStoreMemberAddressManager() {
		return storeMemberAddressManager;
	}
	public void setStoreMemberAddressManager(
			IStoreMemberAddressManager storeMemberAddressManager) {
		this.storeMemberAddressManager = storeMemberAddressManager;
	}

	public IStoreMemberManager getStoreMemberManager() {
		return storeMemberManager;
	}

	public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
		this.storeMemberManager = storeMemberManager;
	}

}
