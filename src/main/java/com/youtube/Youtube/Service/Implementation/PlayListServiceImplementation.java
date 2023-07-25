package com.youtube.Youtube.Service.Implementation;

import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.Entity.PlayList;
import com.youtube.Youtube.Entity.PlayListItemMap;
import com.youtube.Youtube.Entity.YTLink;
import com.youtube.Youtube.PersistenceService.ObjectifyInitializer;
import com.youtube.Youtube.Repository.PlayListRepository;
import com.youtube.Youtube.Service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayListServiceImplementation implements PlayListService {
    @Autowired
    PlayListRepository playListRepository;

    @Autowired
    ObjectifyInitializer objectifyInitializer;
    @Override
    public PlayListDTO createPlayList(PlayListDTO playListDTO) throws Exception {

        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        if(playList != null) throw  new Exception("PlayList Already Found");

        playList = PlayList.builder()
                .playListId(UUID.randomUUID().toString())
                .playListName(playListName)
                .userId(userId)
                .build();

        ObjectifyInitializer.ofy().save().entity(playList);

        return getPlayLists(playListDTO);
    }

    @Override
    public PlayListDTO getPlayLists(PlayListDTO playListDTO) {

        String userId = playListDTO.userId;

        List<PlayList> playLists = ObjectifyInitializer.ofy().load().type(PlayList.class).filter("userId",userId).list();

        playListDTO.playLists = new ArrayList<>();
        for(PlayList name : playLists)
            playListDTO.playLists.add(name.getPlayListName());

        return playListDTO;
    }

    public PlayListDTO getPlayList(PlayListDTO playListDTO,PlayList playList){
        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                .filter("playListId",playList.getPlayListId()).list();

        playListDTO.ytLinks = new ArrayList<>();

        for(PlayListItemMap item : playListItemMaps){
            YTLink ytLink = new YTLink();
            ytLink.setVideoLink(item.getVideoLink());
            ytLink.setVideoTitle(item.getVideoTitle());
            ytLink.setVideoThumbnail(item.getVideoThumbnail());
            playListDTO.ytLinks.add(ytLink);
        }

        return playListDTO;

    }

    @Override
    public PlayListDTO getPlayList(PlayListDTO playListDTO) {

        String playListName = playListDTO.playListName;
        String userId = playListDTO.userId;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                .filter("playListId",playList.getPlayListId()).list();

        System.out.println(Arrays.toString(playListItemMaps.toArray()));

        playListDTO.ytLinks = new ArrayList<>();

        for(PlayListItemMap item : playListItemMaps){
            YTLink ytLink = new YTLink();
            ytLink.setVideoLink(item.getVideoLink());
            ytLink.setVideoTitle(item.getVideoTitle());
            ytLink.setVideoThumbnail(item.getVideoThumbnail());
            playListDTO.ytLinks.add(ytLink);
        }

        return playListDTO;
    }

    public PlayListDTO deletePlayList(PlayListDTO playListDTO){
        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        System.out.println(playListName+" "+userId);

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("userId",userId).filter("playListName",playListName)
                .first().now();

        System.out.println(playList.toString());

        ObjectifyInitializer.ofy().delete().type(PlayList.class).id(playList.getPlayListId()).now();

        List<PlayListItemMap> playListItemMaps = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class).list();

        for(PlayListItemMap item : playListItemMaps){
            ObjectifyInitializer.ofy().delete().entity(item);
        }

        return getPlayLists(playListDTO);
    }

    @Override
    public PlayListDTO addItemToPlayList(PlayListDTO playListDTO) {
        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        YTLink ytLink = ObjectifyInitializer.ofy().load().type(YTLink.class)
                .filter("videoTitle",videoTitle)
                .first().now();

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        PlayListItemMap playListItemMap = PlayListItemMap.builder()
                .playListItemMapId(UUID.randomUUID().toString())
                .playListId(playList.getPlayListId())
                .videoLink(ytLink.getVideoLink())
                .videoTitle(ytLink.getVideoTitle())
                .videoThumbnail(ytLink.getVideoThumbnail())
                .build();

        ObjectifyInitializer.ofy().save().entity(playListItemMap).now();

        return getPlayList(playListDTO,playList);
    }

    @Override
    public PlayListDTO removeItemFromPlayList(PlayListDTO playListDTO) {
        String userId = playListDTO.userId;
        String playListName = playListDTO.playListName;
        String videoTitle = playListDTO.videoTitle;

        PlayList playList = ObjectifyInitializer.ofy().load().type(PlayList.class)
                .filter("playListName",playListName).filter("userId",userId)
                .first().now();

        PlayListItemMap playListItemMap = ObjectifyInitializer.ofy().load().type(PlayListItemMap.class)
                .filter("playListId",playList.getPlayListId()).filter("videoTitle",videoTitle)
                .first().now();


        ObjectifyInitializer.ofy().delete().entity(playListItemMap);

        return getPlayList(playListDTO,playList);
    }
}
