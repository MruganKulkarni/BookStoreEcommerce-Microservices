package com.bookstore.feedback.controller;

import com.bookstore.feedback.entity.Feedback;
import com.bookstore.feedback.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public Feedback add(@RequestBody Feedback feedback) {
        return service.addFeedback(feedback);
    }

    @GetMapping("/{productId}")
    public List<Feedback> get(@PathVariable Long productId) {
        return service.getByProduct(productId);
    }
}