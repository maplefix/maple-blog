package top.maplefix.service;

import top.maplefix.vo.ReplayEmail;

/**
 * @author Maple
 * @description 邮件发送service
 * @date 2020/1/22 19:39
 */
public interface EmailService {

    /**
     * 发送邮件
     * @param to 发送给
     * @param title 标题
     * @param content 内容
     */
    void sendHtmlMail(String to, String title, String content);

    /**
     * 回复邮件
     * @param url 邮寄地址
     * @param htmlContent HTML内容
     * @param nickName 昵称
     * @param email 原始邮件
     * @param replayEmail 回复邮件
     */
    void sendReplyEmail(String url, String htmlContent, String nickName, String email, ReplayEmail replayEmail);

    /**
     * 友链申请结果回复
     * @param success 成功
     * @param reason 原因
     */
    void sendLinkApplyResult(boolean success,String reason);
}
