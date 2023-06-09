package com.a05.simaya.anggota.service;

import com.a05.simaya.anggota.model.*;
import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.anggota.repository.AnggotaDb;
import com.a05.simaya.anggota.repository.ProfileAnggotaDb;
import com.a05.simaya.anggota.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AnggotaServiceImpl implements AnggotaService {

    @Autowired
    AnggotaDb anggotaDb;

    @Autowired
    ProfileAnggotaDb profileAnggotaDb;

    @Autowired
    FileUploadUtil fileUploadUtil;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void tambahAnggota(AnggotaDTO anggota) {
        AnggotaModel anggotaModel = setAnggotaModel(anggota, new AnggotaModel());
        ProfileModel profileModel = new ProfileModel();
        setUsernamePassword(anggota, anggotaModel);
        anggotaModel.setProfile(profileModel);
        anggotaDb.save(anggotaModel);
        profileAnggotaDb.save(profileModel);
    }

    @Override
    public List<AnggotaModel> getListAnggota() {
        return anggotaDb.findAll();
    }

    @Override
    public AnggotaDTO getInfoAnggota(String id) {
        AnggotaDTO updateAnggotaDTO = new AnggotaDTO();
        AnggotaModel anggotaModel = anggotaDb.findAnggotaModelById(id);

        updateAnggotaDTO.setId(id);
        updateAnggotaDTO.setNamaDepan(anggotaModel.getNamaDepan());
        updateAnggotaDTO.setNamaBelakang(anggotaModel.getNamaBelakang());
        updateAnggotaDTO.setEmail(anggotaModel.getEmail());
        updateAnggotaDTO.setNomorHP(anggotaModel.getNomorHP());
        updateAnggotaDTO.setRole(String.valueOf(anggotaModel.getRole()));
        updateAnggotaDTO.setGolonganDarah(String.valueOf(anggotaModel.getGolonganDarah()));
        updateAnggotaDTO.setTempatLahir(anggotaModel.getTempatLahir());
        updateAnggotaDTO.setTanggalLahir(anggotaModel.getTanggalLahir());
        updateAnggotaDTO.setJenisKelamin(anggotaModel.getJenisKelamin());
        updateAnggotaDTO.setStatusKeanggotaan(anggotaModel.getStatusKeanggotaan());

        updateAnggotaDTO.setProfile(anggotaModel.getProfile());
        log.info("On Get Info Anggota: " + updateAnggotaDTO.getProfile().getPhotoUrl());

        return updateAnggotaDTO;
    }

    @Override
    public void updateDataAnggota(AnggotaDTO anggotaDTO) {
        AnggotaModel anggota = anggotaDb.findAnggotaModelById(anggotaDTO.getId());
        Long id_profile = anggota.getProfile().getIdProfile();

        AnggotaModel updatedAnggota = setAnggotaModel(anggotaDTO, anggota);
        ProfileModel updateProfile = setProfileAnggota(anggotaDTO.getProfile(), anggota.getProfile());
        updateProfile.setIdProfile(id_profile);

        profileAnggotaDb.save(updateProfile);
        anggotaDb.save(updatedAnggota);
    }

    @Override
    public String encrypt(String password) {
        return encoder.encode(password);
    }

    @Override
    public AnggotaModel getAnggotaByUsername(String username) {
        return anggotaDb.findByUsername(username);
    }

    @Override
    public AnggotaModel getAnggotaById(String id) {
        return anggotaDb.findAnggotaModelById(id);
    }
    @Override
    public boolean cekPassword(String id, String oldPassword) {
        AnggotaModel anggotaModel = anggotaDb.findAnggotaModelById(id);
        return encoder.matches(oldPassword, anggotaModel.getPassword());
    }

    @Override
    public void gantiPassword(String id, String newPassword) {
        AnggotaModel anggotaModel = anggotaDb.findAnggotaModelById(id);
        anggotaModel.setPassword(encrypt(newPassword));
    }

    @Override
    public String uploadProfile(MultipartFile image, String username, String pastUrl) throws IOException {
        log.info(String.valueOf(image.isEmpty()));
        if (image.isEmpty())
            return pastUrl;

        String uploadedUrl = fileUploadUtil.saveFile(username, image);
        return uploadedUrl;
    }

    @Override
    public List<AnggotaModel> getListAnggotaBasedonRole(RoleEnum ROLE) {
        return anggotaDb.findAllByRoleEquals(ROLE);
    }

    private AnggotaModel setAnggotaModel(AnggotaDTO anggotaDTO, AnggotaModel anggotaModel) {
        anggotaModel.setRole(RoleEnum.valueOf(anggotaDTO.getRole()));
        anggotaModel.setEmail(anggotaDTO.getEmail());
        anggotaModel.setTanggalLahir(anggotaDTO.getTanggalLahir());
        anggotaModel.setTempatLahir(anggotaDTO.getTempatLahir());
        anggotaModel.setGolonganDarah(GolDarEnum.valueOf(anggotaDTO.getGolonganDarah()));
        anggotaModel.setJenisKelamin(anggotaDTO.getJenisKelamin());
        anggotaModel.setNamaDepan(anggotaDTO.getNamaDepan());
        anggotaModel.setNamaBelakang(anggotaDTO.getNamaBelakang());
        anggotaModel.setNomorHP(anggotaDTO.getNomorHP());
        anggotaModel.setStatusKeanggotaan(anggotaDTO.getStatusKeanggotaan());
        anggotaModel.setProfile(anggotaDTO.getProfile());

        return anggotaModel;
    }

    private void setUsernamePassword(AnggotaDTO anggotaDTO, AnggotaModel anggotaModel) {
        anggotaModel.setUsername(anggotaDTO.getUsername());
        anggotaModel.setPassword(encrypt(anggotaDTO.getPassword()));
    }


    private ProfileModel setProfileAnggota(ProfileModel profileDTO, ProfileModel profileModel) {
        profileModel.setDivisi(profileDTO.getDivisi());
        profileModel.setPekerjaan(profileDTO.getPekerjaan());
        profileModel.setIsMengelolaMentoring(profileDTO.getIsMengelolaMentoring());
        profileModel.setIsPunyaMobil(profileDTO.getIsPunyaMobil());
        profileModel.setIsPunyaMotor(profileDTO.getIsPunyaMotor());
        profileModel.setIsPunyaRumah(profileDTO.getIsPunyaRumah());
        profileModel.setIsPunyaVila(profileDTO.getIsPunyaVila());
        profileModel.setCatatan(profileDTO.getCatatan());
        profileModel.setPhotoUrl(profileDTO.getPhotoUrl());

        return profileModel;
    }


}
