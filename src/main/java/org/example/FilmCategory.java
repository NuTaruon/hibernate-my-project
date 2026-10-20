package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_category")
public class FilmCategory {
    @Id
    private int id;
}
