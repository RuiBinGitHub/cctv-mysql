package com.springboot.bean;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailBean {

	@Resource
	private JavaMailSender sender;

	@Async
	public void sendMail(String mail, String code) {
		try {
			MimeMessage mimeMessage = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			StringBuffer text = new StringBuffer("【深圳麦斯迪埃】");
			text.append("您正在使用邮箱进行校验，效验码：<a href='#'>" + code + "</a>。");
			text.append("有效时间10分钟，超时请重新获取。(如非本人操作，请忽略该信息)");
			text.append("<p style='color:#999999'>该信息为系统自动发件，请勿回复!</p>");
			helper.setSubject("邮箱验证");
			helper.setText(text.toString(), true);
			helper.setFrom("SZMSDI@126.com");
			helper.setTo(mail);
			sender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
