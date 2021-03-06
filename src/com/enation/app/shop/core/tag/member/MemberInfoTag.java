package com.enation.app.shop.core.tag.member;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.enation.app.base.core.model.Member;
import com.enation.app.base.core.service.IMemberManager;
import com.enation.eop.sdk.context.UserConext;
import com.enation.framework.taglib.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 会员信息标签
 * 返回当前的会员，如果没有登陆返回null
 * @author kingapex
 *2013-8-1下午3:24:08
 */
@Component
@Scope("prototype")
public class MemberInfoTag extends BaseFreeMarkerTag {
	private IMemberManager memberManager;
	
	
	/**
	 * 会员详细标签
	 * @param 无
	 * @return 返回当前会员,Member型
	 * 如果未登陆，返回null
	 * {@link Member}
	 */
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		String mustlogin=(String)params.get("mustlogin");
		
		Member member = UserConext.getCurrentMember();
		
		if( member==null){
			throw new TemplateModelException("未登陆不能使用此标签[MemberInfoTag]");
		}else{
		
			int memberid =member.getMember_id();
			member = this.memberManager.get(memberid);
			
			return member;
		}
	}
	public IMemberManager getMemberManager() {
		return memberManager;
	}
	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}

}
