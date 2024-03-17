package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer> {

}
