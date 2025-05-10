package com.tasky.tasky.domain.test;

import com.tasky.tasky.domain.test.dto.TestDto;
import com.tasky.tasky.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @PostMapping
    public TestDto create(@RequestBody TestDto dto) {
        return testService.save(dto);
    }

    @GetMapping
    public List<TestDto> getAll() {
        return testService.findAll();
    }

    @GetMapping("/{id}")
    public TestDto getOne(@PathVariable Long id) {
        return testService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        testService.delete(id);
    }
}
