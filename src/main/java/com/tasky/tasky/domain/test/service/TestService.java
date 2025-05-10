package com.tasky.tasky.domain.test.service;


import com.tasky.tasky.domain.test.dto.TestDto;
import com.tasky.tasky.domain.test.entity.Test;
import com.tasky.tasky.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  TestService {
    private final TestRepository testRepository;


    public TestDto save(TestDto dto) {
        Test memo = Test.builder()
                .memo(dto.getMemo())
                .build();
        Test saved = testRepository.save(memo);
        return new TestDto(saved.getId(), saved.getMemo());
    }

    public List<TestDto> findAll() {
        return testRepository.findAll().stream()
                .map(m -> new TestDto(m.getId(), m.getMemo()))
                .collect(Collectors.toList());
    }

    public TestDto findById(Long id) {
        return testRepository.findById(id)
                .map(m -> new TestDto(m.getId(), m.getMemo()))
                .orElse(null);
    }

    public void delete(Long id) {
        testRepository.deleteById(id);
    }

}
