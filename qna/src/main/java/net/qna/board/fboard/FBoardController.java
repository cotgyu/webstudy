package net.qna.board.fboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.qna.dao.board.MybatisBoardDao;
import net.qna.dao.fboard.FMyBoardDao;
import net.qna.dao.users.MybatisUserDao;
import net.qna.domain.board.Board;
import net.qna.domain.board.BoardPage;
import net.qna.domain.board.FBoard;
import net.qna.domain.board.PhotoVo;

@Controller
@RequestMapping("/fboard")
public class FBoardController {
	
	@Autowired
	private FMyBoardDao fboardDao;
	@Autowired
	private MybatisUserDao userDao;
	
	//게시판 리스트
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, 
			@RequestParam(defaultValue="") String keyword,
			@RequestParam(defaultValue="1") int curPage) throws Exception{
		
		//레코드의 개수
		int count = fboardDao.countboard(searchOption, keyword);
		
		//페이지
		BoardPage boardPage = new BoardPage(count, curPage);
		int start = boardPage.getPageBegin();
		int end = boardPage.getPageEnd();
		
		List<FBoard> list = fboardDao.Viewlist(start, end, searchOption, keyword);
		
		List<FBoard> poplist = fboardDao.popboard();
		
		//데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); //list
		map.put("count", count); //레코드 개수
		map.put("searchOption", searchOption); //검색 옵션
		map.put("keyword", keyword); //검색 키워드
		map.put("boardPage", boardPage); 
		map.put("poplist", poplist);
		//모델과 뷰
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
        mav.setViewName("fboard/fboard"); // 뷰를 list.jsp로 설정
       
        return mav; // list.jsp로 List가 전달된다.
	}
	
	//02 게시물 작성화면 이동
	@RequestMapping(value="write", method=RequestMethod.GET)
    public String write(){
        return "fboard/write"; // write.jsp로 이동
    }
	
	//03 게시물 작성
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute FBoard vo, HttpSession session) throws Exception{
	    // session에 저장된 userId를 writer에 저장
	    String writer = (String) session.getAttribute("userId");
	    // vo에 writer를 세팅
	    vo.setWriter(writer);
	    fboardDao.create(vo);
	    return "redirect:list";
	}

	//04 게시물 보기
	@RequestMapping(value="view", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int bnum, HttpSession session) throws Exception{
        // 조회수 증가 처리
		fboardDao.uphit(bnum);
		
		
		String userId = fboardDao.findByWriter(bnum);
		String userimg = userDao.findByprofile(userId);	
        // 모델(데이터)+뷰(화면)를 함께 전달하는 객체
        ModelAndView mav = new ModelAndView();
        // 뷰의 이름
        mav.setViewName("fboard/view");
        // 뷰에 전달할 데이터
        mav.addObject("dto", fboardDao.read(bnum));
        mav.addObject("profileimg",userimg);
        return mav;
    }
  
	
	//글 수정창으로 연결     
    @RequestMapping(value="/updatedetail/{bnum}", method=RequestMethod.GET)
    public ModelAndView boardDetail(@PathVariable("bnum") Integer bnum, ModelAndView mav){
        FBoard vo = fboardDao.detail(bnum);
        // 뷰이름 지정
        mav.setViewName("fboard/modify");
        // 뷰에 전달할 데이터 지정
        mav.addObject("vo", vo);
        // replyDetail.jsp로 포워딩
        return mav;
    }
    // 05. 게시글 수정
    // 폼에서 입력한 내용들은 @ModelAttribute BoardVO vo로 전달됨
    @RequestMapping(value="update", method=RequestMethod.POST)
    public String update(@ModelAttribute FBoard vo) throws Exception{
        fboardDao.update(vo);
        return "redirect:list";
    }
   
    // 06. 게시글 삭제
    @RequestMapping("delete")
    public String delete(@RequestParam int bnum) throws Exception{
        fboardDao.delete(bnum);
        return "redirect:list";
    }
	
    @RequestMapping("/photoUpload")
    public String photoUpload(HttpServletRequest request, PhotoVo vo){
        String callback = vo.getCallback();
        String callback_func = vo.getCallback_func();
        String file_result = "";
        try {
            if(vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null && !vo.getFiledata().getOriginalFilename().equals("")){
                //파일이 존재하면
                String original_name = vo.getFiledata().getOriginalFilename();
                String ext = original_name.substring(original_name.lastIndexOf(".")+1);
                //파일 기본경로
                String defaultPath = request.getSession().getServletContext().getRealPath("/");
                //파일 기본경로 _ 상세경로
                String path = defaultPath + "resources" + File.separator + "Editors" + File.separator +"uploadimg" + File.separator;             
                File file = new File(path);
                System.out.println("path:"+path);
                //디렉토리 존재하지 않을경우 디렉토리 생성
                if(!file.exists()) {
                    file.mkdirs();
                }
                //서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
                String realname = UUID.randomUUID().toString() + "." + ext;
            ///////////////// 서버에 파일쓰기 /////////////////
                vo.getFiledata().transferTo(new File(path+realname));
                file_result += "&bNewLine=true&sFileName="+original_name+"&sFileURL=/resources/Editors/uploadimg/"+realname;
            } else {
                file_result += "&errstr=error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + callback + "?callback_func="+callback_func+file_result;
    }

    //다중파일업로드
    @RequestMapping("multiplePhotoUpload")
    public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response){
        try {
             //파일정보
             String sFileInfo = "";
             //파일명을 받는다 - 일반 원본파일명
             String filename = request.getHeader("file-name");
             //파일 확장자
             String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
             //확장자를소문자로 변경
             filename_ext = filename_ext.toLowerCase();
             //파일 기본경로
             String dftFilePath = request.getSession().getServletContext().getRealPath("/");
             //파일 기본경로 _ 상세경로
             String filePath = dftFilePath + "resources" + File.separator +  "SmartEditor" + File.separator + "UploadPhoto" + File.separator;
             File file = new File(filePath);
             if(!file.exists()) {
                file.mkdirs();
             }
             String realFileNm = "";
             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
             String today= formatter.format(new java.util.Date());
             realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
             String rlFileNm = filePath + realFileNm;
             ///////////////// 서버에 파일쓰기 /////////////////
             InputStream is = request.getInputStream();
             OutputStream os=new FileOutputStream(rlFileNm);
             int numRead;
             byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
             while((numRead = is.read(b,0,b.length)) != -1){
                os.write(b,0,numRead);
             }
             if(is != null) {
                is.close();
             }
             os.flush();
             os.close();
             ///////////////// 서버에 파일쓰기 /////////////////
             // 정보 출력
             sFileInfo += "&bNewLine=true";
             // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
             sFileInfo += "&sFileName="+ filename;;
             sFileInfo += "&sFileURL="+"/resources/SmartEditor/UploadPhoto/"+realFileNm;
             PrintWriter print = response.getWriter();
             print.print(sFileInfo);
             print.flush();
             print.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}