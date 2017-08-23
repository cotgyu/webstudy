package net.qna.dao.fboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.qna.domain.board.Board;
import net.qna.domain.board.FBoard;

public class FMyBoardDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
	public List<FBoard> Viewlist(int start, int end, String searchOption, String keyword) throws Exception{
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
	    map.put("start", start);
	    map.put("end", end);
		return sqlSession.selectList("FBoardMapper.viewlist", map);
	}
	
	
	public void create(FBoard vo)  throws Exception{
		 sqlSession.insert("FBoardMapper.insert",vo);
	}
	
	
	public void uphit(int bnum) throws Exception{
		sqlSession.update("FBoardMapper.uphit", bnum);
	}
	
	
	public FBoard read(int bnum) throws Exception{
		return sqlSession.selectOne("FBoardMapper.view",bnum);
	}
	
	
	public void update(FBoard vo) throws Exception{
		sqlSession.update("FBoardMapper.updateArticle", vo);
	}
	
	
	public void delete(int bnum) throws Exception{
		sqlSession.delete("FBoardMapper.delete", bnum);
		
	}
	
	public int countboard(String searchOption, String keyword) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("FBoardMapper.countboard", map);
	}
	
	public List<FBoard> popboard() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		
		return sqlSession.selectList("FBoardMapper.popboard", map);
	}
	public FBoard detail(Integer bnum) {
		
		return sqlSession.selectOne("FBoardMapper.modifyview", bnum);
	}
	
	public String findByWriter(int bnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("FBoardMapper.findByWriter", bnum);
	}

	
}
