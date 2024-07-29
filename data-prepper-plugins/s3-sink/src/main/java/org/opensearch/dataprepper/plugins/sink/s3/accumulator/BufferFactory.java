/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.dataprepper.plugins.sink.s3.accumulator;

import org.opensearch.dataprepper.plugins.sink.s3.grouping.S3GroupIdentifier;
import org.opensearch.dataprepper.plugins.sink.s3.ownership.BucketOwnerProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import java.util.function.Supplier;

public interface BufferFactory {
    default Buffer getBuffer(S3AsyncClient s3Client, S3GroupIdentifier s3GroupIdentifier, String defaultBucket, BucketOwnerProvider bucketOwnerProvider) {
        return getBuffer(s3Client, s3GroupIdentifier::getFullBucketName, s3GroupIdentifier::getGroupIdentifierFullObjectKey, defaultBucket, bucketOwnerProvider);
    }
    Buffer getBuffer(S3AsyncClient s3Client, Supplier<String> bucketSupplier, Supplier<String> keySupplier, String defaultBucket, BucketOwnerProvider bucketOwnerProvider);
}
