package IpCounter;

import java.io.BufferedReader;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.Scanner;

public class IpCounter {

    private static byte[][][][] addrArr;

    public static void main(String[] args) {
        System.out.println("Enter filename:");
        System.out.print("> ");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();

        try (BufferedReader freader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String addrLine;
            addrArr = new byte[256][][][];
            int k = 0;
            while ((addrLine = freader.readLine()) != null) {
                if (!addrLine.isEmpty())
                    k = k + findAddr(addrLine);
            }
            System.out.println("Unique: " + k);
        } catch (NumberFormatException e) {
            System.out.println("Address format error");
        } catch (IOException e) {
            System.out.println("Reading file error");
        }
    }

    private static int findAddr(String addrStr) throws NumberFormatException {
        int pos1, pos2, pos3, pos4;
        int k = 0;
        String[] strArr = addrStr.split("\\.");
        pos1 = Integer.parseInt(strArr[0]);
        pos2 = Integer.parseInt(strArr[1]);
        pos3 = Integer.parseInt(strArr[2]);
        pos4 = Integer.parseInt(strArr[3]);
        System.out.println(addrStr + "\t" + pos1 + " " + pos2 + " " + pos3 + " " + pos4);
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
        return k;
    }
}
