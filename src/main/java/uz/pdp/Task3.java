package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.datafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class Task3 {
    public static void main(String[] args) {
        Faker faker = new Faker();
        List<BaseFaker> baseFakers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
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

        System.out.println("Har bir guruhdagi eng yuqori talabalar");
        maxGradeByGroup.forEach((group, studentOpt) ->
                studentOpt.ifPresent(s -> System.out.println("Guruh " + group + " -> Max: " + s.getFullName() + s.getGrade()))
        );

        System.out.println("\nHar bir guruhdagi eng past talabalar");
        minGradeByGroup.forEach((group, studentOpt) ->
                studentOpt.ifPresent(s -> System.out.println("Guruh " + group + " -> Min: " + s.getFullName() + s.getGrade()))
        );

        System.out.println("\nGuruhlar bo'yicha saralangan talabalar");
        Map<Integer, List<BaseFaker>> groupedAndSorted = baseFakers.stream()
                .collect(Collectors.groupingBy(
                        BaseFaker::getGroup,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparingInt(BaseFaker::getGrade).reversed())
                                        .toList()
                        )
                ));

        groupedAndSorted.forEach((group, students) -> {
            System.out.println("\n=== Guruh: " + group + " ===");
            students.forEach(s -> System.out.println(s.getFullName() + " | Grade: " + s.getGrade()));
        });

        System.out.println("\n--- Yoshi 20 dan katta TOP 3 talaba ---");
        List<BaseFaker> top3Students = baseFakers.stream()
                .filter(p -> p.getAge() > 20)
                .sorted(Comparator.comparingInt(BaseFaker::getGrade).reversed())
                .limit(3)
                .toList();

        top3Students.forEach(s ->
                System.out.println(s.getFullName() + " | Yoshi: " + s.getAge() + " | Bahosi: " + s.getGrade())
        );
    }
}

@Data
@AllArgsConstructor
class BaseFaker {
    private String uuid;
    private String fullName;
    private int group;
    private int grade;
    private int age;
    private String city;
}