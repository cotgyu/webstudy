import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.qna.dao.users.UserDao;
import net.qna.domain.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDao userDao;
		
	
	
	@Test
	public void findById() {
		User user = userDao.findByID("javajigi");
		log.debug("User: {}" ,user);
	}
	
	@Test
	public void create() throws Exception{
	//	User user =new User("sanjigi", "password", "산지기", "sanjigi@gmail.com");
	//	userDao.create(user);
	//	User actual = userDao.findByID(user.getUserId());
	//	assertThat(actual, is(user));
	}

}
