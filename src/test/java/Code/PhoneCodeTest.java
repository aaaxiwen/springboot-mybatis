package Code;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.springboot.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.github.qcloudsms.httpclient.PoolingHTTPClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PhoneCodeTest {
	
	// 短信应用SDK AppID
	int appid = 1400148612; // 1400开头    合家欢腾讯云

	// 短信应用SDK AppKey
	String appkey = "fd7eb8860f2b3b57350c18145162b120";

	// 需要发送短信的手机号码
	String[] phoneNumbers = {"13640438654", "12345678902", "12345678903"};

	// 短信模板ID，需要在短信应用中申请
	int templateId = 207797; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

	// 签名
	String smsSign = "合家欢私人管家"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`
	
	
	@Test
	public void test01() throws org.json.JSONException {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
//		        templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		    
		    SmsSingleSenderResult result = ssender.send(0, "86", phoneNumbers[0],
		            "5678为您的注册验证码，请于2分钟内填写。如非本人操作，请忽略本短信。", "", "");
		    System.out.println(result);
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {

        int appid = 122333333;
        String appkey = "9ff91d87c2cd7cd0ea762f141975d1df37481d48700d70ac37470aefc60f9bad";
        String[] phoneNumbers = {
            "21212313123", "12345678902", "12345678903",
            "21212313124", "12345678903", "12345678904",
            "21212313125", "12345678904", "12345678905",
            "21212313126", "12345678905", "12345678906",
            "21212313127", "12345678906", "12345678907",
        };
        
        // 创建一个连接池httpclient, 并设置最大连接量为10
        PoolingHTTPClient httpclient = new PoolingHTTPClient(10);

        // 创建SmsSingleSender时传入连接池http client
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey, httpclient);

        // 创建线程
        SmsThread[] threads = new SmsThread[phoneNumbers.length];
        for (int i = 0; i < phoneNumbers.length; i++) {
            threads[i] = new SmsThread(ssender, "86", phoneNumbers[i], "您验证码是：5678");
        }

        // 运行线程
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        // join线程
        for (int i = 0; i < threads.length; i++) {
            try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        // 关闭连接池httpclient
        httpclient.close();
    }
}
