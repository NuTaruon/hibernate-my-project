package org.example.dao;

import org.example.domain.Category;
import org.example.domain.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.Set;


public class CategoryDAO extends GenericlDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }

    public Category findByName(String name){
        Query queryCategoty = getCurrentSession().createQuery("select c from Category c where c.name = :NAME");
        queryCategoty.setParameter("NAME", name);
        return (Category) queryCategoty.uniqueResult();
    }

    public Set<Film> getFilmsCategory(Category category) {
        // Здесь 'films' — это имя коллекции (поля) внутри вашего Java-класса Category
        Query<Film> filmQuery = getCurrentSession().createQuery(
                "select f from Category c join c.films f where c.id = :categoryId",
                Film.class
        );
        filmQuery.setParameter("categoryId", (short) category.getId());
        return new HashSet<>(filmQuery.getResultList());
    }

    public void saveFilmsCategory(Film film, Category category) {
        Set<Film> films = getFilmsCategory(category);
        if(films == null){
            films = new HashSet<>();
        }
        films.add(film);
        category.setFilms(films);
        update(category);
    }
}
