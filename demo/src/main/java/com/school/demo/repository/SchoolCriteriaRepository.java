package com.school.demo.repository;

import com.school.demo.model.Teacher;
import com.school.demo.model.SchoolPage;
import com.school.demo.model.SchoolSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class SchoolCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SchoolCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Teacher> findAllTeachersWithFilters(SchoolPage schoolPage,
                                                    SchoolSearchCriteria schoolSearchCriteria){
        CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> teacherRoot = criteriaQuery.from(Teacher.class);
        Predicate predicate = getPredicate(schoolSearchCriteria, teacherRoot);
        criteriaQuery.where(predicate);
        setOrder(schoolPage, criteriaQuery, teacherRoot);

        TypedQuery<Teacher> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(schoolPage.getPageNumber() * schoolPage.getPageSize());
        typedQuery.setMaxResults(schoolPage.getPageSize());

        Pageable pageable = getPageable(schoolPage);

        long teachersCount = getTeachersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, teachersCount);
    }

    private Predicate getPredicate(SchoolSearchCriteria schoolSearchCriteria,
                                   Root<Teacher> teacherRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(schoolSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(teacherRoot.get("name"),
                            "%" + schoolSearchCriteria.getName() + "%")
            );
        }
        if(Objects.nonNull(schoolSearchCriteria.getSurname())){
            predicates.add(
                    criteriaBuilder.like(teacherRoot.get("surname"),
                            "%" + schoolSearchCriteria.getSurname() + "%")
            );
        }
       
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(SchoolPage schoolPage,
                          CriteriaQuery<Teacher> criteriaQuery,
                          Root<Teacher> teacherRoot) {
        if(schoolPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(teacherRoot.get(schoolPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(teacherRoot.get(schoolPage.getSortBy())));
        }
    }

    private Pageable getPageable(SchoolPage schoolPage) {
        Sort sort = Sort.by(schoolPage.getSortDirection(), schoolPage.getSortBy());
        return PageRequest.of(schoolPage.getPageNumber(), schoolPage.getPageSize(), sort);
    }

    private long getTeachersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Teacher> countRoot = countQuery.from(Teacher.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
