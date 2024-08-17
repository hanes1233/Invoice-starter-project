package cz.itnetwork.dto.statisticsDTO;

//region imports
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//endregion

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonStatistics {
    @JsonProperty("personId")
    private Long id;
    @JsonProperty("personName")
    private String name;
    @JsonProperty("revenue")
    private Long revenue;
}
