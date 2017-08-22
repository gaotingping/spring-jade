package test_fun;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.mvw.jadetest.dao.UserDAO;
import com.mvw.jadetest.model.Person;

/**
 * CRUD测试
 * 
 * @author gaotingping
 */
@ContextConfiguration(locations = { "classpath:spring-common.xml"})
public class CRUDTest1 extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void test(){
		System.out.println(userDAO.getAll());
	}
	
	@Test
	public void test2(){
		for(int i=1;i<11;i++){
			Person p=new Person();
			p.setId(i);
			p.setName("name"+i);
			p.setBb(i);
			userDAO.up(p);
		}
	}
	
	@Test
	public void test3(){
		Person p=new Person();
		p.setName("name11");
		p.setBb(11);
		Integer id = userDAO.insert(p);
		System.err.println(id);
	}
	
	@Test
	public void test4(){
		
		List<Person> list=new ArrayList<>();
		
		for(int i=0;i<10;i++){
			
			Person p=new Person();
			p.setName("name"+i);
			p.setBb(i);
			
			list.add(p);
		}
		
		Object r = userDAO.batchInsert(list);
		System.out.println(r);
	}
}
