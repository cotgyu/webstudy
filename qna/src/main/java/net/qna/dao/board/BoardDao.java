package net.qna.dao.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.qna.domain.board.Board;

public interface BoardDao {
	void create(Board vo)  throws Exception;
	
	void uphit(int bnum)  throws Exception;

	Board read(int bnum) throws Exception;

	List<Board> Viewlist(int start, int end, String searchOption, String keyword)  throws Exception;

	void update(Board vo) throws Exception;

	void delete(int bnum) throws Exception;

	int countboard(String searchOption, String keyword) throws Exception;

	List<Board> popboard() throws Exception;


}
