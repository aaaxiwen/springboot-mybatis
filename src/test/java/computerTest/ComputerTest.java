package computerTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.spring.springboot.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ComputerTest {
	
	
	@Test
	public void test1() {
		String ss;
		int n;
		Scanner scStr = new Scanner(System.in);
		ss = scStr.next();
		Scanner scByte =  new Scanner(System.in);
		n = scByte.nextInt();
		getValue(ss, n);		
	}
	
	public void test2() {
		BufferedReader[] buf = new BufferedReader[10];//申请缓存数组
		int[] array = new int[10];
		for(int i = 0;i < 10;i++) {
			System.out.println("请输入第"+(i+1)+"个数");
			buf[i] = new BufferedReader(new InputStreamReader(System.in));
			try {
				array[i] = Integer.parseInt(buf[i].readLine());
			} catch (Exception e) {
				System.out.println("不是数字");
			}
		}
		System.out.println("原数组是");
		for(int a:array) {
			System.out.print(a+" ");
		}
		int s;
		for(int i = 0;i < 5;i++) {
			s = array[i];
			array[i] = array[9-i];
			array[9-i] = s;
		}
		System.out.println("交互后数组是");
		for(int a:array) {
			System.out.print(a+" ");
		}
	}

	public static String[] getValue(String ss,int n) {
		String[] sss = new String[ss.length()];
		for(int i = 0;i<sss.length;i++) {
			sss[i] = ss.substring(i, i+1);
		}
		int count = 0;
		String m = "[\u4e00-\u9fa5]";
		for(int i = 0;i < sss.length;i++) {
			if(sss[i].matches(m)) {
				count = count + 2;
			}else {
				count = count + 1;
			}
			if(count <= n) {
				System.out.print(sss[i]);
			}else {
				return null;
				
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		ComputerTest computerTest = new ComputerTest();
		computerTest.test2();
		
	}
}
