package com.itmentorcommunityplatform.projectservice.repository;

import com.itmentorcommunityplatform.projectservice.model.Review;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ListCrudRepository<Review, Long> {
}