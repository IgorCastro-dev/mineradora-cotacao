package org.mineradora.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mineradora.entity.QuotationEntity;

import java.util.Optional;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationEntity> {
    public Optional<QuotationEntity> findLastQuotation(){
        return find("from QuotationEntity order by id desc").firstResultOptional();
    }
}
