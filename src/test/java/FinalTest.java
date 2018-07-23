import org.junit.Test;
import org.spring.springboot.domain.User;

public class FinalTest extends SpringTestCase {

	@Test
	public void test() {
		User user = new User();
		final User user2 = new  User();
		user2.setName("final修饰对象只能保证不能被第二次指向，指向的实现是可以修改的");
		System.out.println(user2.getName());
	}
}
