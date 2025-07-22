package gr.uop;

import java.util.ArrayList;

public class PhoneBook {
    private ArrayList<Person> data = new ArrayList<>();

    public PhoneBook() {
        data.add(new Person("Platis", "Nikos", "Kalavriton 2\n22132 Tripoli"));
        data.add(new Person("Trifonopoulos", "Christos", "Naypliou 44\n22132 Tripoli"));
        data.add(new Person("Lepouras", "Giorgos", "Karaiskaki 30\n22131 Tripoli"));
    }

    public int getPersonCount() {
        return data.size();
    }

    public Person getPerson(int i) {
        return data.get(i);
    }

    public void setPersonData(int i, String surname, String name, String address) {
        Person p = data.get(i);
        p.setSurname(surname);
        p.setName(name);
        p.setAddress(address);
    }

    public void close() {
        data = null;
    }
}
