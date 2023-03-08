package com.a05.simaya.service;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.repository.AnggotaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AnggotaServiceImpl implements AnggotaService{
    @Autowired
    AnggotaDb anggotaDb;

    @Override
    public List<AnggotaModel> getListAnggota() {
        return anggotaDb.findAll();
    }

}
