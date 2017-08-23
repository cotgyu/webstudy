package net.qna.dao.gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.qna.domain.board.Board;
import net.qna.domain.gallery.gallery;

public class MyGalleryDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	public int countboard(String searchOption, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		
		return sqlSession.selectOne("GalleryMapper.countboard", map);
	}

	public List<Board> Viewimglist(int start, int end, String searchOption, String keyword) {
		//검색 옵션, 키워드 맵에 저장
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("searchOption", searchOption);
				map.put("keyword", keyword);
				// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
			    map.put("start", start);
			    map.put("end", end);
				return sqlSession.selectList("GalleryMapper.viewimglist", map);
	}
	
	
	public void imageinsert(gallery vo) {
		sqlSession.insert("GalleryMapper.insertimage",vo);
		
	}
	public void imageup(int imgid) {
		sqlSession.update("GalleryMapper.upimage",imgid);
		
	}
	public void imagedelete(int imgid) {
		sqlSession.delete("GalleryMapper.deleteimage",imgid);
		
	}
	public List<gallery> uplist(int start, int end, String searchOption, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값을 맵에 
	    map.put("start", start);
	    map.put("end", end);
		return sqlSession.selectList("GalleryMapper.uplist", map);
	}
	public List<gallery> poplist() {
		Map<String, String> map = new HashMap<String, String>();
		
		return sqlSession.selectList("GalleryMapper.popimglist", map);
	}
	

}
