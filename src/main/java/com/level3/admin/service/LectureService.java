package com.level3.admin.service;

import com.level3.admin.dto.lecture.LectureRequestDto;
import com.level3.admin.dto.lecture.LectureResponseDto;
import com.level3.admin.entity.Lecture;
import com.level3.admin.entity.LectureCategory;
import com.level3.admin.entity.Lecturer;
import com.level3.admin.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    // 강의 등록
    @Transactional
    public LectureResponseDto createLecture(LectureRequestDto requestDto, Lecturer lecturer) {
        Lecture lecture = new Lecture(requestDto, lecturer);
        lectureRepository.save(lecture);
        return new LectureResponseDto(lecture);
    }

    // 선택 강의 조회
    @Transactional(readOnly = true)
    public LectureResponseDto getLectureById(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));
        return new LectureResponseDto(lecture);
    }

    // 전체 강의 조회
    @Transactional(readOnly = true)
    public List<LectureResponseDto> getAllLectures() {
        List<Lecture> lectures = lectureRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
        return lectures.stream()
                .map(LectureResponseDto::new)
                .collect(Collectors.toList());
    }

    // 강의 카테고리 조회
    @Transactional(readOnly = true)
    public List<LectureResponseDto> getLecturesByCategory(LectureCategory category) {
        List<Lecture> lectures = lectureRepository.findByCategory(category, Sort.by(Sort.Direction.DESC, "regDate"));
        return lectures.stream()
                .map(LectureResponseDto::new)
                .collect(Collectors.toList());
    }

    // 선택 강의 수정
    @Transactional
    public void updateLecture(Long id, LectureRequestDto requestDto) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));
        lecture.update(requestDto);
        lectureRepository.save(lecture);
    }

    // 강의 삭제
    @Transactional
    public void deleteLecture(Long id) {
        if(!lectureRepository.existsById(id)) {
            throw new NoSuchElementException("해당 강의 정보를 찾을 수 없습니다");
        }
        lectureRepository.deleteById(id);
    }
}
