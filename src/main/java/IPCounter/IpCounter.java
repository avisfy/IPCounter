package IPCounter;

import java.io.*;
import java.util.Scanner;

public class IpCounter {

    private static byte[][][][] addrArr;


    public static void main(String[] args) {
        System.out.println("Enter filename:");
        //System.out.print("> ");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        long k = 0;
        try (BufferedReader freader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))))) {
            addrArr = new byte[256][][][];
            String addrLine;
            while ((addrLine = freader.readLine()) != null) {
                if (!addrLine.isEmpty()) {
                    k = k + findAddr(addrLine);
                    //if (k % 1000000 == 0) {
                        //System.out.println("working: " + addrLine + " " + k);
                    //}
                }
            }
            System.out.println("Unique: " + k);
        } catch (NumberFormatException e) {
            System.out.println("Address format error");
        } catch (IOException e) {
            System.out.println("Reading file error");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error:" +  k);
        }
    }


    private static int findAddr(String addrStr) throws Exception {
        int pos1, pos2, pos3, pos4;
        int k = 0;
        int posPoint1= 0;
        int posPoint2= -1;
        String[] strArr = new String[4];
        for (int i = 0; i<4; i++) {
            posPoint1 = posPoint2 + 1;
            posPoint2 = addrStr.indexOf('.', posPoint1);
            if (posPoint2 != -1)
                strArr[i] = new String(addrStr.substring(posPoint1, posPoint2));
            else
                strArr[i] = new String(addrStr.substring(posPoint1));
        }
        pos1 = Integer.parseInt(strArr[0]);
        pos2 = Integer.parseInt(strArr[1]);
        pos3 = Integer.parseInt(strArr[2]);
        pos4 = Integer.parseInt(strArr[3]);
        if (addrArr[pos1] == null) {
            addrArr[pos1] = new byte[256][][];
            addrArr[pos1][pos2] = new byte[256][];
            addrArr[pos1][pos2][pos3] = new byte[256];
            addrArr[pos1][pos2][pos3][pos4] = 1;
            k++;
        } else if (addrArr[pos1][pos2] == null) {
            addrArr[pos1][pos2] = new byte[256][];
            addrArr[pos1][pos2][pos3] = new byte[256];
            addrArr[pos1][pos2][pos3][pos4] = 1;
            k++;
        } else if (addrArr[pos1][pos2][pos3] == null) {
            addrArr[pos1][pos2][pos3] = new byte[256];
            addrArr[pos1][pos2][pos3][pos4] = 1;
            k++;
        } else if (addrArr[pos1][pos2][pos3][pos4] <= 1) {
            addrArr[pos1][pos2][pos3][pos4]++;
            if (addrArr[pos1][pos2][pos3][pos4] == 1) {
                k++;
            } else if (addrArr[pos1][pos2][pos3][pos4] == 2) {
                k--;
            }
        }
        addrStr = null;
        return k;
    }
}
