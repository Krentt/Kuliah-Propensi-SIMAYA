package com.a05.simaya.event.repository;

import com.a05.simaya.event.model.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventDb extends JpaRepository<EventModel, Long> {
    @Query("SELECT COUNT(e) FROM EventModel e WHERE e.listProgres IS NOT EMPTY AND (SELECT COUNT(p) FROM e.listProgres p WHERE p.status = true) = SIZE(e.listProgres) AND e.waktuAkhir < CURRENT_TIMESTAMP() AND e.isDeleted = false")
    Integer countEventsWithAllProgressDone();

    @Query("SELECT COUNT(e) FROM EventModel e WHERE e.listProgres IS NOT EMPTY AND (SELECT COUNT(p) FROM e.listProgres p WHERE p.status = false) > 0 AND e.waktuAkhir < CURRENT_TIMESTAMP() AND e.isDeleted = false")
    Integer countEventsWithAtLeastOneProgressNotDone();
    List<EventModel> findAllByIsDeletedIsFalse();

    EventModel getEventModelByNamaEvent(String nama);
}
