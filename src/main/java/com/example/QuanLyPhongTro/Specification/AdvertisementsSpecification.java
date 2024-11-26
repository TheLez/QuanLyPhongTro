package com.example.QuanLyPhongTro.Specification;

import com.example.QuanLyPhongTro.models.Advertisements;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdvertisementsSpecification {
    public static Specification<Advertisements> getAdvertisements(Map<String, String> params){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            params.forEach((key, value) -> {
                switch (key) {
                    case "province":
                        predicates.add(criteriaBuilder.like(root.get("address"), "%" + value + "%"));
                        break;
                    case "district":
                        predicates.add(criteriaBuilder.like(root.get("address"), "%" + value + "%"));
                        break;
                    case "ward":
                        predicates.add(criteriaBuilder.like(root.get("address"), "%" + value + "%"));
                        break;
                    case "area":
                        predicates.add(criteriaBuilder.lessThan(root.get("area"), Integer.parseInt(value)));
                        break;
                    case "title":
                        predicates.add(criteriaBuilder.like(root.get("title"), "%" + value + "%"));
                        break;
                    case "cost":
                        predicates.add(criteriaBuilder.lessThan(root.get("cost"), Double.parseDouble(value)));
                        break;
                    case "status":
                        predicates.add(criteriaBuilder.lessThan(root.get("status"), Integer.parseInt(value)));
                        break;
                    case "maxOccupants":
                        predicates.add(criteriaBuilder.lessThan(root.get("maxOccupants"), Integer.parseInt(value)));
                        break;
                    case "type":
                        predicates.add(criteriaBuilder.lessThan(root.get("type"), Integer.parseInt(value)));
                        break;
                    case "createdAt":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), new Date(Long.parseLong(value))));
                        break;
                }
            });
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
