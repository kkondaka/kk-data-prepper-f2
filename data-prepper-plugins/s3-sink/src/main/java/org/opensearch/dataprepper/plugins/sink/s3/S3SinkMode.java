package org.opensearch.dataprepper.plugins.sink.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
public class S3SinkMode {

    @JsonProperty("type")
    String type;

    public String getType() {
        return type;
    }
}

