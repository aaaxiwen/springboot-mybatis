import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.spring.springboot.dao.UserDao;
import org.spring.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

public class MybatispulsTest extends SpringTestCase {
	@Autowired
	UserDao userDao;
	
	
	@Test
	public void test() {
		int result = 0;
		User user = new User();
		user.setName("冰锐");
		user.setUserRole("打怪兽");
//		for(int i=0;i<10;i++) {
//			result = userDao.insert(user);
//		}
		Page<User> page = new Page<User>();
		page.setCurrent(1);
		page.setSize(5);
//		List<User> userList = userDao.selectPage(new Page<User>(1,5), new EntityWrapper<User>().eq("name", "萧喜文"));
		List<User> userList = userDao.selectPage(page, new EntityWrapper<User>().eq("name", "冰锐"));
		Iterator<User> iterator = userList.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getName());
		}
		System.out.println(page.getTotal());
		System.out.println(page.getPages());
		
	}
}
