package cz.itnetwork.dto.statisticsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatistics {
    private int currentYearSum;
    private int allTimeSum;
    private int invoiceCount;
}
