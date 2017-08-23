package net.qna.dao.users;

import net.qna.domain.users.User;

public interface UserDao {

	User findByID(String userId);

	void create(User user);

	void update(User user);

}