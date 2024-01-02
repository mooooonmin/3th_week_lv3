package com.level3.admin.domain.instructor.repository;

import com.level3.admin.domain.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Instructor> findByPhoneNumber(String phoneNumber);

}
