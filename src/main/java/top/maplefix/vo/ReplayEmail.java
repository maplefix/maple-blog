package top.maplefix.vo;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maple
 * @description 邮件回复实体
 * @date 2020/3/13 10:44
 */
@Data
public class ReplayEmail implements Serializable {
    /**
     * reply Create time
     */
    private String createDate;
    /**
     * blog title
     */
    private String title;
    /**
     * reply name
     */
    private String replyName;
    /**
     * name
     */
    private String name;
    /**
     * origin content
     */
    private String originContent;
    /**
     * reply content
     */
    private String replyContent;
    /**
     * url
     */
    private String url;
    /**
     * header img
     */
    private String headerImg;

    public Map<String, Object> toMap() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, Object> map = new HashMap<>(16);
        map.put("createTime", simpleDateFormat.format(createDate));
        map.put("title", title);
        map.put("replyName", replyName);
        map.put("originContent", originContent);
        map.put("replyContent", replyContent);
        map.put("url", url);
        return map;
    }
}
