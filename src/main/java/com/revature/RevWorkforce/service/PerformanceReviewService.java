package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.dtos.PerformanceReviewRequestDto;
import com.revature.RevWorkforce.entity.Account;
import com.revature.RevWorkforce.entity.PerformanceReview;
import com.revature.RevWorkforce.exception.AccountNotFound;
import com.revature.RevWorkforce.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceReviewService {
    private final PerformanceReviewRepository performanceReviewRepository;
    private final NotificationService notificationService;
    private final AccountService accountService;

    public PerformanceReview submitPerformanceReview(Integer employeeId, PerformanceReviewRequestDto performanceReviewRequest) {
        PerformanceReview reviewToSubmit = new PerformanceReview();
        reviewToSubmit.setEmployeeId(employeeId);
        reviewToSubmit.setAchievements(performanceReviewRequest.getAchievements());
        reviewToSubmit.setDeliverables(performanceReviewRequest.getDeliverables());
        reviewToSubmit.setAreasOfImprovement(performanceReviewRequest.getAreasOfImprovement());
        reviewToSubmit.setGoals(performanceReviewRequest.getGoals());
        reviewToSubmit.setTargets(performanceReviewRequest.getTargets());
        reviewToSubmit.setDeadline(performanceReviewRequest.getDeadline());
        reviewToSubmit.setWeightage(performanceReviewRequest.getWeightage());

        Account account = null;

        try {
            account = accountService.getAccountByAccountId(employeeId);
        }
        catch (AccountNotFound e) {
            throw e;
        }

        if (account.getManagerId() != null) {
            notificationService.addNotification (
                    account.getManagerId(),
                    account.getFullName() + " has submitted a performance review."
            );
        }
        
        return performanceReviewRepository.saveAndFlush(reviewToSubmit);
    }

}
