package com.youtube.Youtube.Repository;

import com.youtube.Youtube.Entity.PlayListItemMap;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListItemMapRepository extends DatastoreRepository<PlayListItemMap,String> {
}
