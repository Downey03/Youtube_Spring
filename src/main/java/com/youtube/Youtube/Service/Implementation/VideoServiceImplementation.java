package com.youtube.Youtube.Service.Implementation;

import com.googlecode.objectify.ObjectifyService;
import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.Entity.YTLink;
import com.youtube.Youtube.PersistenceService.ObjectifyInitializer;
import com.youtube.Youtube.Service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImplementation implements VideoService {


    @Override
    public PlayListDTO videoSearch(String searchKeyword) {
        List<YTLink> ytLinkList = ObjectifyInitializer.ofy().load().type(YTLink.class).list();

        PlayListDTO playListDTO = new PlayListDTO();
        ytLinkList.removeIf(video -> video.getVideoTitle() == null || !video.getVideoTitle().toLowerCase().contains(searchKeyword.trim().toLowerCase()));

        playListDTO.ytLinks = ytLinkList;
        return playListDTO;
    }

    @Override
    public PlayListDTO videoSearch() {

        List<YTLink> ytLinkList = ObjectifyInitializer.ofy().load().type(YTLink.class).list();

        PlayListDTO playListDTO = new PlayListDTO();
        playListDTO.ytLinks = ytLinkList;

        return playListDTO;
    }
}
