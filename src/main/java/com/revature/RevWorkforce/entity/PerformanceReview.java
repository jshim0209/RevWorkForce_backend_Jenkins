package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "performance_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    @Column(name = "employee_id")
    private Integer employeeId;
    private String deliverables;
    private String achievements;
    @Column(name = "areas_of_improvement")
    private String areasOfImprovement;
    private String goals;
    private String targets;
    private String deadline;
    private Integer weightage;
}
