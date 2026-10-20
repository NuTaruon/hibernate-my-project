package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_actor")
public class FilmActor {
    @Id
    private int id;
}
