package com.tiv.spring.ai.doctor.service;

import com.tiv.spring.ai.doctor.model.SSEMessage;

public interface DoctorService {

    void chat(SSEMessage sseMessage);

}
