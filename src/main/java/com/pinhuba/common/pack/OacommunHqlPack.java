package com.pinhuba.common.pack;

import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.core.pojo.OaChatGroups;
import com.pinhuba.core.pojo.OaChatters;
import com.pinhuba.core.pojo.OaForums;
import com.pinhuba.core.pojo.OaPosts;
import com.pinhuba.core.pojo.OaVote;

public class OacommunHqlPack {
	//查询所有分组信息
	public static String packChatGroupQuery(OaChatGroups groups, String emp, long companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(groups.getOaChatgpName(), "oaChatgpName", result);
		HqlPack.getStringEqualPack(emp, "recordId", result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	//查询通讯手册记录信息
	public static String packCommunicationQuery(OaChatters chatter, String emp, long companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(chatter.getOaChatEmp(), "oaChatEmp", result);
		HqlPack.getStringLikerPack(chatter.getOaChatCom(), "oaChatCom", result);
		HqlPack.getNumEqualPack(chatter.getOaChatGroup(), "oaChatGroup", result);
		HqlPack.getNumEqualPack(chatter.getOaChatSex(), "oaChatSex", result);
		HqlPack.getNumEqualPack(chatter.getOaIsShare(), "oaIsShare", result);
		HqlPack.getStringEqualPack(emp, "recordId", result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	//不分页查询分组信息
	public static String packGroupQuery(String emp, long companyId){
		StringBuffer result = new StringBuffer();
		HqlPack.getStringEqualPack(emp, "recordId", result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	// 查询共享给自己的通讯手册记录
	public static String packChatShare(String emp, long companyId){
		StringBuffer result = new StringBuffer();
		String emptep = emp+",";
		HqlPack.getStringLikerPack(emptep, "oaShareEmp", result);
		HqlPack.getNumEqualPack(EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value, "oaIsShare",result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	//查询论坛版块信息
	public static String packForumsQuery(OaForums forums, long companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(forums.getOaForumName(), "oaForumName", result);
		HqlPack.getStringEqualPack(forums.getOaForumEmp(), "oaForumEmp", result);
		HqlPack.getStringEqualPack(forums.getOaForumAdmin(), "oaForumAdmin", result);
		HqlPack.timeBuilder(forums.getOaForumTime(), "oaForumTime", result, false,false);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	//查询帖子信息
	public static String packPostsQuery(OaPosts posts, long companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(posts.getOaPostName(), "oaPostName", result);
		HqlPack.getStringEqualPack(posts.getOaPostEmp(), "oaPostEmp", result);
		HqlPack.getNumEqualPack(posts.getOaPostForum(), "oaPostForum",result);
		HqlPack.getNumEqualPack(posts.getOaIsBoutique(), "oaIsBoutique",result);
		HqlPack.timeBuilder(posts.getOaPostTime(), "oaPostTime", result, false,true);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		result.append(" and model.oaIsPost = "+EnumUtil.OA_IS_POSTS.POSTS.value);
		return result.toString();
	}
	
	//无条件，无分页查询版块信息
	public static String packNoForumQuery(long companyId){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	//查询帖子回复信息
	public static String packRegPostsQuery(long postid,long companyId){
		StringBuffer result = new StringBuffer();
		HqlPack.getNumEqualPack(postid, "oaPostReg",result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
	
	//查询投票记录信息(投票页面用)
	public static String packVoteQuery(OaVote vote, long companyId,String empId,String depId) {
		StringBuffer result = new StringBuffer();
		result.append("select vote.* from oa_vote vote,hrm_employee emp where vote.oa_vote_emp = emp.hrm_employee_id");
		
		SqlPack.getStringLikerPack(vote.getOaVoteName(), "vote.oa_vote_name", result);
		SqlPack.getNumEqualPack(vote.getOaChooseType(), "vote.oa_choose_type", result);
		SqlPack.getNumEqualPack(vote.getOaIsAnonymous(), "vote.oa_is_anonymous", result);
		SqlPack.getNumEqualPack(vote.getOaVoteType(), "vote.oa_vote_type", result);
		if(vote.getEmployee() != null){
		        SqlPack.getStringLikerPack(vote.getEmployee().getHrmEmployeeName(), "emp.hrm_employee_name", result);
		}
		SqlPack.timeBuilder(vote.getOaVoteStart(),"vote.oa_vote_start",result,false,true);
		SqlPack.timeBuilder(vote.getOaVoteEnd(),"vote.oa_vote_end",result,false,true);
		SqlPack.getNumEqualPack(vote.getOaVoteStatus(), "vote.oa_vote_status",result);
		SqlPack.getNumEqualPack(companyId, "vote.company_id",result);
		
//		result.append(" and ( vote.oa_range_emp like '%"+empId+"%' or CheckStrInArr('"+depId+"',vote.oa_range_dep)>0   or vote.oa_vote_emp ='"+empId.substring(0, empId.length()-1)+"')");
		result.append(" and ( vote.oa_range_emp like '%"+empId+"%' or INSTR(vote.oa_range_dep,'"+depId+"')>0   or vote.oa_vote_emp ='"+empId.substring(0, empId.length()-1)+"')");
	
		return result.toString();
	}
	
	//查询投票记录信息(投票管理页面用)
	public static String packVoteManagerQuery(OaVote vote, long companyId) {
		StringBuffer result = new StringBuffer();
		HqlPack.getStringLikerPack(vote.getOaVoteName(), "oaVoteName", result);
		HqlPack.getNumEqualPack(vote.getOaChooseType(), "oaChooseType", result);
		HqlPack.getNumEqualPack(vote.getOaIsAnonymous(), "oaIsAnonymous", result);
		HqlPack.getNumEqualPack(vote.getOaVoteType(), "oaVoteType", result);
		HqlPack.getStringEqualPack(vote.getOaVoteEmp(), "oaVoteEmp", result);
		HqlPack.timeBuilder(vote.getOaVoteStart(),"oaVoteStart",result,false,true);
		HqlPack.timeBuilder(vote.getOaVoteEnd(),"oaVoteEnd",result,false,true);
		HqlPack.getNumEqualPack(vote.getOaVoteStatus(), "oaVoteStatus",result);
		HqlPack.getNumEqualPack(companyId, "companyId",result);
		return result.toString();
	}
}

