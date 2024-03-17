package com.revature.RevWorkforce.controller;

import com.revature.RevWorkforce.dtos.PerformanceReviewRequestDto;
import com.revature.RevWorkforce.entity.PerformanceReview;
import com.revature.RevWorkforce.service.PerformanceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/performance-review")
@RequiredArgsConstructor
public class PerformanceReviewController {
    private final PerformanceReviewService performanceReviewService;

    @PostMapping("/{account_id}/submit")
    public PerformanceReview submitPerformanceReview(
            @PathVariable Integer account_id,
            @RequestBody PerformanceReviewRequestDto performanceReviewRequest) {
        return performanceReviewService.submitPerformanceReview(account_id, performanceReviewRequest);
    }

}
