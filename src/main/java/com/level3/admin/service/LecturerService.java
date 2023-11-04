package com.level3.admin.service;

import com.level3.admin.dto.lecturer.LecturerRequestDto;
import com.level3.admin.dto.lecturer.LecturerResponseDto;
import com.level3.admin.entity.Lecturer;
import com.level3.admin.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    // 강사 등록 (매니저와 스태프 모두 가능)
    public LecturerResponseDto createLecturer(LecturerRequestDto requestDto) {
        System.out.println("Service: Creating lecturer. Request Data: " + requestDto);
        Lecturer lecturer = new Lecturer(requestDto);
        lecturerRepository.save(lecturer);
        return new LecturerResponseDto(lecturer);
    }

    // 선택한 강사 정보 조회
    public LecturerResponseDto getLecturerById(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));
        return new LecturerResponseDto(lecturer);
    }

    // 전체 강사 정보 조회
    public List<LecturerResponseDto> getAllLecturers() {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        return lecturers.stream()
                .map(LecturerResponseDto::new)
                .collect(Collectors.toList());
    }

    // (매니저) 선택 강사 수정
    @Transactional
    public void updateLecturer(Long id, LecturerRequestDto requestDto) {
        // 강사 정보 찾고 수정 - 강사 아이디로 찾기
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));

        lecturer.update(requestDto);
        lecturerRepository.save(lecturer);
    }

    // (매니저) 선택 강사 삭제
    @Transactional
    public void deleteLecturer(Long id) {
        // 강사 정보가 있는지 확인
        if (!lecturerRepository.existsById(id)) {
            throw new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다");
        }
        // 강사 정보 삭제
        lecturerRepository.deleteById(id);
    }
}