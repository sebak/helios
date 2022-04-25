package com.helios.robot;

public class Robot {
    Integer numberOfBox;
    String packaging;

    public Robot(Integer numberOfBox, String packaging) {
        this.numberOfBox = numberOfBox;
        this.packaging = packaging;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "numberOfBox=" + numberOfBox +
                ", packaging='" + packaging + '\'' +
                '}';
    }
}
