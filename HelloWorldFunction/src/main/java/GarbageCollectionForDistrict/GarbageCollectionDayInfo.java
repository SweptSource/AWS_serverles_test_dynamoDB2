package GarbageCollectionForDistrict;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import javafx.util.Pair;
import lombok.*;

@ToString
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@DynamoDBDocument
public class GarbageCollectionDayInfo {

    @DynamoDBAttribute(attributeName = "dzien")
    //private Pair<String, String> dzien;
    private String dzien;

    @DynamoDBAttribute(attributeName = "tydz")
    //private  Pair<String, String> tydz;
    private String tydz;

    @DynamoDBAttribute(attributeName = "typ")
    //private  Pair<String, String> typ;
    private String typ;
}
