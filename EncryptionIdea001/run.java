/**
 * Author: Nathan van der Velde
 * Date Created: 2018-07-06
 */

//IMPORTS
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import static java.lang.System.out;

public class run
{
    public static void main(String [] args)
    {
        Scanner sc = new Scanner(System.in);
        out.println("Please enter in the string to encrypt.");
        String input = sc.nextLine();
        out.println(encryptor(input));
    }//END main

    public static String encryptor(String baseString)
    {
        byte [] byteArr = baseString.getBytes();
        String [] tempArr = new String[byteArr.length];
        for(int ii=0;ii<byteArr.length;ii++) 
        {
            tempArr[ii]=String.format("%8s", Integer.toBinaryString(byteArr[ii] & 0xFF)).replace(' ', '0');
        }//END FOREACH
        String [] Xs = new String[tempArr.length];
        String [] Ys = new String[tempArr.length];
        for(int ii=0;ii<tempArr.length;ii++)
        {
            Xs[ii] = tempArr[ii].substring(0,4);
            Ys[ii] = tempArr[ii].substring(4,8);
        }//END FOR
        int [] xs = new int[Xs.length];
        int [] ys = new int[Ys.length];
        for(int ii=0;ii<tempArr.length;ii++)
        {
            xs[ii] = Integer.parseInt(Xs[ii], 2);
            ys[ii] = Integer.parseInt(Ys[ii], 2);
        }//END FOR

        changeXs(xs);
        changeYs(ys);

        for(int ii=0;ii<tempArr.length;ii++)
        {
            Xs[ii] = Integer.toBinaryString(xs[ii]);
            Ys[ii] = Integer.toBinaryString(ys[ii]);
        }//END FOR

        for(int ii=0;ii<tempArr.length;ii++)
            tempArr[ii] = Xs[ii] + Ys[ii];

        for(int ii=0;ii<tempArr.length;ii++)
            byteArr[ii] = (byte)(Integer.parseInt(tempArr[ii], 2));

        String retString = "NULL";
        try
        {
            retString = new String(byteArr, "AUTF-8");    
        }//END TRY
        catch(UnsupportedEncodingException ueex)
        {
            //DO nothing
        }//END CATCH
        return retString;
    }//END encryptor

    public static void changeXs(int [] X)
    {
        for(int ii=0;ii<X.length;ii++)
        {
            int x = X[ii];
            if(x >= 0 && x <= 3)
                X[ii] = h(x);
            else if(x >= 4 && x <= 7)
                X[ii] = f(x);
            else if(x >= 8 && x <= 11)
                X[ii] = j(x);
            else if(x >= 12 && x <= 15)
                X[ii] = g(x);
        }//END FOR
    }//END changeXs

    public static void changeYs(int [] Y)
    {
        for(int ii=0;ii<Y.length;ii++)
        {
            int y = Y[ii];
            if(y >= 0 && y <= 3)
                Y[ii] = h(y);
            else if(y >= 4 && y <= 7)
                Y[ii] = h(y);
            else if(y >= 8 && y <= 11)
                Y[ii] = f(y);
            else if(y >= 12 && y <= 15)
                Y[ii] = k(y);
        }//END FOR
    }//END chnageYs

    public static int f(int i)
    {
        return i-4;
    }//END f

    public static int g(int i)
    {
        return i-8;
    }//END g

    public static int h(int i)
    {
        return i+8;
    }//END h

    public static int j(int i)
    {
        return i+4;
    }//END j

    public static int k(int i)
    {
        return i-12;
    }//END k
}//END class run