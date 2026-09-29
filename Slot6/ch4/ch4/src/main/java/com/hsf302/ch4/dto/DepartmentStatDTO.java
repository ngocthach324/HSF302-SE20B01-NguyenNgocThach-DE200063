package com.hsf302.ch4.dto;

public record DepartmentStatDTO(String code, String name, Long totalStudents, Double avgGpa) {

    @Override
    public String toString() {
        return String.format("%-5s | %-25s | students: %-2d | avg GPA: %s",
                code, name, totalStudents,
                avgGpa == null ? "N/A" : String.format("%.3f", avgGpa));
    }
}
