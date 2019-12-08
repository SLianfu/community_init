package life.majiang.community.community.Test;

public class Test_i_add_add {
    public static void main(String[] args) {
        int i = 1;
        i = i++;
        System.out.println(i);
        //左边：i=i+1 => i=2; 右边：i = 1；所以此时 i=1
        int j = i++; //j=1,i = 2
        int k = i + ++i * i++; //2 + 3*3,i = 4
        System.out.println("i="+i +"  j=" +j +" k=" +k);
    }
}
