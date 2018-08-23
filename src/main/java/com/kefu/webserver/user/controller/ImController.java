package com.kefu.webserver.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.kefu.constant.Constants;
import com.kefu.server.model.MessageWrapper;
import com.kefu.server.model.proto.MessageBodyProto;
import com.kefu.server.model.proto.MessageProto;
import com.kefu.server.proxy.MessageProxy;
import com.kefu.server.session.SessionManager;
import com.kefu.webserver.base.controller.BaseController;
import com.kefu.webserver.dwrmanage.DwrUtil;
import com.kefu.webserver.dwrmanage.connertor.DwrConnertor;
import com.kefu.webserver.sys.service.FilesInfoService;
import com.kefu.webserver.user.model.ImFriendUserData;
import com.kefu.webserver.user.model.ImFriendUserInfoData;
import com.kefu.webserver.user.model.ImGroupUserData;
import com.kefu.webserver.user.model.ImUserData;
import com.kefu.webserver.user.model.UserAccountEntity;
import com.kefu.webserver.user.model.UserInfoEntity;
import com.kefu.webserver.user.model.UserMessageEntity;
import com.kefu.webserver.user.service.UserAccountService;
import com.kefu.webserver.user.service.UserDepartmentService;
import com.kefu.webserver.user.service.UserMessageService;
import com.kefu.webserver.util.Pager;
import com.kefu.webserver.util.Query;
@Controller
public class ImController extends BaseController{
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private UserAccountService userAccountServiceImpl;
	@Autowired
	private UserDepartmentService userDepartmentServiceImpl;
	@Autowired
	private FilesInfoService filesInfoServiceImpl;
	@Autowired
	private UserMessageService userMessageServiceImpl;
	@Autowired
	private DwrConnertor dwrConnertorImpl;
	@Autowired
	private MessageProxy proxy;
	
	/**
	 * 单聊
	 */
	@RequestMapping("/chat")
	public String chat(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "chat";
	}
	/**
	 * 群聊
	 */
	@RequestMapping("/groupchat")
	public String group(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "groupchat";
	}
	/**
	 * 机器人
	 */
	@RequestMapping("/bot")
	public String list(@RequestParam Map<String, Object> params,HttpServletRequest request){
		request.setAttribute("allsession", sessionManager.getSessions());
		return "bot";
	}
	
	/**
	 * 登录IM
	 */
	@RequestMapping("/login")
	public String login(@RequestParam Map<String, Object> params,HttpServletRequest request){
		Query query = new Query(params);
		UserAccountEntity userAccount = userAccountServiceImpl.validateUser(query);
		if(userAccount!=null){
			setLoginUser(userAccount);
			String template = check(request);
			if(template.equals(Constants.ViewTemplateConfig.mobiletemplate)){
				return "layimmobile";
			}
			return "layim";
		}
		return "redirect:login.jsp";
	}
	
	
	/** 
	 *  取得所有聊天用户
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getusers", produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getAllUser(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		    // 数据格式请参考文档  http://www.layui.com/doc/modules/layim.html  
			if(getLoginUser()!=null){
				
				UserInfoEntity  user = getLoginUser().getUserInfo();	
				ImFriendUserInfoData  my = new ImFriendUserInfoData();
				my.setId(user.getUid());
				my.setAvatar(user.getProfilephoto());
				my.setSign(user.getSignature());
				my.setUsername(user.getName());
				my.setStatus("online");
				
				//模拟群信息
				ImGroupUserData  group = new ImGroupUserData();
				group.setAvatar("http://tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg");
				group.setId(1L);
				group.setGroupname("公司群");
				List<ImGroupUserData>  groups = new ArrayList<ImGroupUserData>();
				groups.add(group);
				
				Map map = new HashMap();
				ImUserData  us = new ImUserData();
				us.setCode("0");
				us.setMsg("");
				map.put("mine", my);
				map.put("group",groups);
				//获取用户分组 及用户
				List<ImFriendUserData> friends = userDepartmentServiceImpl.queryGroupAndUser();
				map.put("friend",friends);
				us.setData(map);
				return JSONArray.toJSON(us);
			}else{
				return JSONArray.toJSON("");
			}
	}
	
	
	/** 
	 * 图片上传
	 * @param file
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgupload", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadImgFile(@RequestParam MultipartFile  file,
			HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		
		    UserAccountEntity   u = getLoginUser();
			Long uid = u.getId();
		 	String path=request.getSession().getServletContext().getRealPath("upload/img/temp/");
		 	String files = filesInfoServiceImpl.savePicture(file,uid.toString()+UUID.randomUUID().toString(), path);
		 	Map<String,Object> map = new HashMap<String,Object>();
		 	 Map<String,String> submap = new HashMap<String,String>();
			 if(files.length()>0){
					map.put("code","0");
					map.put("msg", "上传过成功");  
				    submap.put("src", request.getContextPath()+"/upload/img/temp/"+files+"?"+new Date().getTime());
			  }else{
				  submap.put("src", "");
				  map.put("code","1");
				  map.put("msg", "上传过程中出现错误，请重新上传");  
			  }
			  map.put("data",submap);
			  return  JSONArray.toJSON(map);
	}
	
	
	/** 
	 * 文件上传
	 * @param file
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fileupload", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadAllFile(@RequestParam MultipartFile  file,
			HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception {
		
		    UserAccountEntity  u = getLoginUser();
			Long uid = u.getId();
		 	String path=request.getSession().getServletContext().getRealPath("upload/file/temp/");
		 	String files = filesInfoServiceImpl.saveFiles(file, uid.toString()+UUID.randomUUID().toString(), path);
		 	Map<String,Object> map = new HashMap<String,Object>();
		 	Map<String,String> submap = new HashMap<String,String>();
			 if(files.length()>0){
					map.put("code","0");
					map.put("msg", "上传过成功");  
				    submap.put("src", request.getContextPath()+"/upload/file/temp/"+files+"?"+new Date().getTime());
				    submap.put("name", file.getOriginalFilename());
			  }else{
				  submap.put("src", "");
				  map.put("code","1");
				  map.put("msg", "上传过程中出现错误，请重新上传");  
			  }
			  map.put("data",submap);
			  return  JSONArray.toJSON(map);
	}
	
	/**
	 * 模拟最新系统消息
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String userMessage(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		
		List<UserMessageEntity>  list = new ArrayList<UserMessageEntity>();
		UserMessageEntity  msg = new UserMessageEntity();
		msg.setContent("模拟系统消息");
		msg.setCreatedate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		list.add(msg);
		UserMessageEntity  msgTwo = new UserMessageEntity();
		msgTwo.setContent("模拟系统消息1");
		msgTwo.setCreatedate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		list.add(msgTwo);
		request.setAttribute("msgList", list);
		return "message";
	}
	/**
	 * 取得离线消息
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getofflinemsg", produces="text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public Object userMessageCount(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		if(getLoginUser()!=null){
			map.put("receiveuser", getLoginUser().getId().toString());
		}else{
			map.put("receiveuser", request.getSession().getId());
		}
		List<UserMessageEntity> list = userMessageServiceImpl.getOfflineMessageList(map);
		return JSONArray.toJSON(list);
	} 
	
	/**
	 * 聊天记录
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/historymessageajax", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object userHistoryMessages(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("page", getSkipToPage());
		map.put("limit", getPageSize());
		map.put("senduser", getLoginUser().getId());
		map.put("receiveuser", Long.parseLong(request.getParameter("id")));
		List<UserMessageEntity> list = userMessageServiceImpl.getHistoryMessageList(new Query(map));
		Map<String,List<UserMessageEntity>>  resultMap = new HashMap();
		resultMap.put("data", list);
		return JSONArray.toJSON(resultMap);
	}
	
	/**
	 * 聊天记录页面
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/historymessage", method = RequestMethod.GET)
	public String userHistoryMessagesPage(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("senduser", getLoginUser().getId());
		map.put("receiveuser", Long.parseLong(request.getParameter("id")));
		int totalsize = userMessageServiceImpl.getHistoryMessageCount(map);
		Pager pager = new Pager(getSkipToPage(),getPageSize(),totalsize);
		request.setAttribute("pager", pager);
		return "/historymessage";
	} 
	
	
	@RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
	@ResponseBody
	public Object sendMsg(HttpServletResponse response,HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws Exception{
		String sessionid = request.getSession().getId();
		if(getLoginUser()!=null){
			sessionid = getLoginUser().getId().toString();
		}
		MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
        builder.setCmd(Constants.CmdType.MESSAGE);
        builder.setSender(sessionid);
        builder.setReceiver((String)request.getParameter("receiver"));
        builder.setMsgtype(Constants.ProtobufType.REPLY);
        MessageBodyProto.MessageBody.Builder  msg =  MessageBodyProto.MessageBody.newBuilder();
        msg.setContent((String)request.getParameter("content")); 
        builder.setContent(msg.build().toByteString());
        MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionid, builder.build());
		dwrConnertorImpl.pushMessage(sessionid, wrapper);
		return JSONArray.toJSON("");
	} 
	
	 
	
}
