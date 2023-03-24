package school.hei.haapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query(value = "select c from Course c " +
            "where " +
            "upper(c.teacher.firstName) like upper(:firstName) " +
            "or upper(c.teacher.lastName) like upper(:lastName) " +
            "or upper(c.code) like upper(:code) " +
            "or upper(c.credits) = :credits " +
            "or upper(c.name) like upper(:name) ")
    List<Course> findAllCourseBy(
            // overload this for nullish values

            Pageable pageable,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("code") String code,
            @Param("credits") Integer credits,
            @Param("name") String name
    );


}