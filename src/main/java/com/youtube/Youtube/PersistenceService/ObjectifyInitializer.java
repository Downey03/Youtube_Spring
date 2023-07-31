package com.youtube.Youtube.PersistenceService;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.youtube.Youtube.Entity.PlayList;
import com.youtube.Youtube.Entity.PlayListItemMap;
import com.youtube.Youtube.Entity.User;
import com.youtube.Youtube.Entity.YTLink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;

@Configuration
public class ObjectifyInitializer  {

    public ObjectifyInitializer(){
        ObjectifyService.init();
        ObjectifyService.register(User.class);
        ObjectifyService.register(PlayList.class);
        ObjectifyService.register(PlayListItemMap.class);
        ObjectifyService.register(YTLink.class);
    }

    public static void init(){

        ObjectifyService.register(User.class);
        ObjectifyService.register(PlayList.class);
        ObjectifyService.register(PlayListItemMap.class);
        ObjectifyService.register(YTLink.class);
    }

    public static Objectify ofy(){
        return ObjectifyService.ofy();
    }
}
