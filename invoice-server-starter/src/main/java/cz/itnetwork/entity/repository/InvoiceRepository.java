package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long>, JpaSpecificationExecutor<InvoiceEntity> {
    InvoiceEntity findBySellerId(Long id);
    @Query(value = "SELECT SUM(price) FROM invoice", nativeQuery = true)
    int getAllTimeSum();
    @Query(value = "SELECT COUNT(id) FROM invoice", nativeQuery = true)
    int getInvoicesCount();
    @Query(value = "SELECT SUM(price) FROM invoice WHERE issued >= :date", nativeQuery = true)
    int getCurrentYearSum(@Param("date") Date currentYear);
    @Query(value = "SELECT DISTINCT seller_id FROM invoice", nativeQuery = true)
    List<Long> findSellerIds();
    @Query(value = "SELECT SUM(price) FROM invoice WHERE seller_id = :id", nativeQuery = true)
    Long findRevenueById(@Param("id")Long id);
    @Query(value = "SELECT * FROM invoice WHERE seller_id = :id OR buyer_id = :id", nativeQuery = true)
    List<InvoiceEntity> findAllByPersonId(Long id);
}
