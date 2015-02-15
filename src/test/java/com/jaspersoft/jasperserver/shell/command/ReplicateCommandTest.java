package com.jaspersoft.jasperserver.shell.command;//package com.jaspersoft.jasperserver.shell.command;
//
//import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
//import com.jaspersoft.jasperserver.shell.command.exp.RepositoryDataExporter;
//import com.jaspersoft.jasperserver.shell.command.imp.RepositoryDataImporter;
//import com.jaspersoft.jasperserver.shell.factory.SessionFactory;
//import com.jaspersoft.jasperserver.shell.parameter.Parameter;
//import com.jaspersoft.jasperserver.shell.profile.Profile;
//import com.jaspersoft.jasperserver.shell.profile.ProfileConfiguration;
//import com.jaspersoft.jasperserver.shell.profile.factory.ProfileConfigurationFactory;
//import jline.console.ConsoleReader;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintStream;
//
//import static java.lang.System.setOut;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.times;
//import static org.mockito.MockitoAnnotations.initMocks;
//
///**
// * Unit tests for {@link ReplicateCommand}
// */
//@Test
//@PrepareForTest({
//        ProfileConfigurationFactory.class, PrintStream.class,
//        SessionFactory.class, RepositoryDataExporter.class,
//        RepositoryDataImporter.class, ReplicateCommand.class})
//public class ReplicateCommandTest extends PowerMockTestCase {
//
//    @Mock
//    private InputStream stream;
//
//    @Mock
//    private Session firstSessionMock;
//
//    @Mock
//    private Session secondSessionMock;
//
//    @Mock
//    private RepositoryDataExporter exporterMock;
//
//    @Mock
//    private RepositoryDataImporter importerMock;
//
//    private ReplicateCommand replicateSpy;
//
//    @BeforeMethod
//    public void before() throws IOException {
//        initMocks(this);
//        replicateSpy = Mockito.spy(new ReplicateCommand());
//        replicateSpy.setReader(new ConsoleReader());
//    }
//
//    public void should_replicate_configuration_from_one_jrs_to_another() throws Exception {
//
//        /** Given **/
//
//        Parameter anon = replicateSpy.getParameters().get(0);
//        anon.getValues().add("JRS-1"); // src
//        anon.setAvailable(true);
//        anon.getValues().add("JRS-2"); // dest
//        anon.setAvailable(true);
//
//        Parameter withAudit = replicateSpy.getParameters().get(1);
//        withAudit.getValues().add("to");
//        withAudit.setAvailable(true);
//
//        ProfileConfiguration fakeConfig = new ProfileConfiguration();
//
//        fakeConfig.getProfiles().add(new Profile()
//                .setName("JRS-1")
//                .setOrganization("organization_1")
//                .setUrl("http://localhost:8080/jasperserver-pro")
//                .setUsername("superuser"));
//
//        fakeConfig.getProfiles().add(new Profile()
//                .setName("JRS-2")
//                .setOrganization("organization_1")
//                .setUrl("http://localhost:8085/jasperserver-pro")
//                .setUsername("superuser"));
//
//        PowerMockito.stub(PowerMockito.method(ReplicateCommand.class, "askPasswords")).toReturn("password");
//
//        PowerMockito.mockStatic(ProfileConfigurationFactory.class);
//        PowerMockito.when(ProfileConfigurationFactory.createSession("../conf/profile.yml")).thenReturn(fakeConfig);
//
//        PowerMockito.mockStatic(SessionFactory.class);
//        PowerMockito.when(SessionFactory.createImmutable(eq("http://localhost:8080/jasperserver-pro"),
//                eq("superuser"), eq("password"), eq("organization_1"))).thenReturn(firstSessionMock);
//        PowerMockito.when(SessionFactory.createImmutable(eq("http://localhost:8085/jasperserver-pro"),
//                eq("superuser"), eq("password"), eq("organization_1"))).thenReturn(secondSessionMock);
//
//        PowerMockito.whenNew(RepositoryDataExporter.class).withArguments(firstSessionMock).thenReturn(exporterMock);
//        PowerMockito.whenNew(RepositoryDataImporter.class).withArguments(secondSessionMock).thenReturn(importerMock);
//
//        doReturn(stream).when(exporterMock).export();
//
//        PrintStream streamSpy = spy(new PrintStream(new OutputStream() {
//            public void write(int b) {
//            }
//        }));
//        setOut(streamSpy);
//
//
//        /** When **/
//        replicateSpy.run();
//
//
//        /** Then **/
//        PowerMockito.verifyStatic(times(1));
//        ProfileConfigurationFactory.createSession(anyString());
//
//        PowerMockito.verifyStatic(times(1));
//        SessionFactory.createImmutable(eq("http://localhost:8080/jasperserver-pro"), eq("superuser"),
//                eq("password"), eq("organization_1"));
//
//        PowerMockito.verifyStatic(times(1));
//        SessionFactory.createImmutable(eq("http://localhost:8085/jasperserver-pro"), eq("superuser"),
//                eq("password"), eq("organization_1"));
//
//        PowerMockito.verifyNew(RepositoryDataExporter.class, times(1)).withArguments(firstSessionMock);
//        PowerMockito.verifyNew(RepositoryDataImporter.class, times(1)).withArguments(secondSessionMock);
//
//        //PowerMockito.verifyPrivate(ReplicateCommand.class, times(5));
//
//        Mockito.verify(exporterMock, times(1)).export();
//        Mockito.verify(importerMock, times(1)).importData(stream);
//
//        Mockito.verify(firstSessionMock, times(1)).logout();
//        Mockito.verify(secondSessionMock, times(1)).logout();
//
//        Mockito.verify(streamSpy, times(1)).printf("\rReplication status: SUCCESS\n");
//    }
//
//    @AfterMethod
//    public void after() {
//        replicateSpy = null;
//        reset(secondSessionMock, firstSessionMock, importerMock, exporterMock, stream);
//    }
//}