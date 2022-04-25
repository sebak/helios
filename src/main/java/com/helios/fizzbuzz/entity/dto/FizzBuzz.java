package com.helios.fizzbuzz.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Component
public class FizzBuzz {
    @Min(1)
    private int int1;
    @Min(1)
    private int int2;
    @Min(1)
    private int limit;
    @NotBlank
    private String str1;
    @NotBlank
    private String str2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer count;

    public FizzBuzz() {
    }

    public FizzBuzz(int int1, int int2, int limit, String str1, String str2) {
        this.int1 = int1;
        this.int2 = int2;
        this.limit = limit;
        this.str1 = str1;
        this.str2 = str2;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FizzBuzz fizzBuzz = (FizzBuzz) o;
        return int1 == fizzBuzz.int1 && int2 == fizzBuzz.int2 && limit == fizzBuzz.limit && str1.equals(fizzBuzz.str1) && str2.equals(fizzBuzz.str2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(int1, int2, limit, str1, str2);
    }
}
