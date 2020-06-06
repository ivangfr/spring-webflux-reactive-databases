package com.mycompany.customerapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@RequiredArgsConstructor
@EnableConfigurationProperties({CouchbaseDataProperties.class, CouchbaseProperties.class})
@EnableReactiveCouchbaseRepositories
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    private final CouchbaseDataProperties couchbaseDataProperties;
    private final CouchbaseProperties couchbaseProperties;

    @Override
    public String getConnectionString() {
        return couchbaseProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return couchbaseDataProperties.getBucketName();
    }

    @Override
    protected boolean autoIndexCreation() {
        return couchbaseDataProperties.isAutoIndex();
    }

}
