package com.a05.simaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnggotaController {
    @GetMapping(value = "/anggota/viewall")
    public String viewAllAnggota() {
        return "anggota/daftar-anggota";
    }
}
