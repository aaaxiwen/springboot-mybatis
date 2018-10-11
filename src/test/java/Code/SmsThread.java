package Code;

import java.io.IOException;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

class SmsThread extends Thread {

    private final SmsSingleSender sender;
    private final String nationCode;
    private final String phoneNumber;
    private final String msg;

    public SmsThread(SmsSingleSender sender, String nationCode, String phoneNumber, String msg) {
        this.sender = sender;
        this.nationCode = nationCode;
        this.phoneNumber = phoneNumber;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            SmsSingleSenderResult result = sender.send(0, nationCode, phoneNumber, msg, "", "");
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
        } catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
