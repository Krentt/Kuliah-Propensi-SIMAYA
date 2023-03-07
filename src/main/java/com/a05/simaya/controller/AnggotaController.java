package com.a05.simaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnggotaController {

    @GetMapping(value = "/tambah-anggota")
    public String getPage() {
        return "anggota/form-tambah-anggota";
    }

}
