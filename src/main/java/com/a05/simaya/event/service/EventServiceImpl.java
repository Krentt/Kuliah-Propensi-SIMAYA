package com.a05.simaya.event.service;

import com.a05.simaya.event.model.DirektoratEnum;
import com.a05.simaya.event.model.EventModel;
import com.a05.simaya.event.payload.CreateEventDTO;
import com.a05.simaya.event.repository.EventDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventDb eventDb;

    @Override
    public void tambahEvent(CreateEventDTO eventDTO) {
        eventDb.save(makeEventModel(eventDTO));
    }

    @Override
    public List<EventModel> getListOngoing() {
        List<EventModel> listEvent = eventDb.findAll();
        if (listEvent.size() == 0){
            return null;
        } else {
            LocalDateTime now = LocalDateTime.now();
            List<EventModel> listOngoing = new ArrayList<>();
            for (EventModel event: listEvent){
                if (event.getWaktuMulai().isBefore(now) && event.getWaktuAkhir().isAfter(now)){
                    listOngoing.add(event);
                }
            }
            if (listOngoing.size() == 0){
                return null;
            }
            return listOngoing;
        }
    }

    @Override
    public List<EventModel> getListUpcoming() {
        List<EventModel> listEvent = eventDb.findAll();
        if (listEvent.size() == 0){
            return null;
        } else {
            LocalDateTime now = LocalDateTime.now();
            List<EventModel> listUpcoming = new ArrayList<>();
            for (EventModel event: listEvent){
                if (event.getWaktuMulai().isBefore(now.plusWeeks(1)) && event.getWaktuMulai().isAfter(now)){
                    listUpcoming.add(event);
                }
            }
            if (listUpcoming.size() == 0){
                return null;
            }
            return listUpcoming;
        }
    }

    private EventModel makeEventModel(CreateEventDTO eventDTO) {
        EventModel eventModel = new EventModel();
        eventModel.setNamaEvent(eventDTO.getNamaEvent());
        eventModel.setDeskripsi(eventDTO.getDeskripsi());
        eventModel.setDirektorat(DirektoratEnum.valueOf(eventDTO.getDirektorat()));
        eventModel.setProgramKerja(eventDTO.getProgramKerja());
        eventModel.setTempatPelaksanaan(eventDTO.getTempatPelaksanaan());
        eventModel.setWaktuMulai(eventDTO.getWaktuMulai());
        eventModel.setWaktuAkhir(eventDTO.getWaktuAkhir());
        eventModel.setPenanggungJawab(eventDTO.getPenanggungJawab());

        return eventModel;
    }
}
