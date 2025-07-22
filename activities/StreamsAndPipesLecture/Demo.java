import java.util.stream.Stream;

public class Demo {
    public static void main(String args[]) {
        String[] grades = new String[] {
            "assmt,score,possible",
            "A1,7,10",
            "A2,8,10",
            "A3,9,10",
            "A4,3,10",
            "A5,10,10"
        };

        int sum = Stream.of(grades)
            .map( x -> x.split(",") )
            .map( a -> a[1] )
            .skip(1)
            .map( s -> Integer.parseInt(s) )
            .reduce( 0, (a,b) -> a+b );
            //.forEach( x -> System.out.println(x) );
        System.out.println("sum: " + sum);
    }
}