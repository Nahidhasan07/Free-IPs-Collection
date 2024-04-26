
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nahid32081
 */
public class IP_collection_v1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            // TODO code application logic here
            ok_text(0);
            all_block();
            ok_text(1);
            System.exit(0);
        } catch (IOException ex) {
            ok_text(-1);
            System.exit(0);
            Logger.getLogger(IP_collection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void all_block() throws IOException {
        String IPblock;
//        String[] anik = {"10.100.81.", "10.100.82.", "10.100.83.", "10.100.84.", "10.100.85.", "10.100.75.", "10.100.86.",
//            "10.100.76.", "10.100.87.", "10.100.88.", "10.100.89.", "10.100.90.", "10.100.91."};
//        String[] sepal = {"10.100.51.", "10.100.52.", "10.100.53.", "10.100.54.", "10.100.55.", "10.100.56.", "10.100.57.",
//            "10.100.58."};
        String[] sepal = {"10.100.51.", "10.100.52."};
//        String[] branch = {"10.20.10.", "10.20.75.", "10.20.21.", "10.20.91.", "10.20.174."};
        loop1:
        for (int a = 0; a < 2; a++) {
            System.out.println("##############time = " + a + "\n\n");
            loop2:
            for (int a1 = 0; a1 < sepal.length; a1++) {
                IPblock = sepal[a1];
                operation(IPblock);
            }
        }
//        for (int a = 0; a < 2; a++) {
//            System.out.println("##############time = " + a + "\n\n");
//            loop2:
//            for (int a1 = 0; a1 < anik.length; a1++) {
//                IPblock = anik[a1];
//                operation(IPblock);
//            }
//        }
//        for (int a = 0; a < 2; a++) {
//            System.out.println("##############time = " + a + "\n\n");
//            loop2:
//            for (int a1 = 0; a1 < branch.length; a1++) {
//                IPblock = branch[a1];
//                operation(IPblock);
//            }
//        }
    }

    public static void operation(String IPblock) throws IOException {
        String blockString = IPblock, n = "";
        int flag;
        List<String> free_ipList = new LinkedList<>();
        // TODO code application logic here
        Process process;
        System.out.println("********First check**********");
        for (int a = 40; a < 249; a++) {
            n = blockString + a;
//            System.out.println(n);
            process = Runtime.getRuntime().exec("ping -a " + n);
            flag = CMDResults(process);
//            System.out.println("flag  " + flag);
            if (flag == 1) {
                System.out.println("free IP " + n);
                free_ipList.add(n);
            } else if (flag != 0 && flag != 1) {
                System.out.print("pblm\n");
            }
        }
        System.out.println("\n********Second check**********\n");
        flag = -1;
        for (int a = 0; a < free_ipList.size(); a++) {
            n = free_ipList.get(a);
            process = Runtime.getRuntime().exec("ping -a " + n);
            flag = CMDResults(process);
//            System.out.println("flag  " + flag);
            if (flag == 1) {
                System.out.println("free IP " + n);
//                free_ipList.add(n);
            } else {
                free_ipList.remove(n);
            }
        }
        System.out.println("\n********Final List**********\n");
        System.out.print(free_ipList + "\n");
//excel add
//        writeExcel(free_ipList, IPblock);
        file_ip(free_ipList, "IP_" + IPblock);

    }

    public static int CMDResults(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        int a = 0, f = 0;
        while ((line = reader.readLine()) != null) {
//            System.out.println("a = " + a + " and f=" + f + " " + line);
            if (a > 1 && f == 1 && line.contains("Request")) {
//                System.out.println("here\n");
                return 1;
            }
            if (a == 1) {
                String[] splited = line.split(" ");
                String x = splited[1];
//                System.out.print(x + "\n");
                int n = (int) x.charAt(0);
                if (n >= 48 && n <= 57) {
                    f = 1;
                    a++;
                    continue;
                } else {
                    return 0;
                }
            }
            a++;
        }
        return -1;
    }

    public static void file_ip(List<String> list, String block) throws FileNotFoundException, IOException {
//        String pathsString = System.getProperty("user.home");
        String pathsString = "";
//        System.out.println(pathsString);
        String Path = pathsString + "E:\\Nahid\\free _IP\\" + block + ".txt";
//        System.out.println(Path);
        FileOutputStream fileOut = new FileOutputStream(Path);
        FileWriter fWriter = new FileWriter(Path);
        for (int a = 0; a < list.size(); a++) {
            fWriter.write(list.get(a) + "\n");
        }
        fWriter.close();
        fileOut.close();
    }

    public static void ok_text(int num) throws FileNotFoundException, IOException {
//        String pathsString = System.getProperty("user.home");
        String pathsString = "";
//        System.out.println(pathsString);
        String Path = pathsString + "E:\\Nahid\\free _IP\\ok.txt";
//        System.out.println(Path);
        FileOutputStream fileOut = new FileOutputStream(Path);
        FileWriter fWriter = new FileWriter(Path);
        if (num == 1) {
            fWriter.write("ok\n");
        } else if (num == 0) {
            fWriter.write("START\n");
        } else {
            fWriter.write("NOT OK\n");
        }
        fWriter.close();
        fileOut.close();
    }

//    public static void writeExcel(List<String> list, String block) throws FileNotFoundException, IOException {
//        spreadsheet = workbook.createSheet("Sheet_" + block);
//        XSSFRow row;
//        for (int a = 0; a < list.size(); a++) {
//            row = spreadsheet.createRow(a + 1);
//            int cellid = 0;
//            XSSFCell cell = row.createCell(cellid++);
//            cell.setCellValue(list.get(a));
//        }
////        OutputStream fileOut = new FileOutputStream("E:\\Nahid\\free_IP\\free_IP_block.xlsx");
//        OutputStream fileOut = new FileOutputStream("C:\\Users\\nahid32081\\Desktop\\free_IP_block.xlsx");
//        workbook.write(fileOut);
//        fileOut.close();
//    }   
}
