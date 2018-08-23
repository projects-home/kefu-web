package com.kefu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * 获取当前系统信息
 */
public class SystemInfo {
    // 当前实例
    private static SystemInfo currentSystem = null;
    private InetAddress localHost = null;

    private SystemInfo() {
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 单例模式获取对象
     * 
     * @return
     */
    public static SystemInfo getInstance() {
        if (currentSystem == null)
            currentSystem = new SystemInfo();
        return currentSystem;
    }

    /**
     * 本地IP
     * 
     * @return IP地址
     */
    public String getIP() {
        String ip = localHost.getHostAddress();
        return ip;
    }

    /**
     * 获取用户机器名称
     * 
     * @return
     */
    public String getHostName() {
        return localHost.getHostName();
    }
 
    /**
     * 获取Mac地址
     * 
     * @return Mac地址，例如：F0-4D-A2-39-24-A6
     */
    public String getMac() {
        NetworkInterface byInetAddress;
        try {
            byInetAddress = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = byInetAddress.getHardwareAddress();
            return getMacFromBytes(hardwareAddress);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前系统名称
     * 
     * @return 当前系统名，例如： windows xp
     */
    public String getSystemName() {
        Properties sysProperty = System.getProperties();
        // 系统名称
        String systemName = sysProperty.getProperty("os.name");
        return systemName;
    }
    
    /**
     * 获取当前系统名称
     * 
     * @return 当前系统名，例如： windows xp
     */
    public String getSystem() {
        Properties sysProperty = System.getProperties();
        // 系统名称
        String systemName = sysProperty.getProperty("sun.desktop");
        return systemName;
    }
    

    private String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}