package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Иванов", "Петров", "Сидоров");
        List<People> peoples = new ArrayList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < 12_000_000; i++) {
            peoples.add(new People(names.get(
                    new Random().nextInt(names.size())),
                    new Random().nextInt(100),
                    Sex.randomSex()));
        }
        long count = peoples.parallelStream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27 && x.getSex() == Sex.MAN)
                .count();
        System.out.println("мужчин-военнообязанных " + count);

        double average = peoples.parallelStream()
                .filter(x -> x.getSex() == Sex.MAN)
                .mapToInt(People::getAge).average().getAsDouble();
        System.out.println("средний возраст " + Math.round(average));

       long count2 = peoples.parallelStream()
                .filter(x -> x.getAge() >= 18)
                .filter(x -> x.getSex() == Sex.MAN && x.getAge() <= 60 || x.getSex() == Sex.WOMEN && x.getAge() <= 65)
                .count();
       System.out.println("потенциально работоспособных людей " + count2);

        long stopTime = System.nanoTime();
        double processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }
}
