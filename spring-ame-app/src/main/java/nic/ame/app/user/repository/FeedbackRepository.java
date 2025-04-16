package nic.ame.app.user.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.FeedbackForm;
import nic.ame.app.user.dto.UserFeedBack;
import nic.ame.app.user.service.FeedbackEntity;
import nic.ame.app.user.service.UserfeedbackService;



@Repository
public interface FeedbackRepository extends JpaRepository<UserFeedBack, Long> {
    List<UserFeedBack> findByForceNoAndUnit(Integer forceNo, String unit);
}

	
    // No need for a custom save method as JpaRepository already provides save()

