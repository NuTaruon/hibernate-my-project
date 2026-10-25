package org.example.dao;

import org.example.domain.Actor;
import org.example.domain.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ActorDAO extends GenericlDAO<Actor> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }

    public Actor getOrSetActorByName(String firstName, String lastName) {
        Query<Actor> actorQuery = getCurrentSession().createQuery("select a from Actor a " +
                "where a.firstName = :FIRST_NAME and a.lastName = :LAST_NAME");
        actorQuery.setParameter("FIRST_NAME", firstName);
        actorQuery.setParameter("LAST_NAME", lastName);
        Actor actor = actorQuery.uniqueResult();
        if(actor != null){
            return actor;
        }
        else {
            Actor newActor = new Actor();
            newActor.setFirstName(firstName);
            newActor.setLastName(lastName);
            save(newActor);
            return newActor;
        }
    }

    public Set<Film> getFilmsActor(Actor actor) {
        // Используем HQL с join для развертывания коллекции в плоский список
        Query<Film> filmQuery = getCurrentSession().createQuery(
                "select f from Actor a join a.films f where a.id = :actorId",
                Film.class
        );
        filmQuery.setParameter("actorId", (short) actor.getId());
        return new HashSet<>(filmQuery.getResultList());
    }

    public void saveFilmActor(Film film, Actor actor) {
        Set<Film> actorFilms = getFilmsActor(actor);
        if(actorFilms == null){
            actorFilms = new HashSet<>();
        }
        actorFilms.add(film);
        actor.setFilms(actorFilms);
        update(actor);
    }
}
