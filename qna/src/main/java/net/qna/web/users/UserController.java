package net.qna.web.users;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.qna.dao.users.MybatisUserDao;
import net.qna.dao.users.UserDao;
import net.qna.domain.gallery.gallery;
import net.qna.domain.users.Authenticate;
import net.qna.domain.users.User;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="uploadPath2")
	String uploadPath2;
	
	@Autowired
	private MybatisUserDao userDao;
	
	@RequestMapping("/form")
	public String createform(Model model){
		model.addAttribute("user", new User());
		return "users/form";
	}
	
	@RequestMapping(value="/create" , method=RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult){
		log.debug("User : {}", user);
		if(bindingResult.hasErrors()){
			System.out.println("유효성검사");
			
			log.debug("Binding Result has error");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				log.debug("error: {}", error.getDefaultMessage());
			}
			
		
			return "users/form";
		}
	
		
		userDao.create(user);
		log.debug("Database: {}", userDao.findByID(user.getUserId()));
		return "redirect:/";
	}
	//내정보
	@RequestMapping("/{userId}/myinfo")
	public String myifo(@PathVariable String userId ,Model model){
		if(userId ==null){
			throw new IllegalArgumentException("사용자 아이디가 필요합니다.");
		}
		
		User user = userDao.findByID(userId);		
		model.addAttribute("user", user);
		return "users/myinfo";
	}
	
	//정보 수정
	@RequestMapping("/{userId}/modify")
	public String modify(@PathVariable String userId ,Model model){
		if(userId ==null){
			throw new IllegalArgumentException("사용자 아이디가 필요합니다.");
		}
		
		User user = userDao.findByID(userId);		
		model.addAttribute("user", user);
		return "users/infomodify";
	}
	
	//이미지 올리는창으로 이동 
		@RequestMapping(value="imgmodify", method=RequestMethod.POST)
		public ModelAndView imgmodify(@ModelAttribute User vo, HttpSession session, MultipartFile file, ModelAndView mav) throws Exception{
			String userId = (String) session.getAttribute("userId");
		    //랜덤이름 붙이기
		    UUID uuid = UUID.randomUUID();	    
	        String savedName = uuid.toString()+"_"+file.getOriginalFilename();
	        File target = new File(uploadPath2, savedName);
	        
	        // 임시디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
	        // FileCopyUtils.copy(바이트배열, 파일객체)
	        FileCopyUtils.copy(file.getBytes(), target);

	   
	        mav.addObject("savedName", savedName);
	        //vo에 데이터 넣기
	        
	        vo.setProfileimg(savedName);
	        vo.setUserId(userId);
	     
	        userDao.imgmodify(vo);
	        
	        mav.setViewName("redirect:/home/mainview");
	        return mav;

		
		}
	
	
	
	@RequestMapping(value="" , method=RequestMethod.POST)
	public String updatecreate(@Valid User user, BindingResult bindingResult, HttpSession session){
		log.debug("User : {}", user);
		if(bindingResult.hasErrors()){
			System.out.println("유효성검사");
			
			log.debug("Binding Result has error");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error: errors){
				log.debug("error: {}", error.getDefaultMessage());
			}
			return "users/form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp==null){
			throw new NullPointerException();
		}		
	
		userDao.update(user);
		log.debug("Database: {}", userDao.findByID(user.getUserId()));
		return "redirect:/";
	}
	
	@RequestMapping("/login/form")
	public String loginform(Model model){
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model){
		if(bindingResult.hasErrors()){
			return "users/login";
		}
		
		User user = userDao.findByID(authenticate.getUserId());
		
		if(user ==null){
			model.addAttribute("errorMessage", "존재하지 않는 사용자입니다.");
			return "users/login";
		}
		
		if(!user.matchPassword(authenticate)){
			model.addAttribute("errorMessage", "비밀번호가 틀립니다.");
			return "users/login";
		}
		session.setAttribute("userId", user.getUserId());
		session.setAttribute("profileimg", user.getProfileimg());
		// 세션 사용자 정보 저장 
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("userId");
		return "redirect:/";
	}
	
	
	//아이디 중복체크 

	 @RequestMapping(value = "/checkId.do")
	 public void checkId(HttpServletRequest req, HttpServletResponse res,
	   ModelMap model) throws Exception {
	  PrintWriter out = res.getWriter();
	  try {

	   // 넘어온 ID를 받는다.
	   String paramId = (req.getParameter("prmId") == null) ? "" : String
	     .valueOf(req.getParameter("prmId"));

	   User vo = new User();
	   vo.setUserId(paramId.trim());
	   int chkPoint = userDao.checkId(vo);

	   out.print(chkPoint);
	   out.flush();
	   out.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	   out.print("1");
	  }
	 }

	
}


