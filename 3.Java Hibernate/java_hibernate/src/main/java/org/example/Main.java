package org.example;

import org.example.utils.HibernateHelper;
import org.example.entities.CategoryEntity;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SimpleInsert();
        SimpleInsertFactory();


        /*try {
            var result = session.createSelectionQuery("from CategoryEntity", CategoryEntity.class)
                    .getResultList();
            result.forEach(System.out::println);
        }catch (Exception e) {
            System.out.println("Хюстон у нас проблеми "+e);
        }*/
    }


    private static void SimpleInsert() {
        var session = HibernateHelper.getSession();
        try {
            session.beginTransaction();
            CategoryEntity [] list = new CategoryEntity[2];
            list[0] = new CategoryEntity();
            list[0].setName("Калабуджа");
            //session.save(category);
            session.persist(list[0]);

            list[1] = new CategoryEntity();
            list[1].setName("Пельмені");
            session.persist(list[1]);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("У нас проблеми Хюстон :("+e);
        }
        finally {
            session.close();
        }
    }

    private static void SimpleInsertFactory() {
        var sessionFactory = HibernateHelper.getSessionFactory();
        sessionFactory.inTransaction(session -> {
            session.persist(new CategoryEntity("Кабачок"));
            session.persist(new CategoryEntity("Диня"));
        });
        sessionFactory.close();
    }

}
