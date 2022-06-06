import java.util.Random;

public class Lab1{
    short[] a;
    float[] x;
    double[][] c;
    Random rand = new Random();

    public void printResult(){
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[i].length; j++){
                System.out.printf(" " + "%6.2f", c[i][j]);
            }
            System.out.println();
        }
    }

    public Lab1(){
        a = new short[8];
        for(int i = 0; i < a.length; i++){
            a[i] = (short) (17 - 2 * i);
        }

        x = new float[10];
        for(int i = 0; i < x.length; i++){
            /* rand.nextFloat return value between 0.0 to 1.0
            * when I multiply it with 21 I receive random values between 0.0 and 21.0
            * after we subtract 12 the range will be -12.0 to 9.0
            * */
            x[i] = rand.nextFloat() * 21 - 12;
        }

        c = new double[8][10];
        for(int i = 0; i < c.length; i++){
            for(int j = 0; j < c[i].length; j++){
                switch(a[i]){
                    case 7:
                        c[i][j] = Math.sin(Math.asin(Math.cos(x[j])));
                        break;
                    case 3:
                    case 9:
                    case 15:
                    case 17:
                        c[i][j] = Math.cos(Math.tan(Math.tan(x[j])));
                        break;
                    default:
                        c[i][j] = Math.log(Math.abs(Math.log(Math.pow(Math.E, x[j]))/(2 - Math.tan(Math.atan((x[j] - 1.5)/21)))));
                        break;
                }
            }
        }

    }

    public static void main(String[] args) {
        Lab1 lab1 = new Lab1();
        lab1.printResult();
    }
}
