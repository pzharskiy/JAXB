import javax.xml.bind.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Application {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Group createJavaObjectExample1() throws ParseException {
        Group group = new Group();
        group.setName("Test Group 1");
            group.getMembers().add(createPerson("Alice", "Anderssen", "1970-01-01"));
            group.getMembers().add(createPerson("Bert", "Bobo", "1980-02-02"));
        return group;
    }

    public Person createPerson(String firstName, String lastName, String birthDate) throws ParseException {
        Person person = new Person();
        person.setBirthDate(format.parse(birthDate));
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    public void marshallExample() throws JAXBException, ParseException {
        //...
        JAXBContext context = JAXBContext.newInstance(Group.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(createJavaObjectExample1(), System.out);
    }

    public void unmarshallExample() throws JAXBException, ParseException {
        //...
        JAXBContext context = JAXBContext.newInstance(Group.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                Boolean.TRUE);

        //write XML to an array of bytes
        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();
        marshaller.marshal(createJavaObjectExample1(), baos);

        //read XML from array of bytes
        InputStream bais =
                new ByteArrayInputStream(baos.toByteArray());
        Unmarshaller unmarshaller =
                context.createUnmarshaller();
        Object o = unmarshaller.unmarshal(bais);

        //prove the Group is recreated
        Group groupCopy = (Group) o;
        System.out.println("Objects created from XML:");
        System.out.println(groupCopy.getName());
        for (Person person : groupCopy.getMembers()) {
            System.out.println(person.getFirstName());
        }
    }




    public static void main(String[] args) {
        Application instance = new Application();
        try {

            instance.marshallExample();
            instance.unmarshallExample();

        } catch (JAXBException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}
