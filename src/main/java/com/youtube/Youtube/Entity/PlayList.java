package com.youtube.Youtube.Entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "PlayList")
@com.googlecode.objectify.annotation.Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayList {
    @Id
    @com.googlecode.objectify.annotation.Id
    String playListId;

    @Index
    String playListName;

    @Index
    String userId;

//    @Parent
//    @Index
//    Ref<User> user;

}
