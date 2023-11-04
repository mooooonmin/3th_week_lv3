package com.level3.admin.repository;

import com.level3.admin.entity.Lecture;
import com.level3.admin.entity.LectureCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCategory(LectureCategory category, Sort sort);
    List<Lecture> findByLecturerNameOrderByRegDateDesc(String lecturerName);

}
