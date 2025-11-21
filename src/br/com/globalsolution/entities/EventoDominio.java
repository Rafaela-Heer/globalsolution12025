package br.com.globalsolution.entities;

import java.time.LocalDate;

public abstract class EventoDominio {
    protected LocalDate data;

    public EventoDominio(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() { return data; }
}
