package top.maplefix.common;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * @author : Maple
 * @description : IpConvert 定义域logback的转换器，用来生成ip
 * @date : 2020/12/22 11:24
 */
public class IpConvert extends ClassicConverter {

    private InetAddress ia;

    @Override
    public String convert(ILoggingEvent event) {
        String localname = "";
        String localip = "";
        try {

            ia = Inet6Address.getLocalHost();
            localname = ia.getHostName();
            localip = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return "server:" + localname + "IP:" + localip + " ";
        return localip;
    }
}
