package com.a05.simaya.anggota.repository;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnggotaDb extends JpaRepository<AnggotaModel, String> {
    AnggotaModel findAnggotaModelById(String id);

    AnggotaModel findByUsername(String username);

    List<AnggotaModel> findAllByRoleEquals(RoleEnum ROLE);

}
