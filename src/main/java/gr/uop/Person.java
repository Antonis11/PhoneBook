package gr.uop;

public class Person
{
    private String surname;
    private String name;
    private String address;

    public Person(String surname, String name, String address) {
        this.surname = surname;
        this.name = name;
        this.address = address;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
