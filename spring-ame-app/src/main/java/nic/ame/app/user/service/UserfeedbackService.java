package nic.ame.app.user.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.model.FeedbackForm;
import nic.ame.app.user.dto.UserFeedBack;
import nic.ame.app.user.repository.FeedbackRepository;

@Service
public class UserfeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void saveFeedback(FeedbackForm feedbackForm) {
        UserFeedBack feedbackEntity = new UserFeedBack();
        feedbackEntity.setIrlaNo(feedbackForm.getIrlaNo());
        feedbackEntity.setName(feedbackForm.getName());
        feedbackEntity.setDesignation(feedbackForm.getDesignation());
        feedbackEntity.setForceName(feedbackForm.getForceName()); // Correct field name
        feedbackEntity.setFeedback(feedbackForm.getFeedback());
        feedbackEntity.setStarRating(feedbackForm.getStarRating());
        feedbackEntity.setForceNo(feedbackForm.getForceNo());
        feedbackEntity.setUnit(feedbackForm.getUnit());
        System.out.println(feedbackEntity.toString());
        feedbackRepository.save(feedbackEntity);
    }

    public List<UserFeedBack> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<UserFeedBack> getFeedbackByForceAndUnit(Integer forceId, String unitId) {
        // Fetch feedback based on forceId and unitId
        return feedbackRepository.findByForceNoAndUnit(forceId, unitId);
    }
}
