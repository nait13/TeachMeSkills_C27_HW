package com.lesson49.dao;

import com.lesson49.entity.Grooup;
import com.lesson49.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public Map<String, List<Student>> getTopStudentsByGroup() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        Map<String, List<Student>> topStudentsByGroup = new HashMap<>();

        try {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<String> groupQuery = cb.createQuery(String.class);

            Root<Grooup> groupRoot = groupQuery.from(Grooup.class);
            groupQuery.select(groupRoot.get("title")).distinct(true);

            List<String> groupTitles = session.createQuery(groupQuery).getResultList();

            for (String title : groupTitles) {
                CriteriaQuery<Student> studentQuery = cb.createQuery(Student.class);
                Root<Student> studentRoot = studentQuery.from(Student.class);

                //SELECT * FROM Student s JOIN Grooup g ON s.id = g.id
                //WHERE g.title =:title
                //ORDER BY g.rating DESC
                //LIMIT 3

                studentQuery.where(cb.equal(studentRoot.get("grooup").get("title"), title));
                studentQuery.orderBy(cb.desc(studentRoot.get("recordBook").get("rating")));
                List<Student> topStudents = session.createQuery(studentQuery).setMaxResults(3).getResultList();

                topStudentsByGroup.put(title, topStudents);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return topStudentsByGroup;
    }

    public Map<String, List<Student>> getAveragePerformanceStudentInGroups() {
        Map<String, List<Student>> studentBelowAveragePerformance = new HashMap<>();

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<String> groupQuery = cb.createQuery(String.class);
        Root<Grooup> groupRoot = groupQuery.from(Grooup.class);
        groupQuery.select(groupRoot.get("title")).distinct(true);
        List<String> groupTitleList = session.createQuery(groupQuery).getResultList();

        for (String title : groupTitleList) {
            Query query = session.createQuery("from Grooup WHERE title =:title");
            query.setParameter("title", title);
            Grooup grooup = (Grooup) query.getSingleResult();

            double avgDouble = grooup.getStudents().stream().mapToInt(student -> student.getRecordBook().getRating()).average().getAsDouble();
            double avgRating = Math.round(avgDouble * 100.0) / 100.0;

            CriteriaQuery<Student> studentQuery = cb.createQuery(Student.class);
            Root<Student> studentRoot = studentQuery.from(Student.class);

            studentQuery.where(cb.and(
                            cb.lt(studentRoot.get("recordBook").get("rating"), avgRating),
                            cb.equal(studentRoot.get("grooup").get("title"), title)
                    )
            );


            List<Student> listStudent = session.createQuery(studentQuery).getResultList();
            System.out.println("AVG " + avgRating);
            System.out.println("LIST STUDENT " + listStudent);
            studentBelowAveragePerformance.put("Group " + title + " avg rating: " + avgRating, listStudent);
        }
        return studentBelowAveragePerformance;
    }


    public List<Student> getStudentsByGroup(String groupTitle, int pageNumber, int pageSize, boolean isAscending) {

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);

        // Сортировка по рейтингу
        Order order = isAscending ? cb.asc(root.get("recordBook").get("rating"))
                : cb.desc(root.get("recordBook").get("rating"));

        cq.where(cb.equal(root.get("grooup").get("title"), groupTitle)).orderBy(order);

        Query<Student> query = session.createQuery(cq);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);

        query.setMaxResults(3);

        return query.getResultList();
    }

    public long getTotalStudentsCountByGroup(String groupTitle) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Student> root = cq.from(Student.class);

        cq.select(cb.count(root)).where(cb.equal(root.get("grooup").get("title"), groupTitle));

        return session.createQuery(cq).getSingleResult();
    }
}