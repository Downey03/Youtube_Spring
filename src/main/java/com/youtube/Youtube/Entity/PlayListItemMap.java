package com.youtube.Youtube.Entity;

import com.googlecode.objectify.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@com.googlecode.objectify.annotation.Entity
@Entity(name = "PlayListItemMap")
public class PlayListItemMap {
    @Id
            @com.googlecode.objectify.annotation.Id
    String playListItemMapId;
    @Index
    String playListId;
    @Index
    String videoTitle;
    @Index
    String videoLink;
    @Index
    String videoThumbnail;

}
