package com.a05.simaya.keuangan.controller;

import com.a05.simaya.anggota.payload.AnggotaDTO;
import com.a05.simaya.keuangan.service.KeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KeuanganController {

    @Autowired
    private KeuanganService keuanganService;

    @GetMapping(value = "/keuangan")
    public String ringkasanKeuangan(){
        return "keuangan/daftar-keuangan";
    }
}
