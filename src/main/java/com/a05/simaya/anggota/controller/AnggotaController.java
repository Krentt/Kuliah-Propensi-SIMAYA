package com.a05.simaya.anggota.controller;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.model.ProfileModel;
import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.anggota.repository.AnggotaDb;
import com.a05.simaya.anggota.service.AnggotaService;
import com.a05.simaya.event.model.DirektoratEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AnggotaController {

    @Autowired
    private AnggotaService anggotaService;

    @GetMapping(value = "/tambah-anggota")
    public String getTambahAnggotaPage(Model model) {
        AnggotaDTO anggota = new AnggotaDTO();

        model.addAttribute("anggota", anggota);
        return "anggota/form-tambah-anggota";
    }

    @PostMapping(value = "/anggota/viewall")
    public String postForm(AnggotaDTO anggota, ModelMap model) {
        anggotaService.tambahAnggota(anggota);

        String info = "Data anggota dengan nama " + anggota.getNamaDepan() + " " +
                anggota.getNamaBelakang() + " telah berhasil ditambahkan";
        model.addAttribute("modal_add", info);

        return "anggota/daftar-anggota";
    }

    @GetMapping(value = "/ubah-profil/{id}")
    public String getUbahProfilePage(@PathVariable String id, Model model) {
        AnggotaDTO updateAnggotaDTO = anggotaService.getInfoAnggota(id);

        model.addAttribute("updateAnggota", updateAnggotaDTO);
        return "anggota/form-ubah-profile";
    }

    @PostMapping(value = "/ubah-profil")
    public String ubahProfile(AnggotaDTO updateAnggota) {
        anggotaService.updateDataAnggota(updateAnggota);
        return "redirect:/home";
    }

    @GetMapping(value = "/ubah-data-anggota/{id}")
    public String getUbahDataAnggotaPage(@PathVariable String id, Model model) {
        AnggotaDTO updateAnggotaDTO = anggotaService.getInfoAnggota(id);

        model.addAttribute("updateAnggota", updateAnggotaDTO);
        return "anggota/form-ubah-data-anggota";
    }

    @PostMapping(value = "/ubah-data-anggota")
    public String  ubahDataAnggota(AnggotaDTO updateAnggota) {
        anggotaService.updateDataAnggota(updateAnggota);
        return "redirect:/anggota/viewall";
    }

    @GetMapping(value = "/anggota/viewall")
    public String viewAllAnggota() {
        return "anggota/daftar-anggota";
    }

    @GetMapping(value = "/profil")
    public String profilPage(Model model,
                             Principal principal) {
        AnggotaModel anggota = anggotaService.getAnggotaByUsername(principal.getName());

        model.addAttribute("anggota", anggota);
        model.addAttribute("aset", getAset(anggota.getProfile()));
        model.addAttribute("divisi", getDivisi(anggota.getProfile().getDivisi()));

        return "anggota/profile";
    }

    public String getAset(ProfileModel profile) {
        String res = "-";

        if (profile.getIsPunyaMobil().equals(Boolean.TRUE)) {
            res = "Mobil";
        }

        if (profile.getIsPunyaMotor().equals(Boolean.TRUE)) {
            res = res.equals("-") ? res + ", Motor" : "Motor";
        }

        if (profile.getIsPunyaRumah().equals(Boolean.TRUE)) {
            res = res.equals("-") ? res + ", Rumah" : "Rumah";
        }

        if (profile.getIsPunyaVila().equals(Boolean.TRUE)) {
            res = res.equals("-") ? res + ", Vila" : "Vila";
        }
        return res;
    }

    public String getDivisi(DirektoratEnum direktorat) {
        if (direktorat.equals(DirektoratEnum.KEUANGAN_PROGRAM)) return "Keuangan dan Program";
        if (direktorat.equals(DirektoratEnum.SDM_OPERASIONAL)) return "SDM dan Operasional";
        if (direktorat.equals(DirektoratEnum.PENGEMBANGAN_USAHA)) return "Pengembangan Usaha";
        else return "Unknown";
    }
}
