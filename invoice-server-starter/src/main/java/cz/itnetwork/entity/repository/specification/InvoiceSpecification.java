package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import cz.itnetwork.entity.filter.InvoiceFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {
    private final InvoiceFilter filter;
    @Override
    public Specification<InvoiceEntity> and(Specification<InvoiceEntity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<InvoiceEntity> or(Specification<InvoiceEntity> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        if (filter.getBuyerId() != null) {
            Join<PersonEntity, InvoiceEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicateList.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), filter.getBuyerId()));
        }
        if(filter.getSellerId() != null) {
            Join<PersonEntity, InvoiceEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicateList.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), filter.getSellerId()));
        }
        if (filter.getProduct() != null) {
            predicateList.add(criteriaBuilder.equal(root.get(InvoiceEntity_.PRODUCT), filter.getProduct()));
        }
        if (filter.getMinPrice() != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), filter.getMaxPrice()));
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
