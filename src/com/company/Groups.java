package com.company;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Groups {

    public static List<Person> persons;

    public static void main(String[] args) {

        Group depart1 = new Group("dep 1");
        Group depart2 = new Group("dep 2");
        Group depart3 = new Group("dep 3");

        persons = new ArrayList<Person>(Arrays.asList(
                new Person(depart1, "Ivan", "Ivanov", 30),
                new Person(depart1, "Petr", "Petrov", 66),
                new Person(depart1, "John", "Doe", 40),
                new Person(depart1, "Vlad", "Vladimirov", 40),
                new Person(depart2, "Ivan", "Ivanov", 30),
                new Person(depart2, "Petr", "Petrov", 67),
                new Person(depart2, "John", "Doe", 40),
                new Person(depart3, "Ivan", "Ivanov", 30),
                new Person(depart3, "Petr", "Petrov", 60),
                new Person(depart3, "John", "Doe", 40)));

        printGroups();
        printGroupsStream();
        printAVGYears();
        maxContPersonInDep();

    }

    public static void printGroups() {
        Set<Group> groups = new HashSet<>();
        for (Person person : persons) {
            if (person.getAge() > 65)
                groups.add(person.getGroup());
        }


        List<Group> sorted = new ArrayList<>(groups);
        Collections.sort(sorted, new Comparator<Group>() {
            public int compare(Group a, Group b) {
                return Integer.compare(a.getSize(), b.getSize());
            }
        });
        for (Group group : sorted)
            System.out.println(group.getName());
    }

    public static void printGroupsStream() {
        Comparator<Group> comparator = (a, b) -> Integer.compare(a.getSize(), b.getSize());
        persons.stream().filter(p -> p.getAge() > 65).map(p -> p.getGroup()).distinct().sorted(comparator)
                .map(g -> g.getName()).forEach(System.out::println);
    }

    public static void printAVGYears() {
        Double age = persons.stream().mapToDouble(p -> p.getAge()).average().orElse(Double.NaN);
        System.out.println(age);
    }

    public static void maxContPersonInDep() {
        Map<String, Long> result = new HashMap<>();
        result = persons.stream().map(p -> p.getGroup().getName())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);
    }
}

class Person {
    String name;
    String surname;
    int age;
    Group group;

    public Person(Group group, String name, String surname, int age) {

        this.group
                = group;
        group.add(this);

        this.name
                = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public Group getGroup() {
        return group;
    }
}

class Group {
    String name;
    List<Person> persons = new ArrayList<>();

    public Group(String name) {

        this.name
                = name;
    }

    public void add(Person person) {
        persons.add(person);
    }

    public void add(List<Person> persons) {
        persons.addAll(persons);
    }

    public int getSize() {
        return persons.size();
    }

    public String getName() {
        return name;
    }

}
