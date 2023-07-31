package com.youtube.Youtube.Entity;

import com.googlecode.objectify.annotation.Index;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "User")
@Builder
@com.googlecode.objectify.annotation.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User{

    @Id
    @com.googlecode.objectify.annotation.Id
    String userId;

    @Index
    String userEmail;

    @Index
    String userName;

    @Index
    String password;



    public String toString(){
        return this.userEmail+" "+this.password;
    }

}
