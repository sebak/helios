package com.helios.fizzbuzz.controller;

import com.helios.fizzbuzz.entity.dto.FizzBuzz;
import com.helios.fizzbuzz.service.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/fizz-buzz", produces = MediaType.APPLICATION_JSON_VALUE)
public class FizzBuzzController {
    private final FizzBuzzService fizzBuzzService;

    @Autowired
    public FizzBuzzController(FizzBuzzService fizzBuzzService) {
        this.fizzBuzzService = fizzBuzzService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> fizzBuzz(@RequestBody FizzBuzz fizzBuzz) {
        return fizzBuzzService.generateFizzBuzz(fizzBuzz);
    }

    @GetMapping("/statistic")
    public FizzBuzz statistic() {
        return fizzBuzzService.getMostUsedRequest();
    }
}
