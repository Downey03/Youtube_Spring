package com.youtube.Youtube.Repository;

import com.youtube.Youtube.Entity.YTLink;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YTLinkRepository extends DatastoreRepository<YTLink,String> {

    @Query("select * from YTLinks where videoTitle contains @searchKeyword")
    List<YTLink> findByVideoTitile(@Param("searchKeyword")String searchKeyword);

    @Query("select * from YTLinks where videoTitle has @searchKeyword")
    List<YTLink> findByVideoTitilehas(@Param("searchKeyword")String searchKeyword);
}
