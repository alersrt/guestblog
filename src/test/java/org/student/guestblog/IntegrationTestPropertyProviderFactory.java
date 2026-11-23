package org.student.guestblog;

import io.micronaut.test.support.TestPropertyProvider;
import io.micronaut.test.support.TestPropertyProviderFactory;

import java.util.Map;

/**
 * Supplies the properties of the test infrastructure before the Micronaut context is created.
 */
public class IntegrationTestPropertyProviderFactory implements TestPropertyProviderFactory {

    @Override
    public TestPropertyProvider create(Map<String, Object> properties, Class<?> testClass) {
        return AbstractIntegrationTest::testProperties;
    }
}
