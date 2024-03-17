package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.entity.Holiday;
import com.revature.RevWorkforce.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiscService {

    @Autowired
    HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }
}
