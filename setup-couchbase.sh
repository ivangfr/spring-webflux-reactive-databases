#!/usr/bin/env bash

COUCHBASE_ADMINISTRATOR_USERNAME=Administrator
COUCHBASE_ADMINISTRATOR_PASSWORD=password

COUCHBASE_BUCKET=customers
COUCHBASE_BUCKET_PASSWORD=password

COUCHBASE_BUCKET_USER_ID=customers
COUCHBASE_BUCKET_USER_NAME=customer-api
COUCHBASE_BUCKET_USER_PASSWORD=password

echo
curl -i -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

curl -i http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex

curl -i http://127.0.0.1:8091/settings/web -d port=8091 -d username=$COUCHBASE_ADMINISTRATOR_USERNAME -d password=$COUCHBASE_ADMINISTRATOR_PASSWORD
echo

echo
curl -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST http://127.0.0.1:8091/settings/indexes -d 'storageMode=memory_optimized'
echo

echo
curl -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST http://127.0.0.1:8091/pools/default/buckets -d name=$COUCHBASE_BUCKET -d bucketType=couchbase -d ramQuotaMB=128 -d authType=sasl -d saslPassword=$COUCHBASE_BUCKET_PASSWORD

curl -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X PUT http://127.0.0.1:8091/settings/rbac/users/local/${COUCHBASE_BUCKET_USER_ID} -d "name=${COUCHBASE_BUCKET_USER_NAME}&password=${COUCHBASE_BUCKET_USER_PASSWORD}&roles=bucket_full_access[${COUCHBASE_BUCKET}],bucket_admin[${COUCHBASE_BUCKET}]"
echo
