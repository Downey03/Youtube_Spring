package com.youtube.Youtube.Repository;

import com.youtube.Youtube.Entity.YTLink;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YTLinkRepository extends DatastoreRepository<YTLink,String> {
}
