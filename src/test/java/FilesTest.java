import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FilesTest extends SpringTestCase {
	
	@Test
	public void test() throws IOException {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();  
        //从获取RequestAttributes中获取HttpServletRequest的信息  
		//((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);  
        //如果要获取Session信息的话，可以这样写：  
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);  
        
//        File file = new File("c:/test","hole");
//        String absolutePath = file.getAbsolutePath();
        
        String realPath = request.getServletContext().getRealPath("/static/file");       
		String content = "Hello World !!";
		byte[] data = Files.readAllBytes(Paths.get("C:\\Users\\admin\\Pictures\\7.jpg"));
		Path path = Paths.get(realPath.concat("/test1.jpg"));
		if (!Files.isWritable(path)) {
            Files.createDirectories(path);
        }
		Files.write(path,data);
	}
}
