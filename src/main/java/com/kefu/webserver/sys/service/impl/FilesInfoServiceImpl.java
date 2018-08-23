package com.kefu.webserver.sys.service.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kefu.webserver.sys.service.FilesInfoService;
import com.kefu.webserver.util.FileUtil;
@Service("filesInfoServiceImpl")
public class FilesInfoServiceImpl implements FilesInfoService {
	 
	
	
	
	public int saveUserPicture(MultipartFile file, Long uid, String path) {
		int result = 0;
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  result;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateImgType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   File tempFile = new File(path, "B"+ uid+".jpg"); 
	           FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile );  
	     	   result =1;
	     	   //大图裁剪
		     	 
	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return result;
	}
	
	public String savePicture(MultipartFile file, String newname, String path) {
		String fileUrl = "";
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  fileUrl;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateImgType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   File tempFile = new File(path,  newname+"."+name[1]); 
	           FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile );  
	           fileUrl = newname+"."+name[1];
	     	   //大图裁剪
		     	 
	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return fileUrl;
	}
	
	public String saveFiles(MultipartFile file,String newname, String path) {
		String fileUrl = "";
		if(StringUtils.isEmpty(file.getOriginalFilename())){
    	   return  fileUrl;
    	} 
    	String[]  name=	FileUtil.splitFileName(file.getOriginalFilename());
    	boolean b =  FileUtil.validateFileType(name[1]);
    	//文件类型验证通过 
    	if(b){
    	   try{
    		   fileUrl = newname+"."+name[1];
    		   File tempFile = new File(path,  newname+"."+name[1]); 
	           FileUtils.copyInputStreamToFile(file.getInputStream(),tempFile );  
	     	}catch(Exception e){
	     		e.printStackTrace();
	     	} 
    	} 
		return fileUrl;
	} 

}
