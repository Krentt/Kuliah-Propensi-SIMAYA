package com.a05.simaya.keuangan.service;

import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.keuangan.model.JenisEnum;
import com.a05.simaya.keuangan.model.KeuanganModel;
import com.a05.simaya.keuangan.repository.KeuanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class KeuanganServiceImpl implements KeuanganService{

    @Autowired
    KeuanganDb keuanganDb;

    @Override
    public List<KeuanganModel> getListKeuangan() {
        return keuanganDb.findAll();
    }
}
