package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.entity.Item;

import java.util.List;

@Repository
@AllArgsConstructor
public class ItemStoreImpl implements ItemStore {

    private final SessionFactory sf;

    @Override
    public Item create(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public List<Item> findAll(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery(
                        "from Item where userId = :fUserId", Item.class
                ).setParameter("fUserId", userId)
                .list();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    public List<Item> findCompleted(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> rsl = session.createQuery(
                        "from Item where done = true AND userId = :fUserId", Item.class
                ).setParameter("fUserId", userId)
                .list();
        session.getTransaction();
        session.close();
        return rsl;
    }

    public List<Item> findNew(int userId) {
        List<Item> all = findAll(userId);
        List<Item> allCompleted = findCompleted(userId);
        all.removeAll(allCompleted);
        return all;
    }

    @Override
    public boolean update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "update Item set name = :fName, description = :fDesc,"
                                + " created = :fCreated ,done = :fDone where "
                                + "id = :fId AND userId = :fUserId"
                ).setParameter("fName", item.getName())
                .setParameter("fDesc", item.getDescription())
                .setParameter("fCreated", item.getCreated())
                .setParameter("fDone", item.isDone())
                .setParameter("fId", item.getId())
                .setParameter("fUserId", item.getUserId())
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public boolean delete(int id, int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean rsl = session.createQuery(
                        "delete from Item where id = :fId AND userId = :fUserId "
                ).setParameter("fId", id)
                .setParameter("fUserId", userId)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public Item findById(int id, int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item rsl = session.createQuery(
                        "from Item where id = :fId AND userId = :fUserId", Item.class
                ).setParameter("fId", id)
                .setParameter("fUserId", userId)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return rsl;
    }

    @Override
    public void close() {
        sf.close();
    }
}
