package com.qiqiim.server.test;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.qiqiim.server.test.data.MessageData;

public class Testing {
	
	private static int thread_num = 5;//每秒并发数量
    private static int client_num = 1000;//客户链接数
    
    static Thread mThread = null;

	public static void startThread() {
		
			  /*mThread = new Thread() {
					public void run() {*/
						try {
							int x = (int)(Math.random()*90000+10000);
			                String currentuser = x+UUID.randomUUID().toString().replaceAll("-", "");
			                //链接socket服务
			                new QiQiImClient().connect(new MessageData().generateConnect(currentuser));
			                  
						} catch (Exception e) {
							e.printStackTrace();
						}
						/*	}
				};
				mThread.start();*/
		  
		
	}
    
	 
	public static void main(String[] args) {
		
	  //注意 请先修改QiQiImClient 里面的链接ip
		
	  //模拟用户链接测试
      ExecutorService exec = Executors.newCachedThreadPool();
        // thread_num个线程可以同时访问
        final Semaphore semp = new Semaphore(thread_num);
        // 模拟client_num个客户端访问
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可 
                        semp.acquire();
                        System.out.println("Thread并发数>>"+ NO);
                        try {
                        	startThread();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();  
    }
}
