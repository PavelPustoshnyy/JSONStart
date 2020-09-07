import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyTests {

    @Test
    public void whenSerializingUsingJsonAnyGetter_thenCorrect()
            throws IOException {

        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        String result = new ObjectMapper().writeValueAsString(bean);

        assertThat(result, containsString("attr1"));
        assertThat(result, containsString("val1"));
    }

    public void whenSerializingUsingJsonGetter_thenCorrect()
            throws JsonProcessingException {
        MyBean myBean = new MyBean(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(myBean);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    public void whenSerializingUsingJsonPropertyOrder_thenCorrect()
            throws JsonProcessingException {

        MyBean bean = new MyBean(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
    }

    public void whenSerializingUsingJsonRawValue_thenCorrect()
            throws JsonProcessingException {
        RawBean rawBean = new RawBean("My bean", "{\"attr\":false}");

        String result = new ObjectMapper().writeValueAsString(rawBean);
        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("{\"attr\":false}"));
    }

    public void whenSerializingUsingJsonValue_thenCorrect()
            throws IOException {

        String enumAsString = new ObjectMapper()
                .writeValueAsString(TypeEnumWithValue.TYPE1);

        assertThat(enumAsString, is("\"Type A\""));
        System.out.println(enumAsString);
    }

    public void whenSerializingUsingJsonRootName_thenCorrect()
            throws JsonProcessingException {
        UserWithRoot user = new UserWithRoot(1, "John");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(user);
        System.out.println(result);

        assertThat(result, containsString("John"));
        assertThat(result, containsString("user"));

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(new UserWithRootNamespace(1, "John"));
        System.out.println(xml);
        assertNotNull(xml);
    }

    public void whenSerializingUsingJsonSerialize_thenCorrect()
            throws JsonProcessingException, ParseException {
        SimpleDateFormat df
                = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        EventWithSerializer event = new EventWithSerializer("party", date);

        String result = new ObjectMapper().writeValueAsString(event);
        assertThat(result, containsString(toParse));
        System.out.println(result);
    }

    public void whenDeserializingUsingJsonCreator_thenCorrect()
            throws IOException {

        String json = "{\"id\":1,\"theName\":\"My bean\"}";

        BeanWithCreator beanWithCreator = new ObjectMapper()
                .readerFor(BeanWithCreator.class)
                .readValue(json);
        assertEquals("My bean", beanWithCreator.name);
        System.out.println(beanWithCreator.name);
        System.out.println(beanWithCreator.id);
    }

    public void whenDeserializingUsingJsonInject_thenCorrect()
            throws IOException {

        String json = "{\"name\":\"My bean\"}";

        InjectableValues inject = new InjectableValues.Std()
                .addValue(int.class, 1);
        BeanWithInject beanWithInject = new ObjectMapper().reader(inject)
                .forType(BeanWithInject.class)
                .readValue(json);

        assertEquals("My bean", beanWithInject.name);
        assertEquals(1, beanWithInject.id);
    }

    public void whenDeserializingUsingJsonAnySetter_thenCorrect()
            throws IOException {
        String json
                = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

        ExtendableBeanWithCreator bean = new ObjectMapper().readerFor(ExtendableBeanWithCreator.class)
                .readValue(json);

        assertEquals("My bean", bean.name);
        assertEquals("val2", bean.getProperties().get("attr2"));
    }
    public void whenDeserializingUsingJsonSetter_thenCorrect()
            throws IOException {

        String json = "{\"id\":123,\"name\":\"My bean\"}";

        MyBeanWithSetter bean = new ObjectMapper()
                .readerFor(MyBeanWithSetter.class)
                .readValue(json);
        assertEquals("My bean", bean.getTheName());
        System.out.println(bean.getTheId());
    }
    public void whenDeserializingUsingJsonDeserialize_thenCorrect()
            throws IOException {

        String json
                = "{\"name\":\"party\",\"eventDate\":\"20-12-2014 02:30:00\"}";

        SimpleDateFormat df
                = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        EventWithDeserializer event = new ObjectMapper()
                .readerFor(EventWithDeserializer.class)
                .readValue(json);

        assertEquals(
                "20-12-2014 02:30:00", df.format(event.eventDate));
    }
    public void whenDeserializingUsingJsonAlias_thenCorrect() throws IOException {
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);
        assertEquals("John", aliasBean.getFirstName());
        assertEquals("Green", aliasBean.getLastName());
    }
    public void whenSerializingUsingJsonIgnoreProperties_thenCorrect()
            throws JsonProcessingException {

        BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        String result = new ObjectMapper()
                .writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));

        System.out.println(result);
    }

    public void whenSerializingUsingJsonIgnore_thenCorrect()
            throws JsonProcessingException {

        BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        String result = new ObjectMapper()
                .writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));

        System.out.println(result);

    }
    public void whenSerializingUsingJsonIgnoreType_thenCorrect()
            throws JsonProcessingException {

        User.Name name = new User.Name("John", "Doe");
        User user = new User(1, name);

        String result = new ObjectMapper()
                .writeValueAsString(user);

        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("name")));
        assertThat(result, not(containsString("John")));
        System.out.println(result);
    }
    public void whenSerializingUsingJsonInclude_thenCorrect()
            throws JsonProcessingException {

        MyBeanInclude bean = new MyBeanInclude(1, null);

        String result = new ObjectMapper()
                .writeValueAsString(bean);

        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("name")));
        System.out.println("includeTest");
        System.out.println(result);
    }
    public void whenSerializingUsingJsonAutoDetect_thenCorrect()
            throws JsonProcessingException {

        PrivateBean bean = new PrivateBean(1, "My bean");

        String result = new ObjectMapper()
                .writeValueAsString(bean);

        assertThat(result, containsString("1"));
        assertThat(result, containsString("My bean"));
        System.out.println("whenSerializingUsingJsonAutoDetect_thenCorrect");
        System.out.println(result);
    }

    public void whenSerializingPolymorphic_thenCorrect()
            throws JsonProcessingException {
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);

        String result = new ObjectMapper()
                .writeValueAsString(zoo);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("dog"));
        System.out.println(result);
    }

    public void whenDeserializingPolymorphic_thenCorrect()
            throws IOException {
        String json = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";

        Zoo zoo = new ObjectMapper()
                .readerFor(Zoo.class)
                .readValue(json);

        assertEquals("lacy", zoo.animal.name);
        assertEquals(Zoo.Cat.class, zoo.animal.getClass());

    }

    public void whenUsingJsonProperty_thenCorrect()
            throws IOException {
        MyBean bean = new MyBean(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));

        MyBean resultBean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(result);
        assertEquals("My bean", resultBean.getTheName());
    }

    public void whenSerializingUsingJsonFormat_thenCorrect()
            throws JsonProcessingException, ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        EventWithFormat event = new EventWithFormat("party", date);

        String result = new ObjectMapper().writeValueAsString(event);

        assertThat(result, containsString(toParse));
    }

    public void whenSerializingUsingJsonUnwrapped_thenCorrect()
            throws JsonProcessingException, ParseException {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser user = new UnwrappedUser(1, name);

        String result = new ObjectMapper().writeValueAsString(user);

        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("name")));
        System.out.println(result);
    }

    public void whenSerializingUsingJsonView_thenCorrect()
            throws JsonProcessingException {
        Item item = new Item(2, "book", "John");

        String result = new ObjectMapper()
                .writerWithView(Views.Public.class)
                .writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));
        assertThat(result, not(containsString("John")));
        System.out.println(result);
    }
    public void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect()
            throws JsonProcessingException {
        UserWithRef user = new UserWithRef(1, "John");
        ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));

        System.out.println(result);
    }

    public void whenSerializingUsingJsonIdentityInfo_thenCorrect()
            throws JsonProcessingException {
        UserWithIdentity user = new UserWithIdentity(1, "John");
        ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
        System.out.println(result);
    }
    public void whenSerializingUsingJsonFilter_thenCorrect()
            throws JsonProcessingException {
        BeanWithFilter bean = new BeanWithFilter(1, "My bean");

        FilterProvider filters
                = new SimpleFilterProvider().addFilter(
                "myFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("name"));

        String result = new ObjectMapper()
                .writer(filters)
                .writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, not(containsString("id")));

        System.out.println(result);
    }

    public void whenSerializingUsingCustomAnnotation_thenCorrect()
            throws JsonProcessingException {
        BeanWithCustomAnnotation bean
                = new BeanWithCustomAnnotation(1, "My bean", null);

        String result = new ObjectMapper().writeValueAsString(bean);

        assertThat(result, containsString("My bean"));
        assertThat(result, containsString("1"));
        assertThat(result, not(containsString("dateCreated")));

        System.out.println(result);
    }

    public void whenSerializingUsingMixInAnnotation_thenCorrect()
            throws JsonProcessingException {
        ItemUser item = new ItemUser(1, "book", null);

        String result = new ObjectMapper().writeValueAsString(item);
        assertThat(result, containsString("owner"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(User.class, MyMixInForIgnoreType.class);

        result = mapper.writeValueAsString(item);
        assertThat(result, not(containsString("owner")));
        System.out.println(result);
    }

    public void whenDisablingAllAnnotations_thenAllDisabled()
            throws IOException {
        MyBeanDisable bean = new MyBeanDisable(1, null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
        String result = mapper.writeValueAsString(bean);

        assertThat(result, containsString("1"));
        assertThat(result, containsString("name"));
        System.out.println(result);
    }

    public void testAll() throws IOException, ParseException {
        whenSerializingUsingJsonAnyGetter_thenCorrect();
        whenSerializingUsingJsonGetter_thenCorrect();
        whenSerializingUsingJsonPropertyOrder_thenCorrect();
        whenSerializingUsingJsonRawValue_thenCorrect();
        whenSerializingUsingJsonValue_thenCorrect();
        whenSerializingUsingJsonRootName_thenCorrect();
        whenSerializingUsingJsonSerialize_thenCorrect();
        whenDeserializingUsingJsonCreator_thenCorrect();
        whenDeserializingUsingJsonInject_thenCorrect();
        whenDeserializingUsingJsonAnySetter_thenCorrect();
        whenDeserializingUsingJsonSetter_thenCorrect();
        whenDeserializingUsingJsonDeserialize_thenCorrect();
        whenDeserializingUsingJsonAlias_thenCorrect();
        whenSerializingUsingJsonIgnoreProperties_thenCorrect();
        whenSerializingUsingJsonIgnore_thenCorrect();
        whenSerializingUsingJsonIgnoreType_thenCorrect();
        whenSerializingUsingJsonInclude_thenCorrect();
        whenSerializingUsingJsonAutoDetect_thenCorrect();
        whenSerializingPolymorphic_thenCorrect();
        whenDeserializingPolymorphic_thenCorrect();
        whenUsingJsonProperty_thenCorrect();
        whenSerializingUsingJsonFormat_thenCorrect();
        whenSerializingUsingJsonUnwrapped_thenCorrect();
        whenSerializingUsingJsonView_thenCorrect();
        whenSerializingUsingJacksonReferenceAnnotation_thenCorrect();
        whenSerializingUsingJsonIdentityInfo_thenCorrect();
        whenSerializingUsingJsonFilter_thenCorrect();
        whenSerializingUsingCustomAnnotation_thenCorrect();
        whenSerializingUsingMixInAnnotation_thenCorrect();
        whenDisablingAllAnnotations_thenAllDisabled();
    }
}
