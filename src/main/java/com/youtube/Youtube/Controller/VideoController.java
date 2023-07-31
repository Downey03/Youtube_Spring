package com.youtube.Youtube.Controller;

import Utilities.Utils;
import com.google.common.hash.HashCode;
import com.google.gson.Gson;
import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.DTO.SearchDTO;
import com.youtube.Youtube.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoService videoService;

    @GetMapping("/search")
    public ResponseEntity<ModelAndView> searchVideo(@RequestParam("searchKeyword") String searchKeyword, HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String jsonString = Utils.getRequestString(req);
//        System.out.println(jsonString);
//        SearchDTO searchDto = new Gson().fromJson(new StringReader(jsonString), SearchDTO.class);

        SearchDTO searchDto = new SearchDTO(searchKeyword);
        HttpSession session = req.getSession();

        PlayListDTO playListDTO;
        if(searchDto != null && searchDto.getSearchKeyword() != null && !searchDto.getSearchKeyword().trim().equals("")) {
            playListDTO = videoService.videoSearch(searchDto.getSearchKeyword());
        }else{
            playListDTO = videoService.videoSearch();
        }

        if(playListDTO.ytLinks == null || playListDTO.ytLinks.size()==0){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
        }else{
            resp.setStatus(201);//
        }

//        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(playListDTO.ytLinks);
//        modelAndView.addObject("ytlinks",new Gson().toJson(playListDTO.ytLinks));
//        modelAndView.setViewName("index");
       return new ResponseEntity<ModelAndView>(modelAndView,HttpStatus.ACCEPTED);
//     working   return playListDTO.ytLinks;
//        return modelAndView;
//        out.print(new Gson().toJson(playListDTO.ytLinks));
//        out.flush();
    }

}
