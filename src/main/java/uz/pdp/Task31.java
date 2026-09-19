package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.datafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class Task31 {
    public static void main(String[] args) {
        Faker faker = new Faker();
        List<BaseFaker> baseFakers = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            BaseFaker baseFaker = new BaseFaker(
                    faker.internet().uuid(),
                    faker.name().fullName(),
                    faker.number().numberBetween(1, 10),
                    faker.number().numberBetween(20, 100),
                    faker.number().numberBetween(10, 40),
                    faker.address().city()
            );
            baseFakers.add(baseFaker);
        }
        baseFakers.forEach(System.out::println);
        System.out.println();

       /* IntSummaryStatistics intSummaryStatistics = baseFakers
                .stream()
                .mapToInt(BaseFaker::getGrade)
                .summaryStatistics();
        System.out.println("min grade : " + intSummaryStatistics.getMin());
        System.out.println("max grade : " + intSummaryStatistics.getMax());*/

        // 1. Har bir guruhdagi eng yuqori baholi talaba
        Map<Integer, Optional<BaseFaker>> maxGradeByGroup = baseFakers.stream()
                .collect(Collectors.groupingBy(
                        BaseFaker::getGroup,
                        Collectors.maxBy(Comparator.comparingInt(BaseFaker::getGrade))
                ));

        Map<Integer, Optional<BaseFaker>> minGradeByGroup = baseFakers.stream()
                .collect(Collectors.groupingBy(
                        BaseFaker::getGroup,
                        Collectors.minBy(Comparator.comparingInt(BaseFaker::getGrade))
                ));

        System.out.println("--- Har bir guruhdagi eng yuqori talabalar ---");
        maxGradeByGroup.forEach((group, studentOpt) -> {
            studentOpt.ifPresent(s -> System.out.println(group + " -> Max: " + s.getFullName() + " (" + s.getGrade() + ")"));
        });
        System.out.println();

        System.out.println("--- Har bir guruhdagi eng past talabalar ---");
        minGradeByGroup.forEach((group, studentOpt) -> {
            studentOpt.ifPresent(s -> System.out.println(group + " -> Min: " + s.getFullName() + " (" + s.getGrade() + ")"));
        });
        System.out.println();

        System.out.println("Grade kamayish tartibida");
        List<BaseFaker> list = baseFakers
                .stream()
                .sorted(Comparator.comparing(BaseFaker::getGrade).reversed())
                .toList();
        list.forEach(System.out::println);
        System.out.println();

        System.out.println("Yoshi 20 dan kota va top 3 gradelar");
        List<BaseFaker> list1 = baseFakers.stream().filter(p -> p.getAge() > 20)
                .sorted(Comparator.comparing(BaseFaker::getGrade).reversed())
                .limit(3)
                .toList();
        list1.forEach(System.out::println);

    }

}

@Data
@AllArgsConstructor
class BaseFaker2 {
    private String uuid;
    private String fullName;
    private int group;
    private int grade;
    private int age;
    private String city;

}