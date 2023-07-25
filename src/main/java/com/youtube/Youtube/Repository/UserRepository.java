package com.youtube.Youtube.Repository;

import com.youtube.Youtube.Entity.User;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DatastoreRepository<User,String> {

    @Query("Select * from User where userEmail = @userEmail")
    User findByUserEmail(@Param("userEmail") String userEmail);
}
