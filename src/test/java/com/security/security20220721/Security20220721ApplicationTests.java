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


	@Autowired // �Զ�װ��ѧ������ʵ��
	private StudentConfig studentConfig;

    @Autowired
	private ServiceAImpl serviceAImpl;

	@Test
	public void testStudentConfig() {
		// ���ѧ������ʵ����Ϣ
		System.out.println(studentConfig);
	}
	@Test
	public void testserviceAImpl() {
		// ���ѧ������ʵ����Ϣ
		System.out.println(serviceAImpl);
	}

}
