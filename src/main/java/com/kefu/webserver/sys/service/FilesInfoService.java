package com.kefu.webserver.sys.service;

import org.springframework.web.multipart.MultipartFile;

public interface FilesInfoService {
 
	public int saveUserPicture(MultipartFile file, Long uid, String path);
	
	public String savePicture(MultipartFile file, String newname, String path);
	
	public String saveFiles(MultipartFile file,String newname, String path) ;
	
}
