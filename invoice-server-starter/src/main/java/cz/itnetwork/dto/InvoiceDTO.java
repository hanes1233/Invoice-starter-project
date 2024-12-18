package cz.itnetwork.dto;
//region imports
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
//endregion

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long id;
    private int invoiceNumber;
    private PersonDTO seller;
    private PersonDTO buyer;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issued;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private String product;
    private Long price;
    private int vat;
    private String note;

}
