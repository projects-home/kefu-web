package com.kefu.webserver.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kefu.webserver.user.dao.UserMessageDao;
import com.kefu.webserver.user.model.UserMessageEntity;
import com.kefu.webserver.user.service.UserMessageService;

import java.util.List;
import java.util.Map;



@Service("userMessageServiceImpl")
public class UserMessageServiceImpl implements UserMessageService {
	@Autowired
	private UserMessageDao userMessageDao;
	
	@Override
	public UserMessageEntity queryObject(Long id){
		return userMessageDao.queryObject(id);
	}
	
	@Override
	public List<UserMessageEntity> queryList(Map<String, Object> map){
		return userMessageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userMessageDao.queryTotal(map);
	}
	
	@Override
	public void save(UserMessageEntity userMessage){
		userMessageDao.save(userMessage);
	}
	
	@Override
	public int update(UserMessageEntity userMessage){
		return userMessageDao.update(userMessage);
	}
	
	@Override
	public int delete(Long id){
		return userMessageDao.delete(id);
	}
	
	@Override
	public int deleteBatch(Long[] ids){
		return userMessageDao.deleteBatch(ids);
	}

	@Override
	public List<UserMessageEntity> getHistoryMessageList(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageList(map);
	}

	@Override
	public int getHistoryMessageCount(Map<String, Object> map) {
		return userMessageDao.getHistoryMessageCount(map);
	}

	@Override
	public List<UserMessageEntity> getOfflineMessageList(Map<String, Object> map) {
		 List<UserMessageEntity> result = userMessageDao.getOfflineMessageList(map);
		 //更新状态为已读状态
		 userMessageDao.updatemsgstate(map);
		 return result;
	}
	
}
