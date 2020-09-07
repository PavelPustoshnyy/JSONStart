import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class JSONStart {
    public static void main(String[] args) throws IOException, ParseException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Car car = new Car("yellow", "renault");
        objectMapper.writeValue(new File("target/car.json"), car);

        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car carJson = objectMapper.readValue(json, Car.class);
        objectMapper.writeValue(new File("target/carJson.json"), carJson);


        Car carJsonFile = objectMapper.readValue(new File("target/carJson.json"), Car.class);
        String carAsString = objectMapper.writeValueAsString(carJsonFile);
        System.out.println(carAsString);

        Car carURL =
                objectMapper.readValue(new URL("file:target/car.json"), Car.class);
        carAsString = objectMapper.writeValueAsString(carURL);
        System.out.println(carAsString);

        json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
        String type = jsonNode.get("type").asText();
        System.out.println(color);
        System.out.println(type);

        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});

        json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map
                = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});

        String jsonString
                = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        car = objectMapper.readValue(jsonString, Car.class);

        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();
        System.out.println(year);

        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);




        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(Car.class, new CustomCarSerializer());
        mapper.registerModule(module);
        car = new Car("yellow", "renault");
        String carJsonString = mapper.writeValueAsString(car);
        System.out.println(carJsonString);


        json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        //ObjectMapper mapper = new ObjectMapper();
        module =
                new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);
        car = mapper.readValue(json, Car.class);
        System.out.println(car.getColor());
        System.out.println(car.getType());


        //ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request(car,new Date (94,9,10,23,22,21));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);
        carAsString = objectMapper.writeValueAsString(request);
        System.out.println(carAsString);
        // output: {"car":{"color":"yellow","type":"renault"},"datePurchased":"2016-07-03 11:43 AM CEST"}

        jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        Car[] cars = objectMapper.readValue(jsonCarArray, Car[].class);

        //jsonCarArray =
        //        "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        objectMapper = new ObjectMapper();
        listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
        // print cars
        // print cars
        MyTests test = new MyTests();
        test.testAll();





    }
}
