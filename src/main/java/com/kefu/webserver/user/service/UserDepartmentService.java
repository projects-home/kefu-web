package com.kefu.webserver.user.service;

import java.util.List;
import java.util.Map;

import com.kefu.webserver.user.model.ImFriendUserData;
import com.kefu.webserver.user.model.UserDepartmentEntity;

/**
 * 部门
 * 
 * @author qiqiim
 * @email 1044053532@qq.com
 * @date 2017-11-27 09:38:52
 */
public interface UserDepartmentService {
	
	UserDepartmentEntity queryObject(Long id);
	
	List<UserDepartmentEntity> queryList(Map<String, Object> map);
	
    List<ImFriendUserData> queryGroupAndUser(); 
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserDepartmentEntity userDepartment);
	
	int update(UserDepartmentEntity userDepartment);
	
	int delete(Long id);
	
	int deleteBatch(Long[] ids);
}
