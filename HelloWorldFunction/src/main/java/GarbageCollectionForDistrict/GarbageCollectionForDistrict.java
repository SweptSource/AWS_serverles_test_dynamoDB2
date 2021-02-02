package GarbageCollectionForDistrict;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Map;

@ToString
@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "wywozy_dzielnica")
public class GarbageCollectionForDistrict {

    @DynamoDBHashKey(attributeName = "dzielnica")
    private String dzielnica;

    @DynamoDBAttribute(attributeName = "dni_wywozu")
    //public List<Map<String, String>> dniWywozu; // to dziala ok !
    private List<GarbageCollectionDayInfo> dniWywozu;  //to dziala ok ale musialem 2 razy wrzucac !!
}
