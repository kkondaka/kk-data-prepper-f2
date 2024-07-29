package org.opensearch.dataprepper.plugins.sink.s3.grouping;

import java.util.Map;

public class S3SecurityLakeIdentifier extends S3GroupIdentifier {
    public S3SecurityLakeIdentifier(Map<String, Object> groupIdentificationHash, String fullObjectKey, String fullBucketName) {
        super(groupIdentificationHash, fullObjectKey, fullBucketName);
    }

    @Override
    public String getGroupIdentifierFullObjectKey() {
        return  super.getGroupIdentifierFullObjectKey();
    }

    @Override
    public Map<String, String> getMetadata(int eventCount) {
        return Map.of("asl_rows", Integer.toString(eventCount));
    }
}
