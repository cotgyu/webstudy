package net.qna.home.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.qna.dao.board.MybatisBoardDao;
import net.qna.dao.fboard.FMyBoardDao;
import net.qna.dao.gallery.MyGalleryDao;
import net.qna.domain.board.Board;
import net.qna.domain.board.FBoard;
import net.qna.domain.gallery.gallery;

@Controller
@RequestMapping("/home")
public class HomeViewController {
	@Autowired
	private MybatisBoardDao boardDao;
	@Autowired
	private FMyBoardDao fboardDao;
	@Autowired
	MyGalleryDao galleryDao;
	
	@RequestMapping("/mainview")
	public ModelAndView list() throws Exception{
		
		
		List<Board> poplist = boardDao.popboard();
		List<FBoard> popFlist = fboardDao.popboard();
		List<gallery> popImglist = galleryDao.poplist();
		//데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("poplist", poplist);
		map.put("popFlist", popFlist);
		map.put("popImglist",popImglist);
		
		//모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
        mav.setViewName("home"); // 뷰를 list.jsp로 설정
       
        return mav; // list.jsp로 List가 전달된다.
	}
	
	

}
