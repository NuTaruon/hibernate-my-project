package org.example.dao;

import org.example.domain.Actor;
import org.example.domain.Category;
import org.example.domain.Film;
import org.example.domain.Language;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class FilmDAO extends GenericlDAO<Film> {

    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film createNewFilm() {
        try (Session session = getCurrentSession()) {
            LanguageDAO languageDAO = new LanguageDAO(session.getSessionFactory());
            CategoryDAO categoryDAO = new CategoryDAO(session.getSessionFactory());
            ActorDAO actorDAO = new ActorDAO(session.getSessionFactory());
            Transaction transaction = session.beginTransaction();


            Language languageFilm = languageDAO.getLanguageByName("English");


            Actor actor1 = actorDAO.getOrSetActorByName("KOPAL", "RENOT");
            Actor actor2 = actorDAO.getOrSetActorByName("JENNIFER", "DAVIS");
            Actor actor3 = actorDAO.getOrSetActorByName("CHRISTIAN", "GABLE");


            Category categoryFilm = categoryDAO.findByName("Comedy");

            Film newFilm = new Film();
            newFilm.setTitle("ALIEN");
            newFilm.setLanguage(languageFilm);
            newFilm.setRentalDuration((byte) 5);
            newFilm.setRentalRate(new BigDecimal("4.99"));
            newFilm.setReplacementCost(new BigDecimal(23.99));

            session.persist(newFilm);
            session.flush();

            // 2. СВЯЗЫВАЕМ АКТЕРОВ С ФИЛЬМОМ
            // Используем только один проверенный метод (выберите тот, который исправили ранее в ActorDAO)
            actorDAO.saveFilmActor(newFilm, actor1);
            actorDAO.saveFilmActor(newFilm, actor2);
            actorDAO.saveFilmActor(newFilm, actor3);

            // 3. СВЯЗЫВАЕМ КАТЕГОРИЮ С ФИЛЬМОМ
            // Используем метод из CategoryDAO, который мы перевели на HQL с (short) приведением
            categoryDAO.saveFilmsCategory(newFilm, categoryFilm);
            save(newFilm);

            // 4. ЗАВЕРШАЕМ ТРАНЗАКЦИЮ
            transaction.commit();
            System.out.println("Фильм успешно сохранен! Его ID в базе данных: " + newFilm.getId());
            return newFilm;
        }
    }

    public Set<Actor> getActorsFilm(Film film) {
        Query<Actor> filmQuery = getCurrentSession().createNativeQuery("select * from film f" +
                " join film_actor fa on f.film_id = fa.film_id where f.film_id = :filmId", Actor.class);
        filmQuery.setParameter("filmId", film.getId());
        return new HashSet<Actor>(filmQuery.getResultList());
    }

    public void saveActorsFilm(Film film, Actor actor) {
        // Получаем текущих актеров прямо из объекта фильма, а не через запрос к БД
        Set<Actor> actors = film.getActors();

        if (actors == null) {
            actors = new HashSet<>();
        }

        actors.add(actor);
        film.setActors(actors);

        // Сохраняем или обновляем фильм (Hibernate сам обновит таблицу связей film_actor)
        update(film);
    }

    public Set<Category> getCategoriesFilm(Film film) {
        Query<Category> filmQuery = getCurrentSession().createNativeQuery("select * from film f" +
                " join film_category fc on f.film_id = fc.film_id " +
                "where f.film_id = :filmId", Category.class);;
        filmQuery.setParameter("filmId", film.getId());
        return new HashSet<Category>(filmQuery.getResultList());
    }

    public void saveCategoriesFilm(Film film, Category category) {
        Set<Category> categories = getCategoriesFilm(film);
        if(categories == null){
            categories = new HashSet<>();
        }
        categories.add(category);
        film.setCategory(categories);
        update(film);
    }

    public Film getFirstAvaliableFilmRent(){
        Query<Film> filmQuery = getCurrentSession().createQuery("select f from Film f " +
                "where f.id not in (select distinct film.id from Inventory)", Film.class);
        filmQuery.setMaxResults(1);
        return filmQuery.getSingleResult();
    }

}
