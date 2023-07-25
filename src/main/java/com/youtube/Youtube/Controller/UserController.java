package com.youtube.Youtube.Controller;

import Utilities.JwtUtil;
import Utilities.Utils;
import com.youtube.Youtube.DTO.UserDTO;
import com.youtube.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping("/test")
//    public ModelAndView testing(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("sample","this is the data");
//        modelAndView.setViewName("home");
//        return modelAndView;
//    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createUser( Model model,HttpServletRequest req, HttpServletResponse resp, @RequestBody UserDTO userDTO) throws IOException {

        try{
            System.out.println(userDTO);
            String jwtToken = userService.loginUser(userDTO);

            HttpSession session = req.getSession();
            session.setAttribute("jwtToken",jwtToken);
            Cookie Cookie = new Cookie("JSESSIONID",session.getId());
            resp.addCookie(Cookie);
//            out.print(new Gson().toJson(jwtToken));
            return "home";
        }catch (Exception e){
//            out.print(new Gson().toJson(e.getMessage()));
            return "index";
        }finally {
//            out.flush();
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.PUT)
    public void loginUser( HttpServletRequest req, HttpServletResponse resp, @RequestBody UserDTO userDTO) throws Exception {


        try{
            String jwtToken = userService.loginUser(userDTO);
            HttpSession session = req.getSession();
            String userId = JwtUtil.getUserId(jwtToken);
            session.setAttribute("userId",userId);
            resp.setStatus(200);

        }catch (Exception e){
            resp.setStatus(401);
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void logout( HttpServletRequest req){

        Cookie cookie = WebUtils.getCookie(req,"JSESSIONID");
        cookie.setMaxAge(0);
        HttpSession session = req.getSession();
        session.invalidate();

    }

}
