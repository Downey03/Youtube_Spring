package com.youtube.Youtube.Entity;

import com.googlecode.objectify.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "YTLink")
@com.googlecode.objectify.annotation.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YTLink {
    @Id
            @com.googlecode.objectify.annotation.Id
    String videoId;

    @Index
    String videoTitle;

    @Index
    String videoLink;

    @Index
    String videoThumbnail;
}
