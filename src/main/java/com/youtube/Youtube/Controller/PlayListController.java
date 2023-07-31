package com.youtube.Youtube.Controller;

import Utilities.Utils;
import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.Service.Implementation.PlayListServiceImplementation;
import com.youtube.Youtube.Service.PlayListService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/playlist")
public class PlayListController {

    @Autowired
    PlayListService playListService ;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<ModelAndView> createPlayList(@RequestBody PlayListDTO playListDTO, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        try {
            playListDTO = playListService.createPlayList(playListDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            modelAndView.addObject("playLists",playListDTO.playLists);
            return new ResponseEntity<>(modelAndView,HttpStatus.ACCEPTED);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            modelAndView.addObject(e.getMessage());
            return new ResponseEntity<>(modelAndView,HttpStatus.ALREADY_REPORTED);
        }

    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseEntity<ModelAndView> getPlayLists( HttpServletRequest req, HttpServletResponse resp) throws IOException{

        PlayListDTO playListDTO = new PlayListDTO();
        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");
//        playListDTO.userId = Utils.getUserId(req);
        playListDTO = playListService.getPlayLists(playListDTO);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("playLists",playListDTO.playLists);

        return new ResponseEntity<ModelAndView>(modelAndView,HttpStatus.ACCEPTED);
    }

    @GetMapping("/view")
    public ResponseEntity<ModelAndView> getPlayList(@RequestParam("playListName") String playListName, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.playListName = playListName;
        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");

        playListDTO = playListService.getPlayList(playListDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("YTLinks",playListDTO.ytLinks);
        return new ResponseEntity<>(modelAndView,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ModelAndView> deletePlayList(@RequestBody PlayListDTO playListDTO,HttpServletRequest req,HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");

        playListDTO = playListService.deletePlayList(playListDTO);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("playLists",playListDTO.playLists);
        return new ResponseEntity<>(modelAndView,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/add")
    public ResponseEntity<ModelAndView> addPlayListItem(@RequestBody PlayListDTO playListDTO, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");

        playListDTO = playListService.addItemToPlayList(playListDTO);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("YTLinks",playListDTO.ytLinks);
        return new ResponseEntity<>(modelAndView,HttpStatus.valueOf(202));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ModelAndView> removePlayListItem(@RequestBody PlayListDTO playListDTO, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        playListDTO.userId = (String)session.getAttribute("userId");

        playListDTO = playListService.removeItemFromPlayList(playListDTO);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("YTLinks",playListDTO.ytLinks);
        return new ResponseEntity<>(modelAndView,HttpStatus.valueOf(201));
    }

    @PutMapping("/update")
    public void changeplayListName(@RequestParam("playListName") String playListName,@RequestParam("newName") String newName,HttpServletRequest req,HttpServletResponse resp){

        HttpSession session = req.getSession();
        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.userId = (String)session.getAttribute("userId");

        playListDTO.playListName = playListName;
        playListDTO.newName = newName;
        System.out.println(playListDTO);

        playListService.updatePlayListName(playListDTO);

        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }


}
