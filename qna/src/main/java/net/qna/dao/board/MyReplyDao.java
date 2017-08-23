package net.qna.dao.board;

import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import net.qna.domain.board.Reply;

public class MyReplyDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	private DataSource dataSource;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Reply> list(Integer bnum) {
		
		return sqlSession.selectList("replyMapper.listReply", bnum);
	}
	
	public void create(Reply vo) {
		sqlSession.insert("replyMapper.insertReply", vo);
		//sqlSession.update("replyMapper.setreGroup");
	}
	
	
    public void update(Reply vo) throws Exception {
        sqlSession.update("replyMapper.updateReply",vo);
    }
    // 댓글 삭제
   
    public void delete(Integer rnum)  {
    	sqlSession.delete("replyMapper.deleteReply", rnum);
 
    }
	public Reply detail(Integer rnum) {
		return sqlSession.selectOne("replyMapper.replymodifyview", rnum);
	}
	public void createcomment(Reply vo) {
		sqlSession.insert("replyMapper.insertReplyComment",vo);
	}

	public void stepshape(Reply vo){
		sqlSession.update("replyMapper.replyStepShape", vo);	
	}
}
