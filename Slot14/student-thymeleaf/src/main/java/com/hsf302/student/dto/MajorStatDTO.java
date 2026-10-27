package com.hsf302.student.dto;

public class MajorStatDTO {
    private String major;
    private String majorName;
    private long studentCount;
    private double averageGpa;

    public MajorStatDTO(String major, String majorName, long studentCount, double averageGpa) {
        this.major = major;
        this.majorName = majorName;
        this.studentCount = studentCount;
        this.averageGpa = averageGpa;
    }

    public String getMajor() { return major; }
    public String getMajorName() { return majorName; }
    public long getStudentCount() { return studentCount; }
    public double getAverageGpa() { return averageGpa; }
}
