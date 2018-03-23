package com.pinhuba.core.iservice;

import java.util.List;

import com.pinhuba.common.pages.Pager;
import com.pinhuba.core.pojo.OaChatGroups;
import com.pinhuba.core.pojo.OaChatters;
import com.pinhuba.core.pojo.OaForums;
import com.pinhuba.core.pojo.OaPosts;
import com.pinhuba.core.pojo.OaVote;
import com.pinhuba.core.pojo.OaVoteOption;
import com.pinhuba.core.pojo.OaVoteStatus;
import com.pinhuba.core.pojo.SysImageInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;

/**
 * 
 * @author liurunkai
 * 
 */
public interface IOaChartterService {
	// 通讯手册分组操作
	public List<OaChatGroups> getAllChatGroup(OaChatGroups groups, String emp, long l, Pager pager);

	public int listChatGroupCount(OaChatGroups groups, String emp, long l);

	public OaChatGroups getChatGroupByid(long id);

	public void deleteGroupByid(long[] id);

	public OaChatGroups saveChatGroup(OaChatGroups group);

	public List<OaChatGroups> getAllChatGroupByEmp(String emp, long l);

	// 通讯手册基本信息操作
	public List<OaChatters> getAllCommunication(OaChatters chatter, String emp, long l, Pager pager);

	public int listCommunicationCount(OaChatters chatter, String emp, long l);

	public OaChatters getCommunicationByid(long id);

	public void deleteCOmmunicationByid(long[] ids);

	public OaChatters saveCommunication(OaChatters chatter);

	public int getCommunicationCount(long groupId, String emp, long l);

	public List<OaChatters> getShareCommunication(String emp, long l, Pager pager); // 获取共享通讯手册数据

	public int listCommunicationCount(String emp, long l); // 统计共享通讯数据

	public String setChattersOwnedByids(long[] ids); // 共享通讯手册记录设置私有

	public void shareSetChatter(long[] ids, String shareEmp, String empid); // 设置共享

	public List<OaChatters> getSharedCommunication(String empid, long chatterId); // 查询已签入记录

	// 照片操作
	public SysImageInfo saveImageInfo(SysImageInfo image);

	public void deleteImageInfoByid(long id);

	public SysImageInfo getImageInfoByPK(long id);

	// 论坛版块操作
	public List<OaForums> getAllForums(OaForums forums, long l);

	public int listForumsCount(OaForums forums, long l);

	public void deleteForumsByid(long[] ids);

	public OaForums saveForums(OaForums forums);

	public int getPostsCount(long forumid, long l);

	public OaForums getForumsByid(long id);

	public List<OaForums> getForums(long l); // 无查询条件，无分页获取版块信息

	public List<OaForums> getForumsByAdmin(OaForums forums, long l); // 根据版主获取版块信息

	public int getForumsByAdminCount(OaForums forums, long l); // 根据版主查询板块数

	// 论坛帖子操作
	public List<OaPosts> getAllPosts(OaPosts posts, long l, Pager pager);

	public int listPostSCount(OaPosts posts, long l);

	public boolean deletePostsByid(long[] ids, int companyId, String empId);

	public OaPosts savePosts(OaPosts posts);

	public OaPosts getPostsByid(long id);

	/**
	 * 统计此版块中主题帖的数量
	 */
	public int getTopicCount(long forumid, long l);

	/**
	 * 统计此版块中文章的数量
	 */
	public int getArticleCount(long forumid, long l);

	/**
	 * 统计此版块中今日发帖数
	 */
	public int getTodayPostCount(long forumid, long l);

	/**
	 * 查询最后回复帖子
	 */
	public List<OaPosts> getLastReplyPost(long forumid, long l);

	// 帖子回复操作
	public List<OaPosts> getAllRegByPost(long postId, long l, Pager pager);

	public int listRegByPostCount(long postId, long l);

	public void deleteRegByid(long id);

	public List<OaPosts> getNoPagerRegPosts(long postid, long l);

	// 投票操作
	public List<OaVote> getAllVote(OaVote vote, long com, String empId, String depId, Pager pager) throws Exception;

	public int listVoteCount(OaVote vote, long com, String empId, String depId);

	public void deleteVoteByids(long[] ids, long com);

	public OaVote saveVote(OaVote vote, List<OaVoteOption> options);

	public OaVote updateVote(OaVote vote, List<OaVoteOption> options);

	public OaVote getVoteByid(long id, long com);

	public SysLibraryInfo getVoteTypeByid(long id);

	public OaVote saveVoteNoOption(OaVote vote);

	public List<OaVoteOption> getAlloptionByVote(long voteid, long com);

	public OaVoteOption getOptionByid(long id);

	public List<OaVoteStatus> getStatusByEmpAndVoteId(String emp, long voteid, long com);

	public List<OaVoteStatus> getStatusByOnlyVoteId(long voteid, long com);

	public List<OaVoteStatus> getStatusByVoteId(long voteid, long com);

	public void updateOption(List<OaVoteOption> op);

	public OaVoteStatus saveVoteStatus(OaVoteStatus os);

	public List<OaVote> getAllVoteManager(OaVote vo, long com, Pager pager);

	public int listVoteCountManager(OaVote vote, long com);

	public List<OaVoteStatus> getStatusByVoteIdNoAnonymous(long voteid, long com);
	
	public List<OaForums> getAllForumsByPager(OaForums forums, long l,Pager pager);
	
	public int getChatGroupsCountByname(String name,String empid,long id,long com);
}
