package cz.itnetwork.entity.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceFilter {
    private Long buyerId;
    private Long sellerId;
    private String product;
    private Long minPrice;
    private Long maxPrice;
    private int limit = 10;
}
