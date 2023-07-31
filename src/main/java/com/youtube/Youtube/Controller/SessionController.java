package com.youtube.Youtube.Controller;

import com.google.api.Http;
import com.googlecode.objectify.Ref;
import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.Entity.PlayList;
import com.youtube.Youtube.Entity.User;
import com.youtube.Youtube.PersistenceService.ObjectifyInitializer;
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

    @RequestMapping("/test1")
    public void indexView(@RequestBody PlayListDTO playListDTO){
        PlayList playList = new PlayList();
        playList.setPlayListName(playListDTO.playListName);
        User user = ObjectifyInitializer.ofy().load().type(User.class)
                .filter("userEmail",playListDTO.userEmail)
                .first().now();
        playList.setUserId(user.getUserId());

        ObjectifyInitializer.ofy().save().entity(playList);
    }

    @RequestMapping("/test2")
    public void in(@RequestBody PlayListDTO playListDTO){
        String userID = playListDTO.userId;

//        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
//                .filter()
    }
}
