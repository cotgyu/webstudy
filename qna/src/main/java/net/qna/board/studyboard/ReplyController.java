package net.qna.board.studyboard;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.qna.dao.board.MyReplyDao;
import net.qna.domain.board.Board;
import net.qna.domain.board.Reply;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    
    @Autowired
    MyReplyDao replydao;
    
    // 댓글 입력
    @RequestMapping(value="insert.do", method=RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute Reply vo, HttpSession session,@RequestParam int bnum, @RequestParam String replytext){
    	
    	ModelAndView mav = new ModelAndView();
    	
        String userId = (String) session.getAttribute("userId");
      //int rnum = vo.getRnum();
        //vo.setRegroup(rnum);
        
        vo.setReplyer(userId);
        replydao.create(vo);
        bnum = vo.getBnum();
        
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/board/view?bnum={bnum}");
        
        return mav;
  
    }
      
    // 댓글 목록(@Controller방식 : veiw(화면)를 리턴)
    @RequestMapping("list.do")
    public ModelAndView list(@RequestParam int bnum, ModelAndView mav){
        List<Reply> list = replydao.list(bnum);
        // 뷰이름 지정
        mav.setViewName("board/replylist");	
        // 뷰에 전달할 데이터 지정
        mav.addObject("bnum", bnum);
        mav.addObject("list", list);
        // replyList.jsp로 포워딩
        return mav;
    }
      
    // 댓글 목록(@RestController Json방식으로 처리 : 데이터를 리턴)
    @RequestMapping("listJson.do")
    @ResponseBody // 리턴데이터를 json으로 변환(생략가능)
    public List<Reply> listJson(@RequestParam int bnum){
        List<Reply> list = replydao.list(bnum);
        return list;
    }
    
    @RequestMapping("delete")
    public ModelAndView replydelete(@RequestParam int rnum, @RequestParam int bnum) throws Exception{
    	replydao.delete(rnum);
    	
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/board/view?bnum={bnum}");
    	
    	return mav;
    }
    
    @RequestMapping(value="update", method=RequestMethod.POST)
    public ModelAndView replyupdate(@ModelAttribute Reply vo,  @RequestParam int bnum, @RequestParam int rnum, @RequestParam String replytext) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	
    	replydao.update(vo);
    	
    	mav.addObject("bnum", bnum);
    	mav.setViewName("redirect:/board/view?bnum={bnum}");
    		
    	return mav; 	
    }
//댓글 수정창으로 연결     
    @RequestMapping(value="/detail/{rnum}", method=RequestMethod.GET)
    public ModelAndView replyDetail(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        Reply vo = replydao.detail(rnum);
        // 뷰이름 지정
        mav.setViewName("board/replymodify");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        // replyDetail.jsp로 포워딩
        return mav;
    }
  
  //댓글 수정창으로 연결     
    @RequestMapping(value="/commentwrite/{rnum}", method=RequestMethod.GET)
    public ModelAndView commentwrite(@PathVariable("rnum") Integer rnum, ModelAndView mav){
        Reply vo = replydao.detail(rnum);
        // 뷰이름 지정
        mav.setViewName("board/replycomment");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        // replyDetail.jsp로 포워딩
        return mav;
    }    
    
    @RequestMapping(value="comment")
    public ModelAndView replycomment(@ModelAttribute Reply vo, HttpSession session, @RequestParam int bnum, 
    		@RequestParam String replytext, @RequestParam int regroup, @RequestParam int restep, @RequestParam int reindent){
    	ModelAndView mav = new ModelAndView();
    	vo.setRegroup(regroup);
    	vo.setRestep(restep);
    	vo.setReindent(reindent);
    	replydao.stepshape(vo); 
    	
    	String userId = (String)session.getAttribute("userId");
    	vo.setReplyer(userId);
    	
    	replydao.createcomment(vo); //코멘트생성
    	//실행하고 보여줄 페이지
    	bnum = vo.getBnum();        
        mav.addObject("bnum", bnum);
        mav.setViewName("redirect:/board/view?bnum={bnum}");
        
    	return mav;
    }
}