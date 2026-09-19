package uz.pdp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Task2 {
    public static void main(String[] args) {
        Student student = new Student(1, "Ali Valiyev", "G64", 95);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(student);
        System.out.println(json);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student {
    private long id;
    private String name;
    private String group;
    private int grade;
}
