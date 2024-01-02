package com.level3.admin.domain.instructor.service;

import com.level3.admin.domain.course.repository.CourseRepository;
import com.level3.admin.domain.instructor.dto.InstructorRequestDto;
import com.level3.admin.domain.instructor.dto.InstructorResponseDto;
import com.level3.admin.domain.instructor.entity.Instructor;
import com.level3.admin.domain.instructor.repository.InstructorRepository;
import com.level3.admin.global.exception.CustomException;
import com.level3.admin.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public InstructorResponseDto registerInstructor(InstructorRequestDto instructorRequestDto) {
        validatePhoneNumberOnCreate(instructorRequestDto.getPhoneNumber());

        Instructor instructor = new Instructor(instructorRequestDto);
        Instructor registeredInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(registeredInstructor);
    }

    public InstructorResponseDto getInstructorDetails(Long instructorId) {
        Instructor instructor = validateGetInstructor(instructorId);
        return new InstructorResponseDto(instructor);
    }

    @Transactional
    public InstructorResponseDto reviseInstructorDetails(Long instructorId, InstructorRequestDto instructorRequestDto) {
        Instructor instructor = validateGetInstructor(instructorId);
        validatePhoneNumberOnUpdate(instructorId, instructorRequestDto.getPhoneNumber());

        instructor.updateInstructorDetails(instructorRequestDto);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(updatedInstructor);
    }

    @Transactional
    public void deleteInstructor(Long instructorId) {
        courseRepository.deleteByInstructorId(instructorId); //강사가 촬영한 강의들 삭제
        Instructor instructor = validateGetInstructor(instructorId);
        instructorRepository.delete(instructor);
    }

    // 강사 유효성 검증: 주어진 ID의 강사가 데이터베이스에 존재하는지 확인
    private Instructor validateGetInstructor(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INSTRUCTOR));
    }

    // 전화번호 유효성 검증 (생성 시): 전화번호가 데이터베이스에 존재하지 않는지 확인
    private void validatePhoneNumberOnCreate(String phoneNumber) {
        if (phoneNumber != null && instructorRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(ErrorCode.PHONENUMBER_ALREADY_EXISTS);
        }
    }

    // 전화번호 유효성 검증 (수정 시): 전화번호가 다른 강사와 중복되지 않는지 확인
    private void validatePhoneNumberOnUpdate(Long instructorId, String phoneNumber) {
        instructorRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(instructor -> {
                    if (!instructor.getId().equals(instructorId)) {
                        throw new CustomException(ErrorCode.PHONENUMBER_ALREADY_EXISTS);
                    }
                });
    }

}
