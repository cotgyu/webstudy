package net.qna.dao.fboard;

import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.qna.domain.board.FReply;


public class FMyReplyDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<FReply> list(Integer bnum) {
		
		return sqlSession.selectList("FreplyMapper.listReply", bnum);
	}
	
	public void create(FReply vo) {
		sqlSession.insert("FreplyMapper.insertReply", vo);
		//sqlSession.update("replyMapper.setreGroup");
	}
	
	
    public void update(FReply vo) throws Exception {
        sqlSession.update("FreplyMapper.updateReply",vo);
    }
    // 댓글 삭제
   
    public void delete(Integer rnum)  {
    	sqlSession.delete("FreplyMapper.deleteReply", rnum);
 
    }
	public FReply detail(Integer rnum) {
		return sqlSession.selectOne("FreplyMapper.replymodifyview", rnum);
	}
	public void createcomment(FReply vo) {
		sqlSession.insert("FreplyMapper.insertReplyComment",vo);
	}

	public void stepshape(FReply vo){
		sqlSession.update("FreplyMapper.replyStepShape", vo);	
	}
}
