package com.revature.RevWorkforce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewRequestDto {
    private String deliverables;
    private String achievements;
    private String areasOfImprovement;
    private String goals;
    private String targets;
    private String deadline;
    private Integer weightage;
}
