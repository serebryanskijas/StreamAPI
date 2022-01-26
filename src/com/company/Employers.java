package com.company;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Employers {
    public static void main(String[] args) {
        ArrayList<Employer> employers = read();
        printAll(employers);
        printPositions(employers);
        maxSalary(employers);
    }

    public static ArrayList<Employer> read(){
        String fileName = "src/com/company/date/Person.txt";
        ArrayList<Employer> employers = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            employers = (ArrayList<Employer>) stream.map(s -> s.replaceAll("[\"]+|[\\s]+", ""))
                    .map(s -> s.split(","))
                    .map(s -> new Employer(s[0], s[1], Double.parseDouble(s[2]), Integer.parseInt(s[3]), s[4]))
                    //.forEach(System.out::println);
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employers;
    }

    public static void printAll(ArrayList<Employer> employers){
        employers.stream().forEach(System.out::println);
    }

    public static void printPositions(ArrayList<Employer> employers){
        employers.stream().map(o->o.getPosition()).distinct().forEach(System.out::println);
    }

    public static void maxSalary(ArrayList<Employer> employers){
        Comparator<Double> comparator = (a,b)->Double.compare(a,b);
        Double max = employers.stream().map(o->(double)(o.getSalary()))
                .max(comparator).orElse((double) 0);
        System.out.println(max);
    }
}

class Employer {
    String firstName;
    String secondName;
    double salary;
    int age;
    String position;

    public Employer(String firstName, String secondName, double salary, int age, String position) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.salary = salary;
        this.age = age;
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public double getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public String toString() {
        return String.format(
                "[name: %s, surname: %s, salary: %.0f age: %d, position: %s]", firstName, secondName, salary, age, position);
    }
}

