package com.youtube.Youtube.Controller;

import com.google.api.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class SessionController {
    @RequestMapping("/")
    public static String checkSession(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException, InterruptedException {

        HttpSession session = req.getSession();


            if(session.getAttribute("userId") == null){
              System.out.println("sess is null");

              return "index";
            }else{
                System.out.println("sess is avai");

               return "home";
            }

    }


    @RequestMapping("/home")
    public void getHome(HttpServletResponse resp) throws IOException {

        resp.sendRedirect("/");
    }


    @RequestMapping("/index")
    public void getIndex(HttpServletResponse resp) throws IOException {

        resp.sendRedirect("/");
    }
    @RequestMapping("/playlist")
    public String getPlayList(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        System.out.println(session.getAttribute("userId"));
        if(session.getAttribute("userId") == null){

            resp.sendRedirect("/");
        }

        return "playlist";
    }

    @RequestMapping("/sign_up")
    public String signUpView(){
        return "sign-up";
    }

    @RequestMapping("/test")
    public String indexView(HttpServletRequest req,HttpServletResponse resp){
        HttpSession session = req.getSession(false);
        ModelAndView mv = new ModelAndView();

        Cookie cookie = new Cookie("JSESSIONID",req.getSession().getId());
//        System.out.println("in test cont"+req.getSession().getId());
        System.out.println(req.getSession(false).getId());
        resp.setHeader("JSESSIONID",req.getSession().getId());
        mv.setViewName("index");
        return "home";
    }
}
