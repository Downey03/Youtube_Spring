package com.youtube.Youtube.DTO;



import com.youtube.Youtube.Entity.YTLink;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;


public class PlayListDTO {
    public String playListName;
    public List<YTLink> ytLinks;
    public String jwtToken;
    public String userId;
    public List<String> playLists;

    public String userEmail;
    public String videoTitle;
    public String newName;

    @Override
    public String toString() {
        return "PlayListDTO{" +
                "playListName='" + playListName + '\'' +
                ", ytLinks=" + ytLinks +
                ", jwtToken='" + jwtToken + '\'' +
                ", userId='" + userId + '\'' +
                ", playLists=" + playLists +
                ", userEmail='" + userEmail + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                '}';
    }
}
