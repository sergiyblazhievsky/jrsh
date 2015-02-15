package com.jaspersoft.jasperserver.shell.factory;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.AuthenticationFailedException;
import com.jaspersoft.jasperserver.shell.exception.SessionIsNotAvailableException;
import com.jaspersoft.jasperserver.shell.exception.server.GeneralServerException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.shell.factory.SessionFactory}
 */
@PrepareForTest({SessionFactory.class})
public class SessionFactoryTest extends PowerMockTestCase {

//    @BeforeMethod
//    public void before() {
//        Set<Field> fields = Whitebox.getAllStaticFields(SessionFactory.class);
//        for (Field field : fields) {
//            field = null;
//        }
//    }

    @Test(expectedExceptions = SessionIsNotAvailableException.class, enabled = false)
    public void should_throw_an_exception_if_session_unavailable() {
        SessionFactory.getInstance();
    }

    @Test//(dependsOnMethods = "should_throw_an_exception_if_session_unavailable")
    public void should_create_new_session() throws Exception {

        /** Given **/
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        JasperserverRestClient clientMock = mock(JasperserverRestClient.class);
        Session sessionMock = mock(Session.class);

        PowerMockito.whenNew(RestClientConfiguration.class).withArguments(anyString()).thenReturn(configurationMock);
        PowerMockito.whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);
        Mockito.doReturn(sessionMock).when(clientMock).authenticate(anyString(), anyString());

        /** When **/
        Session session = SessionFactory.createSession("http://54.221.179.100/jasperserver-pro", "superuser", "superuser", "organization_1");

        /** Then **/
        Assert.assertNotNull(session);
        Assert.assertSame(session, sessionMock);
    }

    @Test(dependsOnMethods = "should_create_new_session", expectedExceptions = GeneralServerException.class)
    public void should_throw_an_exception_when_credentials_are_wrong() throws Exception {

        /** Given **/
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        JasperserverRestClient clientMock = mock(JasperserverRestClient.class);

        PowerMockito.whenNew(RestClientConfiguration.class).withArguments(anyString()).thenReturn(configurationMock);
        PowerMockito.whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);
        Mockito.doThrow(new AuthenticationFailedException()).when(clientMock).authenticate(anyString(), anyString());

        /** When **/
        SessionFactory.createSession("http://localhost:4444/jasperserver-pro", "wrongUsername", "superuser", null);

        /** Then **/
        //  throw
    }

    @Test(dependsOnMethods = "should_throw_an_exception_when_credentials_are_wrong")
    public void should_return_null_if_passed_null_parameters() {
        Session session = SessionFactory.createSession(null, null, null, null);
        Assert.assertNull(session);
    }

    @Test(dependsOnMethods = "should_return_null_if_passed_null_parameters")
    public void should_return_not_null_session() throws Exception {

        /** Given **/
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        JasperserverRestClient clientMock = mock(JasperserverRestClient.class);
        Session sessionMock = mock(Session.class);

        PowerMockito.whenNew(RestClientConfiguration.class).withArguments(anyString()).thenReturn(configurationMock);
        PowerMockito.whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);
        Mockito.doReturn(sessionMock).when(clientMock).authenticate(anyString(), anyString());
        SessionFactory.createSession("http://54.221.179.100/jasperserver-pro", "superuser", "superuser", "organization_1");

        /** When **/
        Session session = SessionFactory.getInstance();

        /** Then **/
        Assert.assertNotNull(session);
        Assert.assertSame(session, sessionMock);
    }

    @Test(invocationCount = 10, dependsOnMethods = "should_return_not_null_session")
    public void should_return_uptime() throws Exception {

        /** Given **/
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        JasperserverRestClient clientMock = mock(JasperserverRestClient.class);
        Session sessionMock = mock(Session.class);

        PowerMockito.whenNew(RestClientConfiguration.class).withArguments(anyString()).thenReturn(configurationMock);
        PowerMockito.whenNew(JasperserverRestClient.class).withArguments(configurationMock).thenReturn(clientMock);
        Mockito.doReturn(sessionMock).when(clientMock).authenticate(anyString(), anyString());
        SessionFactory.createSession("http://54.221.179.100/jasperserver-pro", "superuser", "superuser", "organization_1");

        /** When **/
        String uptime = SessionFactory.uptime();

        /** Then **/
        Assert.assertNotNull(uptime);
    }

    @Test(dependsOnMethods = "should_return_uptime", enabled = false)
    public void should_check_if_constructor_is_private() throws NoSuchMethodException {
        Constructor<?>[] constructors = SessionFactory.class.getDeclaredConstructors();
        for (Constructor<?> c : constructors) {
            Assert.assertTrue(Modifier.isPrivate(c.getModifiers()));
        }
    }
}