package com.meng.missyou.service;

import com.meng.missyou.model.Activity;
import com.meng.missyou.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Activity getByName(String name) {
        return this.activityRepository.findByName(name);
    }

}
