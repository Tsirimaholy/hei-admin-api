package school.hei.haapi.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import school.hei.haapi.SentryConf;
import school.hei.haapi.endpoint.rest.api.TeachingApi;
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.endpoint.rest.security.cognito.CognitoComponent;
import school.hei.haapi.integration.conf.AbstractContextInitializer;
import school.hei.haapi.integration.conf.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static school.hei.haapi.integration.conf.TestUtils.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = CourseIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class CourseIT {
    @MockBean
    private SentryConf sentryConf;
    @MockBean
    private CognitoComponent cognitoComponentMock;

    private static ApiClient anApiClient(String token) {
        return TestUtils.anApiClient(token, CourseIT.ContextInitializer.SERVER_PORT);
    }

    @BeforeEach
    void setUp() {
        setUpCognito(cognitoComponentMock);
    }

    static Course course1(){
        return new Course()
                .id("course1_id")
                .name("Interface web")
                .code("WEB1")
                .credits(4)
                .mainTeacher(TeacherIT.teacher1())
                .totalHours(22);
    }

    static Course course2(){
        return new Course()
                .id("course2_id")
                .name("Algoritmique")
                .code("PROG1")
                .credits(6)
                .mainTeacher(TeacherIT.teacher1())
                .totalHours(25);
    }

    static Course course3(){
        return new Course()
                .id("course3_id")
                .name("P.O.O avancée")
                .code("PROG3")
                .credits(6)
                .mainTeacher(TeacherIT.teacher2())
                .totalHours(25);
    }


    @Test
    void teacher_read_ok() throws ApiException {
        ApiClient teacher1Client = anApiClient(TEACHER1_TOKEN);
        TeachingApi teachingApi = new TeachingApi(teacher1Client);

        List<Course> courses = teachingApi
                .getCourses(1, 1, null, null, null, null, null, null, null);
        assertTrue(courses.size()>0);
        assertTrue(courses.contains(course1()));
        assertTrue(courses.contains(course2()));
        assertTrue(courses.contains(course3()));
    }

    static class ContextInitializer extends AbstractContextInitializer {
        public static final int SERVER_PORT = anAvailableRandomPort();

        @Override
        public int getServerPort() {
            return SERVER_PORT;
        }
    }
}
