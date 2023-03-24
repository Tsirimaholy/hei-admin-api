package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.CourseMapper;
import school.hei.haapi.endpoint.rest.mapper.OrderingEnumMapper;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.endpoint.rest.model.OrderingEnum;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final OrderingEnumMapper orderingEnumMapper;

    @GetMapping("/courses")
    public List<Course> getCourses(
            @RequestParam PageFromOne page,
            @RequestParam("page_size") BoundedPageSize pageSize,
            @RequestParam(value = "teacher_first_name", required = false, defaultValue = "") String teacherFirstName,
            @RequestParam(value = "teacher_last_name", required = false, defaultValue = "") String teacherLastName,
            @RequestParam(value = "code", required = false, defaultValue = "") String code,
            @RequestParam(value = "credits", required = false) Integer credits,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "codeOrder", defaultValue = "ASC", required = false) OrderingEnum codeOrder,
            @RequestParam(value = "codeOrder", defaultValue = "ASC", required = false) OrderingEnum creditsOrder
    ) {
        return courseService
                .getCourses(
                        page, pageSize,
                        teacherFirstName, teacherLastName, code, credits, name,
                        orderingEnumMapper.toDomain(codeOrder),
                        orderingEnumMapper.toDomain(creditsOrder
                        )
                )
                .stream().map(courseMapper::toRest).collect(Collectors.toList());
    }
}
