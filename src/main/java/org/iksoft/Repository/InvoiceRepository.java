package org.iksoft.Repository;

import org.iksoft.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Optional<Invoice> findByOrder_Id(Integer orderId);
    List<Invoice> findAllByOrder_Customer_Id(Integer customerId);
}
