package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.hei.haapi.endpoint.rest.mapper.OrderingEnumMapper;
import school.hei.haapi.endpoint.rest.model.OrderingEnum;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Course;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.repository.CourseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseRepository courseRepository;

    public List<Course> getCourses(
            PageFromOne page, BoundedPageSize pageSize,
            String teacherFirstName,
            String teacherLastName,
            String code,
            Integer credits,
            String name,
            Sort.Direction codeOrder,
            Sort.Direction creditsOrder
    ) {

        Pageable pageable = PageRequest.of(page.getValue(), pageSize.getValue(), Sort.by(List.of(
                new Sort.Order(codeOrder, "code"),
                new Sort.Order(creditsOrder, "credits")
        )));

        return courseRepository.findAllCourseBy(
                pageable,
                teacherFirstName,
                teacherLastName,
                code,
                credits,
                name
        );
    }

}


