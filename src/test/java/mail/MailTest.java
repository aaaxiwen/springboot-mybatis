package mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MailTest {
	@Autowired
	JavaMailSenderImpl mailSenderImpl;
	
	@Test
	public void test() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		//设置邮件
		simpleMailMessage.setSubject("黑龙找你");
		simpleMailMessage.setText("要不要请你吃饭？");
		simpleMailMessage.setTo("790126993@qq.com");
		simpleMailMessage.setFrom("574415104@qq.com");
		
		mailSenderImpl.send(simpleMailMessage);
	}
	@Test
	public void test01() throws Exception {
		MimeMessage mimeMessage = mailSenderImpl.createMimeMessage();//复杂的信息邮件
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);//辅佐类 true是开启multipart 相当于附件
		
		helper.setSubject("黑龙给你发来一个大红包");
		helper.setText("<b style='color:red'>红包在附件里面</b>");
		helper.setTo("790126993@qq.com");
		helper.setFrom("574415104@qq.com");
		//上传文件
		helper.addAttachment("timg.jpg", new File("C:\\Users\\admin\\Pictures\\timg.jpg"));
		helper.addAttachment("01.jpg", new File("C:\\Users\\admin\\Pictures\\01.jpg"));
		
		mailSenderImpl.send(mimeMessage);
	}
}
