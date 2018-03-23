package com.pinhuba.core.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinhuba.common.pack.OacommunHqlPack;
import com.pinhuba.common.pages.Pager;
import com.pinhuba.common.util.EnumUtil;
import com.pinhuba.common.util.UtilWork;
import com.pinhuba.core.dao.IOaChatGroupsDao;
import com.pinhuba.core.dao.IOaChattersDao;
import com.pinhuba.core.dao.IOaForumsDao;
import com.pinhuba.core.dao.IOaPostsDao;
import com.pinhuba.core.dao.IOaVoteDao;
import com.pinhuba.core.dao.IOaVoteOptionDao;
import com.pinhuba.core.dao.IOaVoteStatusDao;
import com.pinhuba.core.dao.ISysImageInfoDao;
import com.pinhuba.core.dao.ISysLibraryInfoDao;
import com.pinhuba.core.iservice.IOaChartterService;
import com.pinhuba.core.pojo.OaChatGroups;
import com.pinhuba.core.pojo.OaChatters;
import com.pinhuba.core.pojo.OaForums;
import com.pinhuba.core.pojo.OaPosts;
import com.pinhuba.core.pojo.OaVote;
import com.pinhuba.core.pojo.OaVoteOption;
import com.pinhuba.core.pojo.OaVoteStatus;
import com.pinhuba.core.pojo.SysImageInfo;
import com.pinhuba.core.pojo.SysLibraryInfo;

@Service
@Transactional
public class OaChartterService implements IOaChartterService {
	@Resource	
	private IOaChattersDao oaChattersDao;
	@Resource
	private IOaChatGroupsDao oaChatGroupsDao;
	@Resource
	private ISysImageInfoDao sysImageInfoDao;
	@Resource
	private IOaForumsDao oaForumsDao;
	@Resource
	private IOaPostsDao oaPostsDao;
	@Resource
	private IOaVoteDao OaVoteDao;
	@Resource
	private IOaVoteOptionDao oaVoteOptionDao;
	@Resource
	private IOaVoteStatusDao oaVoteStatusDao;
	@Resource
	private ISysLibraryInfoDao sysLibraryInfoDao;
	
	/**
	 * 删除分组记录
	 */
	public void deleteGroupByid(long[] ids) {
		for (long l : ids) {
			OaChatGroups group = oaChatGroupsDao.getByPK(l);
			oaChatGroupsDao.remove(group);
		}
	}
	
	/**
	 * 显示所有分组信息
	 */
	public List<OaChatGroups> getAllChatGroup(OaChatGroups groups, String emp, long l, Pager pager) {
		List<OaChatGroups> groupList = oaChatGroupsDao.findByHqlWherePage(OacommunHqlPack.packChatGroupQuery(groups,emp,l), pager);
		return groupList;
	}
	
	/**
	 * 根据id获取分组信息
	 */
	public OaChatGroups getChatGroupByid(long id) {
		OaChatGroups group = oaChatGroupsDao.getByPK(id);
		return group;
	}
	
	/**
	 * 记录分组数据数量
	 */ 
	public int listChatGroupCount(OaChatGroups groups, String emp, long l) {
		int count = oaChatGroupsDao.findByHqlWhereCount(OacommunHqlPack.packChatGroupQuery(groups,emp,l));
		return count;
	}
	
	/**
	 * 保存分组信息
	 */
	public OaChatGroups saveChatGroup(OaChatGroups group) {
		OaChatGroups groups =  (OaChatGroups) oaChatGroupsDao.save(group);
		return groups;
	}
	
	/**
	 * 删除通讯手册记录
	 */
	public void deleteCOmmunicationByid(long[] ids) {
		for (long l : ids) {
			OaChatters chatter = oaChattersDao.getByPK(l);
			oaChattersDao.remove(chatter);
		}
	}
	
	/**
	 * 获取通讯手册记录
	 */
	public List<OaChatters> getAllCommunication(OaChatters chatter, String emp, long l, Pager pager) {
		
		List<OaChatters> chatterList = oaChattersDao.findByHqlWherePage(OacommunHqlPack.packCommunicationQuery(chatter, emp, l)+" order by  model.primaryKey desc", pager);
		return chatterList;
	}
	
	/**
	 * 根据id获取通讯手册
	 */
	public OaChatters getCommunicationByid(long id) {
		OaChatters chatter = oaChattersDao.getByPK(id);
		return chatter;
	}
	
	/**
	 * 统计通讯手册数量
	 */
	public int listCommunicationCount(OaChatters chatter, String emp, long l) {
		int count = oaChattersDao.findByHqlWhereCount(OacommunHqlPack.packCommunicationQuery(chatter, emp, l));
		return count;
	}
	
	/**
	 * 保存通讯手册信息
	 */
	public OaChatters saveCommunication(OaChatters chatter) {
		OaChatters oaChatter = (OaChatters) oaChattersDao.save(chatter);
		return oaChatter;
	}
	
	/**
	 * 获取分组信息
	 */
	public List<OaChatGroups> getAllChatGroupByEmp(String emp, long l) {
		List<OaChatGroups> groupList = oaChatGroupsDao.findByHqlWhere(OacommunHqlPack.packGroupQuery(emp, l));
		return groupList;
	}

	
	/**
	 * 保存照片
	 */
	public SysImageInfo saveImageInfo(SysImageInfo image) {
		SysImageInfo imageInfo = (SysImageInfo) sysImageInfoDao.save(image);
		return imageInfo;
	}
	
	/**
	 * 删除照片信息
	 */
	public void deleteImageInfoByid(long id) {
		SysImageInfo imageInfo = sysImageInfoDao.getByPK(id);
		sysImageInfoDao.remove(imageInfo);
	}
	
	/**
	 * 获取照片信息
	 */
	public SysImageInfo getImageInfoByPK(long id) {
		SysImageInfo image = sysImageInfoDao.getByPK(id);
		return image;
	}
	
	/**
	 * 根据分组id统计通讯录数量
	 */
	public int getCommunicationCount(long groupId,String emp, long l) {
		 int count = oaChattersDao.findByHqlWhereCount(" and model.oaChatGroup = "+ groupId +" and model.companyId = "+l+" and model.recordId = '"+emp+"'");
		return count;
	}
	
	/**
	 * 获取共享通讯记录
	 */
	public List<OaChatters> getShareCommunication(String emp, long l, Pager pager) {
		List<OaChatters> chatter = oaChattersDao.findByHqlWherePage(OacommunHqlPack.packChatShare(emp, l), pager);
		return chatter;
	}
	
	/**
	 * 统计共享通讯记录
	 */
	public int listCommunicationCount(String emp, long l) {
		int count = oaChattersDao.findByHqlWhereCount(OacommunHqlPack.packChatShare(emp, l));
		return count;
	}
	
	/**
	 * 共享通讯手册设置私有
	 */
	public String setChattersOwnedByids(long[] ids) {
		for (long l : ids) {
			OaChatters chatter = oaChattersDao.getByPK(l);
			if(chatter == null){
				return "noChatter";
			}
			chatter.setOaIsShare(EnumUtil.OA_COMMUNICATION_IS_SHARE.UNSHARE.value);
			chatter.setOaShareEmp("");
			oaChattersDao.save(chatter);
		}
		return null;
	}
	
	
	/**
	 * 删除论坛版块
	 */
	public void deleteForumsByid(long[] ids) {
		for (long l : ids) {
			OaForums forums = oaForumsDao.getByPK(l);
			oaForumsDao.remove(forums);
		}
	}
	
	/**
	 * 根据版主查询板块数
	 */
	public int  getForumsByAdminCount(OaForums forums,long l) {
		return oaForumsDao.findByHqlWhereCount(OacommunHqlPack.packForumsQuery(forums, l));
	}
	
    /**
     * 获取所有论坛版块信息
     */
	public List<OaForums> getAllForums(OaForums forums, long l) {
		List<OaForums> oaForums = oaForumsDao.findByHqlWhere(OacommunHqlPack.packForumsQuery(forums, l));
		return oaForums;
	}
	
	/**
     * 获取所有论坛版块信息
     */
	public List<OaForums> getAllForumsByPager(OaForums forums, long l,Pager pager) {
		List<OaForums> oaForums = oaForumsDao.findByHqlWherePage(OacommunHqlPack.packForumsQuery(forums, l),pager);
		return oaForums;
	}
	
	/**
	 * 统计此版块中帖子的数量
	 */
	public int getPostsCount(long forumid, long l) {
		int count = oaPostsDao.findByHqlWhereCount(" and model.oaPostForum = "+ forumid +" and model.companyId = "+l+"");
		return count;
	}
	
	/**
	 * 统计此版块中主题帖的数量
	 */
	public int getTopicCount(long forumid, long l) {
		int count = oaPostsDao.findByHqlWhereCount(" and model.oaIsPost = 1 and model.oaPostForum = "+ forumid +" and model.companyId = "+l+"");
		return count;
	}
	
	/**
	 * 统计此版块中文章的数量
	 */
	public int getArticleCount(long forumid, long l) {
		int count = oaPostsDao.findByHqlWhereCount(" and model.oaIsPost = 2 and model.oaPostForum = "+ forumid +" and model.companyId = "+l+"");
		return count;
	}
	
	/**
	 * 统计此版块中今日发帖数
	 */
	public int getTodayPostCount(long forumid, long l) {
		int count = oaPostsDao.findByHqlWhereCount(" and model.oaPostTime between '"+UtilWork.getTodayZeroPoint()+"' and '"+UtilWork.getNextDayZeroPoint()+"' and model.oaPostForum = "+ forumid +" and model.companyId = "+l+"");
		return count;
	}
	
	/**
	 * 查询最后回复帖子
	 */
	public List<OaPosts> getLastReplyPost(long forumid, long l) {
		List<OaPosts> list = oaPostsDao.findBySql("select * from oa_posts where oa_post_forum = "+ forumid +" and company_id = "+l+" order by oa_post_lastregter desc", OaPosts.class);
		return list;
	}
	
	/**
	 * 获取版块数
	 */
	public int listForumsCount(OaForums forums, long l) {
		int count = oaForumsDao.findByHqlWhereCount(OacommunHqlPack.packForumsQuery(forums, l));
		return count;
	}
	
	/**
	 * 保存版块信息
	 */
	public OaForums saveForums(OaForums forums) {
		OaForums oaForums = (OaForums) oaForumsDao.save(forums);
		return oaForums;
	}
	
	/**
	 * 删除帖子
	 */
	public boolean deletePostsByid(long[] ids,int companyId,String empId) {
		boolean bl = false;
		for (long l : ids) {
    		OaPosts ps = this.getPostsByid(l);
			OaForums fs = this.getForumsByid(ps.getOaPostForum());
			// 只有版主才能删除帖子，判断是不是版主，是则删除帖子和此帖子回复，否则不允许删除！
			if (fs.getOaForumAdmin() == null || !fs.getOaForumAdmin().equals(empId)) {
				bl = true;
				break;
			} 
 		 }
		 if(!bl) {
			for (long l : ids) {
				List<OaPosts> oaPosts = oaPostsDao.findByHqlWhere(OacommunHqlPack.packRegPostsQuery(l, companyId));
				for (OaPosts p2 : oaPosts) {
					oaPostsDao.remove(p2);
				}
			}
		}
		return bl;
	}
	
	/**
	 * 获取帖子信息
	 */
	public List<OaPosts> getAllPosts(OaPosts posts, long l, Pager pager) {
		List<OaPosts> oaPosts = oaPostsDao.findByHqlWherePage(OacommunHqlPack.packPostsQuery(posts, l)+" order by  model.oaIsBoutique ,model.oaPostLastregter desc", pager);
		return oaPosts;
	}
	
	/**
	 * 统计帖子数量
	 */
	public int listPostSCount(OaPosts posts, long l) {
		int count = oaPostsDao.findByHqlWhereCount(OacommunHqlPack.packPostsQuery(posts, l));
		return count;
	}
	
	/**
	 * 保存帖子
	 */
	public OaPosts savePosts(OaPosts posts) {
		OaPosts oaPosts = (OaPosts) oaPostsDao.save(posts);
		return oaPosts;
	}
	
	/**
	 * 根据id获取版块信息
	 */
	public OaForums getForumsByid(long id) {
		OaForums forum = oaForumsDao.getByPK(id);
		return forum;
	}
	
	/**
	 * 无分页获取版块信息
	 */
	public List<OaForums> getForums(long l) {
		List<OaForums> list = oaForumsDao.findByHqlWhere(OacommunHqlPack.packNoForumQuery(l));
		return list;
	}
	
	/**
	 * 根据ID获取帖子信息
	 */
	public OaPosts getPostsByid(long id) {
		OaPosts posts = oaPostsDao.getByPK(id);
		return posts;
	}
	
	/**
	 * 根据主键删除回复
	 */
	public void deleteRegByid(long id) {
		OaPosts posts = oaPostsDao.getByPK(id);
		if(posts.getOaIsPost() == EnumUtil.OA_IS_POSTS.POSTS.value){
			//不删除
		}else{
			posts.setOaPostText("******此回复已被版主或发帖人删除******");
		    oaPostsDao.save(posts);
		}
	}
	
	/**
	 * 分页获取回复记录
	 */
	public List<OaPosts> getAllRegByPost(long postId, long l, Pager pager) {
		List<OaPosts> oaPosts = oaPostsDao.findByHqlWherePage(OacommunHqlPack.packRegPostsQuery(postId, l)+" order by model.oaPostTime", pager);
		return oaPosts;
	}
	
	/**
	 * 不分页获取回复记录
	 */
	public List<OaPosts> getNoPagerRegPosts(long postid, long l) {
		List<OaPosts> oaPosts = oaPostsDao.findByHqlWhere(OacommunHqlPack.packRegPostsQuery(postid, l));
		return oaPosts;
	}
	
	/**
	 * 统计回复数量
	 */
	public int listRegByPostCount(long postId, long l) {
		int count = oaPostsDao.findByHqlWhereCount(OacommunHqlPack.packRegPostsQuery(postId, l));
		return count;
	}
	
	/**
	 * 删除投票记录
	 */
	public void deleteVoteByids(long[] ids, long com) {
		for (long l : ids) {
			OaVote vote = OaVoteDao.getByPK(l);
			
			//删除投票选项记录
			List<OaVoteOption> opList = oaVoteOptionDao.findByHqlWhere(" and model.oaVoteId = " +l+" and model.companyId = "+com+"");
			for (OaVoteOption option : opList) {
				oaVoteOptionDao.remove(option);
			}
			
		    //删除人员投票信息
			List<OaVoteStatus> statusList = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteRec = "+l+" and model.companyId ="+com+"");
			for (OaVoteStatus oaVoteStatus : statusList) {
				oaVoteStatusDao.remove(oaVoteStatus);
			}
			
			//删除投票记录
			OaVoteDao.remove(vote);
		}
	}
	
    /**
     * 获取所有投票记录
     */
	public List<OaVote> getAllVote(OaVote vo, long com,String empId,String depId,Pager pager) throws Exception{
		String time = UtilWork.getNowTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<OaVote>  list = OaVoteDao.findByHqlWhere(" and model.companyId = " + com);
		
		for (OaVote oaVote : list) {
			 //判断投票是否已结束 ，如果已结束则不修改任何数据
			if(oaVote.getOaVoteStatus() != null && oaVote.getOaVoteStatus() != EnumUtil.OA_VOTE_STATUS.END.value){ //操作投票中和未开始
				 
				if(oaVote.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.VOTING.value){  //投票中
					if(oaVote.getOaVoteEnd() != null && oaVote.getOaVoteEnd().length() > 0){ //结束时间不为空
						
						try {
							Date endTime = sdf.parse(oaVote.getOaVoteEnd());
							Date date = sdf.parse(time);
							
							if(date .after(endTime)){                        //当前时间大于结束时间，则停止投票
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.END.value);
								oaVote=(OaVote) OaVoteDao.save(oaVote);
							}
						} catch (ParseException e) {
							throw e;
						}
					}
				}else{                          //未开始
					if(oaVote.getOaVoteEnd() != null && oaVote.getOaVoteEnd().length()>0){ //结束时间不为空
						try {
							Date startTime = sdf.parse(oaVote.getOaVoteStart());
							Date endTime = sdf.parse(oaVote.getOaVoteEnd());
							Date date = sdf.parse(time);
							
							if(date.after(startTime) && date.before(endTime)){           //当前时间大于开始时间且小于结束时间，则为投票中
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}else if(date.after(endTime)){                               // 当前时间大于结束时间，直接修改为已结束
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.END.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}
						} catch (ParseException e) {
							throw e;
						}
						
					}else{                                               //结束时间为空
						try {
							Date startTime = sdf.parse(oaVote.getOaVoteStart());
							Date date = sdf.parse(time);
							
							if(date.after(startTime)){                             //当前时间大于开始时间，则开始投票
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}
						} catch (ParseException e) {
							throw e;
						}
					}
					
				}
				
			}
		}
		
		List<OaVote> votelist = OaVoteDao.findBySqlPage(OacommunHqlPack.packVoteQuery(vo, com,empId,depId),OaVote.class, pager);		
		return votelist;
	}
	
	/**
	 * 根据主键获取投票记录
	 */
	public OaVote getVoteByid(long id, long com) {
		OaVote vote = OaVoteDao.getByPK(id);
		return vote;
	}
	
	/**
	 * 统计投票数量(投票页面使用)
	 */
	public int listVoteCount(OaVote vote, long com,String empId,String depId) {
		int count = OaVoteDao.findBySqlCount(OacommunHqlPack.packVoteQuery(vote,com,empId,depId));
		return count;
	}
	
	/**
	 * 统计投票数量(管理界面使用)
	 */
	public int listVoteCountManager(OaVote vote, long com) {
		int count = OaVoteDao.findByHqlWhereCount(OacommunHqlPack.packVoteManagerQuery(vote,com));
		return count;
	}
	
	/**
	 * 新增投票记录
	 */
	public OaVote saveVote(OaVote vote,List<OaVoteOption> options ) {
		OaVote oaVote = (OaVote)OaVoteDao.save(vote);

		for (OaVoteOption op : options) {
			op.setOaVoteId((int)oaVote.getPrimaryKey());
			oaVoteOptionDao.save(op);
		}
		return oaVote;
	}
	
	/**
	 * 编辑投票记录
	 */
	public OaVote updateVote(OaVote vote, List<OaVoteOption> options) {
		//查询旧选项记录
		List<OaVoteOption>  op = oaVoteOptionDao.findByHqlWhere(" and model.oaVoteId = "+vote.getPrimaryKey()+"");
		
		//删除旧选项记录
		for (OaVoteOption o : op) {
			oaVoteOptionDao.remove(o);
		}	
		//保存新选项记录
		for (OaVoteOption p : options) {
			oaVoteOptionDao.save(p);
		}
		
		//如果是已结束的投票记录，则要删除投票状态记录（已投票人员的记录）。
		OaVote oaVote = OaVoteDao.getByPK(vote.getPrimaryKey());
		if(oaVote.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.END.value){
			List<OaVoteStatus> osList = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteRec="+vote.getPrimaryKey()+"");
			//删除记录
			for (OaVoteStatus s : osList) {
				oaVoteStatusDao.remove(s);
			}
		}
		
		//保存投票记录
		OaVote vo = (OaVote) OaVoteDao.save(vote);
		
		return vo;
	}
	
	/**
	 * 根据主键获取投票类型
	 */
	public SysLibraryInfo getVoteTypeByid(long id) {
		SysLibraryInfo lb = sysLibraryInfoDao.getByPK(id);
		return lb;
	}
	
	/**
	 * 根据版主人员主键获取版块信息
	 */
	public List<OaForums> getForumsByAdmin(OaForums forums, long l) {
		List<OaForums> list = oaForumsDao.findByHqlWhere(OacommunHqlPack.packForumsQuery(forums, l));
		return list;
	}
	
	/**
	 * 手动设置投票记录状态，不修改选项及其他关联信息
	 */
	public OaVote saveVoteNoOption(OaVote vote) {
		OaVote oaVote = (OaVote) OaVoteDao.save(vote);
		return oaVote;
	}
	
	/**
	 * 获取所有投票选项记录
	 */
	public List<OaVoteOption> getAlloptionByVote(long voteid,long com) {
		
		List<OaVoteOption>  list = oaVoteOptionDao.findByHqlWhere(" and model.oaVoteId = "+voteid+" and model.companyId ="+com);
		
		return list;
	}
	
	/**
	 * 根据主键获取选项信息
	 */
	public OaVoteOption getOptionByid(long id) {
		OaVoteOption op = oaVoteOptionDao.getByPK(id);
		return op;
	}
	
	/**
	 * 根据当前操作人员与投票主键获取人员投票状态信息
	 */
	public List<OaVoteStatus> getStatusByEmpAndVoteId(String emp, long voteid,long com) {
        List<OaVoteStatus>  os = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteEmp ='"+emp+"' and model.oaVoteRec ="+voteid+" and model.companyId="+com+"");
		return os;
	}
	
	/**
	 * 只根据投票主键获取人员投票状态信息
	 */
	public List<OaVoteStatus> getStatusByOnlyVoteId(long voteid,long com) {
		List<OaVoteStatus>  oss = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteRec ="+voteid+" and model.companyId="+com+"");
		return oss;
	}
	
	/**
	 * 更新投票选项记录
	 */
	public void updateOption(List<OaVoteOption> op) {
        for (OaVoteOption option : op) {
        	oaVoteOptionDao.save(option);
		}
	}
	
	/**
	 * 新增人员投票状态信息
	 */
	public OaVoteStatus saveVoteStatus(OaVoteStatus os) {
		OaVoteStatus s = (OaVoteStatus) oaVoteStatusDao.save(os);
		return s;
	}
	
	 /**
     * 获取所有投票记录
     */
	public List<OaVote> getAllVoteManager(OaVote vo, long com, Pager pager) {
		
		String time = UtilWork.getNowTime();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<OaVote>  list = OaVoteDao.findByHqlWhere(" and model.companyId = " + com);
		
		for (OaVote oaVote : list) {
			 //判断投票是否已结束 ，如果已结束则不修改任何数据
			if(oaVote.getOaVoteStatus() != null && oaVote.getOaVoteStatus() != EnumUtil.OA_VOTE_STATUS.END.value){ //操作投票中和未开始
				 
				if(oaVote.getOaVoteStatus() == EnumUtil.OA_VOTE_STATUS.VOTING.value){  //投票中
					if(oaVote.getOaVoteEnd() != null && oaVote.getOaVoteEnd().length() > 0){ //结束时间不为空
						
						try {
							Date endTime = sdf.parse(oaVote.getOaVoteEnd());
							Date date = sdf.parse(time);
							
							if(date .after(endTime)){                        //当前时间大于结束时间，则停止投票
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.END.value);
								oaVote=(OaVote) OaVoteDao.save(oaVote);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}else{                          //未开始
					if(oaVote.getOaVoteEnd() != null && oaVote.getOaVoteEnd().length()>0){                       //结束时间不为空
						try {
							Date startTime = sdf.parse(oaVote.getOaVoteStart());
							Date endTime = sdf.parse(oaVote.getOaVoteEnd());
							Date date = sdf.parse(time);
							
							if(date.after(startTime) && date.before(endTime)){           //当前时间大于开始时间且小于结束时间，则为投票中
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}else if(date.after(endTime)){                               // 当前时间大于结束时间，直接修改为已结束
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.END.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}else{
								//不执行操作
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
					}else{                                               //结束时间为空
						try {
							Date startTime = sdf.parse(oaVote.getOaVoteStart());
							Date date = sdf.parse(time);
							
							if(date.after(startTime)){                             //当前时间大于开始时间，则开始投票
								oaVote.setOaVoteStatus(EnumUtil.OA_VOTE_STATUS.VOTING.value);
								oaVote = (OaVote) OaVoteDao.save(oaVote);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
		}
		
		List<OaVote> votelist = OaVoteDao.findByHqlWherePage(OacommunHqlPack.packVoteManagerQuery(vo, com)+" order by model.oaVoteStart desc", pager);
		
		return votelist;
	}
	
	/**
	 * 查询非匿名投票状态记录（其他非投票发起者）
	 */
	public List<OaVoteStatus> getStatusByVoteId(long voteid, long com) {
		List<OaVoteStatus> list = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteRec = "+voteid +" and model.companyId = "+com +" and model.oaIsAnonymous ="+EnumUtil.OA_VOTE_IS_ANONYMOUS.NO.value);
		return list;
	}
	
	/**
	 * 查询投票状态记录（投票发起者）
	 * @param voteid
	 * @param com
	 * @return
	 */
	public List<OaVoteStatus> getStatusByVoteIdNoAnonymous(long voteid, long com){
	    List<OaVoteStatus> list = oaVoteStatusDao.findByHqlWhere(" and model.oaVoteRec = "+voteid+" and model.companyId = "+com);
		return list;
	}
	
	/**
	 * 批量设置共享
	 */
	public void shareSetChatter(long[] ids, String shareEmp,String empid) {
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		
		for (long l : ids) {
	    	OaChatters oaChatter = oaChattersDao.getByPK(l);
	    	oaChatter.setOaIsShare(EnumUtil.OA_COMMUNICATION_IS_SHARE.SHARE.value);
	    	oaChatter.setOaShareEmp(shareEmp);
	    	oaChatter.setLastmodiDate(time);
	    	oaChatter.setLastmodiId(empid);
	    	
	    	oaChattersDao.save(oaChatter);
		}
	}
	
	/**
	 * 查询已签入通讯手册记录（当前人员）
	 */
	public List<OaChatters> getSharedCommunication(String empid, long chatterId) {
		List<OaChatters>  list = oaChattersDao.findByHqlWhere(" and model.recordId = '"+empid+"' and model.oaCheckId = "+chatterId+"");
		return list;
	}
	
	/**
	 * 查同名分组记录数量
	 * @param name
	 * @param id
	 * @return
	 */
	public int getChatGroupsCountByname(String name,String empid,long id,long com){
		int count = 0;
		if(id>0){
			count = oaChatGroupsDao.findByHqlWhereCount(" and model.oaChatgpName='"+name + "' and model.primaryKey <>"+id+" and model.recordId='"+empid+"' and model.companyId="+com);
		}else{
			count = oaChatGroupsDao.findByHqlWhereCount(" and model.oaChatgpName='"+name+"' and model.recordId='"+empid+"' and model.companyId="+com);
		}
		
		return count;
	}
}
