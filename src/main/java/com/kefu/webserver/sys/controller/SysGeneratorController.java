/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.kefu.webserver.sys.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kefu.constant.Constants;
import com.kefu.webserver.base.controller.BaseController;
import com.kefu.webserver.sys.service.SysGeneratorService;
import com.kefu.webserver.util.Query;

/**
 * 代码生成器 
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController extends BaseController{
	@Autowired
	private SysGeneratorService sysGeneratorServiceimpl;
	
	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params){
		return "sys/generatorlist";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list", produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<Map<String, Object>>  list = sysGeneratorServiceimpl.queryList(query);
		int total =  sysGeneratorServiceimpl.queryTotal(query);
		return putMsgToJsonString(Constants.WebSite.SUCCESS,"",total,list);
	}
	
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(@RequestParam String[] tables,HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取表名，不进行xss过滤
		byte[] data = sysGeneratorServiceimpl.generatorCode(tables);
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"sourcecode.zip\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  
	}
}
