package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

import GarbageCollectionForDistrict.GarbageCollectionForDistrict;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;



/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static LambdaLogger logger;


    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        logger = context.getLogger();
        logger.log("0.0. log info ");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        AmazonDynamoDB ddbClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion("eu-central-1")
                .build();

        DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

        //***************************************************************************************//
        //SAVE moze kiedys sie przyda jak narazie wyrzucam ,zeby nie zjadal zasobow przy odswierzaniu
/*        GarbageCollectionForDistrict testG = new GarbageCollectionForDistrict();
        testG.setDzielnica("pratnica6");
        List<GarbageCollectionDayInfo> dniWywozuTemp = new ArrayList<GarbageCollectionDayInfo>();
        testG.setDniWywozu(dniWywozuTemp);

        GarbageCollectionDayInfo garbageCollectionDayInfo1 = new GarbageCollectionDayInfo();
        garbageCollectionDayInfo1.setDzien("Dasd");
        garbageCollectionDayInfo1.setTydz("3");
        garbageCollectionDayInfo1.setTyp("Typ pla asd");

        testG.getDniWywozu().add(garbageCollectionDayInfo1);

        logger.log("garbageCollectionDayInfo1 Start");
        logger.log("garbageCollectionDayInfo1: " + garbageCollectionDayInfo1.toString());
        logger.log("testG: " + testG.toString());
        logger.log("testG: " + testG.getDniWywozu().toString());
        logger.log("garbageCollectionDayInfo1 Stop");
        mapper.save(testG);*/
        //***************************************************************************************//


        //LOAD
        //GarbageCollectionForDistrict sResult = load(mapper);

        //GET
        //a po prostu pobrac z bazy doc ? nie potrzebuje tego marshall'owac demarshallowac , serializowac , deserializowac,
        // chcailbym wywolac ta lambde inna lamda
        //AWS.DynamDB.documentClient

        //DynamoDB is abstract cannot be instantiated; musi byc import com.amazonaws.services.dynamodbv2.document.DynamoDB;
        DynamoDB dynamoDB = new DynamoDB(ddbClient);
        Table table = dynamoDB.getTable("wywozy_dzielnica");
        String dzielnica = "pratnica";
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("dzielnica", dzielnica);

        Item outcome = null;

        try {
            System.out.println("Attempting to read the item...");
            outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + dzielnica);
            System.err.println(e.getMessage());
        }


        try {
            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
            //String output = String.format("{ \"message\": \"hello world:\", \"location\": \"%s\" }", pageContents);
            //String output = sResult.toString();
            String output = outcome.toString();

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (IOException e) {
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }

    private static GarbageCollectionForDistrict load(DynamoDBMapper mapper) {
        //LambdaLogger logger = context.getLogger();
        //1 - basic
        GarbageCollectionForDistrict g = new GarbageCollectionForDistrict();
        g.setDzielnica("pratnica4");

        logger.log("before GarbageCollectionForDistrict: " + g.toString());
        GarbageCollectionForDistrict result = mapper.load(g);
        logger.log("after GarbageCollectionForDistrict: " + result.toString());
        return result;
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
