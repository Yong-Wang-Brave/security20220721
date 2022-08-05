package com.security.security20220721;


import com.security.security20220721.config.readConfig.readMyConfig.one.StudentConfig;
import com.security.security20220721.config.readConfig.readMyConfig.two.ServiceAImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Security20220721ApplicationTests {


	@Autowired // 自动装配学生配置实体
	private StudentConfig studentConfig;

    @Autowired
	private ServiceAImpl serviceAImpl;

	@Test
	public void testStudentConfig() {
		// 输出学生配置实体信息
		System.out.println(studentConfig);
	}
	@Test
	public void testserviceAImpl() {
		// 输出学生配置实体信息
		System.out.println(serviceAImpl);
	}

}
