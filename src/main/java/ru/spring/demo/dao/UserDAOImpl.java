package ru.spring.demo.dao;

import org.springframework.stereotype.Repository;
import ru.spring.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public User readUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(readUser(id));
    }

    @Override
    public User getItemByUsername(String firstName) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.firstName =: firstName", User.class)
                .setParameter("firstName", firstName)
                .getSingleResult();
    }

    @Override
    public User getItemById(Long id) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id =: id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public User getItemByEmail(String email){
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email =: email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
