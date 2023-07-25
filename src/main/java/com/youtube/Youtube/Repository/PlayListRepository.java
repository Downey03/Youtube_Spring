package com.youtube.Youtube.Repository;

import com.youtube.Youtube.Entity.PlayList;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListRepository extends DatastoreRepository<PlayList,String> {

    @Query("select * from PlayLists where userId = @userId")
    List<PlayList> findAllByUserId(@Param("userId") String userId);
}
