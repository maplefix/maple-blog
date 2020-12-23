package top.maplefix.common;

import ch.qos.logback.core.PropertyDefinerBase;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author : Maple
 * @description : IpConvert 定义域logback的转换器，用来生成ip
 * @date : 2020/1/16 9:54
 */
public class IpPropertyDefiner extends PropertyDefinerBase {


    @Override
    public String getPropertyValue() {
        String info;
        InetAddress netAddress = getInetAddress();
        //获取主机名 linux多网卡无法根据环境指定具体网卡，采用Inet6Address ，需要正确配置linux网卡
        info = getHostName(netAddress);
        return info;

    }


    public static InetAddress getInetAddress() {
        try {
            return Inet6Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHostName(InetAddress netAddress) {
        if (null == netAddress) {
            return null;
        }
        String ip = netAddress.getHostName()+"-"+netAddress.getHostAddress();
        return ip;
    }

}
