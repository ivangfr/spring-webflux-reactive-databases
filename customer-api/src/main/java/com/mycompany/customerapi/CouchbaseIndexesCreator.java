package com.mycompany.customerapi;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.query.CreatePrimaryQueryIndexOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class CouchbaseIndexesCreator {

    private final CouchbaseTemplate couchbaseTemplate;
    private final Cluster cluster;

    @PostConstruct
    private void init() {
        CreatePrimaryQueryIndexOptions options = CreatePrimaryQueryIndexOptions.createPrimaryQueryIndexOptions().ignoreIfExists(true);
        cluster.queryIndexes().createPrimaryIndex(couchbaseTemplate.getBucketName(), options);
    }

}
