//package com.jaspersoft.cli.tool.command.factory;
//
//import com.jaspersoft.cli.tool.exception.MissingConnectionInformationException;
//import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
//import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
//import org.mockito.Mock;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import static org.mockito.Mockito.reset;
//import static org.mockito.MockitoAnnotations.initMocks;
//
///**
// * Unit tests for {@link SessionFactory}
// */
//@PrepareForTest({SessionFactory.class, RestClientConfiguration.class, JasperserverRestClient.class})
//public class SessionFactoryTest extends PowerMockTestCase {
//
//    @Mock
//    private RestClientConfiguration clientConfigurationMock;
//    @Mock
//    private JasperserverRestClient restClientMock;
//    @Mock
//    private Session sessionMock;
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test(expectedExceptions = MissingConnectionInformationException.class)
//    public void should_throw_exception_if_session_is_null() {
//        SessionFactory.getInstance();
//    }
//
//    @Test(dependsOnMethods = "should_throw_exception_if_session_is_null")
//    public void should_return_new_session_instance() throws Exception {
//
//        // given
//        PowerMockito.whenNew(RestClientConfiguration.class)
//                .withArguments("http://54.221.48.79/jasperserver-pro").thenReturn(clientConfigurationMock);
//        PowerMockito.whenNew(JasperserverRestClient.class)
//                .withArguments(clientConfigurationMock).thenReturn(restClientMock);
//        PowerMockito.doReturn(sessionMock).when(restClientMock).authenticate("superuser", "superuser");
//
//        // when
//        Session retrieved = SessionFactory.create("http://54.221.48.79/jasperserver-pro", "superuser", "superuser", null);
//
//        // than
//        Assert.assertNotNull(retrieved);
//        Assert.assertEquals(retrieved, sessionMock);
//    }
//
//    @Test(dependsOnMethods = "should_return_new_session_instance")
//    public void should_return_session_object() throws Exception {
//        Session retrieved = SessionFactory.getInstance();
//        Assert.assertNotNull(retrieved);
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(clientConfigurationMock, restClientMock, sessionMock);
//    }
//}