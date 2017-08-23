package net.qna.dao.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.qna.domain.board.Board;

import net.qna.domain.board.Reply;
import net.qna.domain.users.User;

public class MybatisBoardDao implements BoardDao{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	@Override
	public List<Board> Viewlist(int start, int end, String searchOption, String keyword) throws Exception{
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
	    map.put("start", start);
	    map.put("end", end);
		return sqlSession.selectList("BoardMapper.viewlist", map);
	}
	
	@Override
	public void create(Board vo)  throws Exception{
		 sqlSession.insert("BoardMapper.insert",vo);
	}
	
	@Override
	public void uphit(int bnum) throws Exception{
		sqlSession.update("BoardMapper.uphit", bnum);
	}
	
	@Override
	public Board read(int bnum) throws Exception{
		return sqlSession.selectOne("BoardMapper.view",bnum);
	}
	
	@Override
	public void update(Board vo) throws Exception{
		sqlSession.update("BoardMapper.updateArticle", vo);
	}
	
	@Override
	public void delete(int bnum) throws Exception{
		sqlSession.delete("BoardMapper.delete", bnum);
		
	}
	@Override
	public int countboard(String searchOption, String keyword) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("BoardMapper.countboard", map);
	}
	
	
	@Override
	public List<Board> popboard() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		
		return sqlSession.selectList("BoardMapper.popboard", map);
	}
	
	public Board detail(Integer bnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("BoardMapper.modifyview", bnum);
	}
	
	public String userlist() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("BoardMapper.userlist");
	}
	public String findByWriter(int bnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("BoardMapper.findBywriter",bnum);
	}
	

	


}
