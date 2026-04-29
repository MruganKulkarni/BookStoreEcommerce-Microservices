package com.bookstore.feedback.service;

import com.bookstore.feedback.entity.Feedback;
import com.bookstore.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback addFeedback(Feedback feedback) {
        return repository.save(feedback);
    }

    public List<Feedback> getByProduct(Long productId) {
        return repository.findByProductId(productId);
    }
}