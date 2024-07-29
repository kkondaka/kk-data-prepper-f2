/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.dataprepper.plugins.sink.s3;

import org.opensearch.dataprepper.expression.ExpressionEvaluator;
import org.opensearch.dataprepper.model.event.Event;
import org.opensearch.dataprepper.plugins.sink.s3.accumulator.ObjectKey;

import java.time.LocalDate;

public class KeyGenerator {
    private final S3SinkConfig s3SinkConfig;
    private final ExtensionProvider extensionProvider;
    private final String sourceLocation;

    private final ExpressionEvaluator expressionEvaluator;

    public KeyGenerator(final S3SinkConfig s3SinkConfig,
                        final String sourceLocation,
                        final ExtensionProvider extensionProvider,
                        final ExpressionEvaluator expressionEvaluator) {
        this.s3SinkConfig = s3SinkConfig;
        this.extensionProvider = extensionProvider;
        this.expressionEvaluator = expressionEvaluator;
        this.sourceLocation = sourceLocation;
    }

    public String getSourceLocation() {
            return sourceLocation;
    }
    /**
     * Generate the s3 object path prefix and object file name.
     *
     * @return object key path.
     */
    public String generateKeyForEvent(final Event event) {
        String pathPrefix = ObjectKey.buildingPathPrefix(s3SinkConfig, event, expressionEvaluator);
        if (sourceLocation != null) {
            final String region=s3SinkConfig.getAwsAuthenticationOptions().getAwsRegion().toString();
            String roleArn = s3SinkConfig.getAwsAuthenticationOptions().getAwsStsRoleArn();
            final String accountId=roleArn.split(":")[4];
            final LocalDate now = LocalDate.now();
            final String eventDay = String.format("%d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
            int locIdx = sourceLocation.indexOf("/ext/");
            pathPrefix = sourceLocation.substring(locIdx+1)+"region="+region+"/accountId="+accountId+"/eventDay="+eventDay+"/";
            System.out.println("----Path Prefix---"+pathPrefix);
        }
        final String namePattern = ObjectKey.objectFileName(s3SinkConfig, extensionProvider.getExtension(), event, expressionEvaluator);
        return (!pathPrefix.isEmpty()) ? pathPrefix + namePattern : namePattern;
    }
}
