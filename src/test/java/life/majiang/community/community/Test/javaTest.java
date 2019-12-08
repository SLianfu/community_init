package life.majiang.community.community.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * == 号 与 equals
 * new的东西一定会在堆里面生成一个对象
 */
public class javaTest {


    public static void main(String[] args) {
        String s1 = new String("abc");

        String s2 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        Set<String> set01 = new HashSet<>();
        /*public HashSet() {        map = new HashMap<>();    }*/
        set01.add(s1);
        set01.add(s2);
        System.out.println(set01.size());
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());


        System.out.println("================");
        Person person01 = new Person("qq");
        Person person02 = new Person("qq");
        System.out.println(person01 == person02);
        System.out.println(person01.equals(person02));
        Set<Person> set02 = new HashSet<>();
        set02.add(person01);
        set02.add(person02);
        System.out.println(set02.size());
        System.out.println(person01.hashCode());
        System.out.println(person02.hashCode());

        System.out.println("---------------");
        String t2 = new String("abc");
        System.out.println(t2 == t2.intern());
        //t2.intern():从常量池里捞出来的，没有就新建一个
        //t2:指向堆里的对象

    }


}
class Person{
    private String name;
    private int age;

    public Person(String name) {
        this.name = name;
    }
}
/*false
true
1
96354
96354
================
false
false
2
1880587981
511754216*/