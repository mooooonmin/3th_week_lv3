package com.level3.admin.domain.course.repository;

import com.level3.admin.domain.course.entity.Course;
import com.level3.admin.domain.course.entity.CourseCategory;
import com.level3.admin.domain.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructorOrderByRegistrationDateDesc(Instructor instructor);
    List<Course> findByCategoryOrderByRegistrationDateDesc(CourseCategory category);
    void deleteByInstructorId(Long instructorId);

}
