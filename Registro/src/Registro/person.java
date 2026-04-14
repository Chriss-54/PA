package Registro;

public class person {
    public String name;
    public int age;
    public long phone;

    public person() {
        name = "";
        age = 0;
        phone = 0L;
    }

    public person(String n, int a, long p) {
        this.name = n;
        this.age = a;
        this.phone = p;
    }

    public String info() {
        return String.format("%s | %d años | (+593)%d", name, age, phone);
    }
}