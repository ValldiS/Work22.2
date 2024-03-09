import java.io.*;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static HashSet<String> list = new HashSet<>();

    public static void main(String[] args) {
        GameProgress save1 = new GameProgress(100, 2, 1, 145.8);
        GameProgress save2 = new GameProgress(70, 6, 1, 109.1);
        GameProgress save3 = new GameProgress(25, 9, 18, 15.9);
        saveGame(save1, "C://Games/saveGames/save1.dat");
        saveGame(save2, "C://Games/saveGames/save2.dat");
        saveGame(save3, "C://Games/saveGames/save3.dat");

        zipFiles("C://Games/saveGames/zip.zip", list);

        deleteFile("C://Games/saveGames", "zip");

    }

    public static void saveGame(GameProgress gameProgress, String way) {
        try (FileOutputStream flow = new FileOutputStream(way);
             ObjectOutputStream save = new ObjectOutputStream(flow)) {
            save.writeObject(gameProgress);
            list.add(way);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipWay, HashSet<String> list) {
        ZipOutputStream zip = null;
        try {
            zip = new ZipOutputStream(new FileOutputStream(zipWay));
            try {
                for (String way : list) {
                    FileInputStream save = new FileInputStream(way);
                    ZipEntry file = new ZipEntry(way);
                    zip.putNextEntry(file);
                    byte[] buffer = new byte[save.available()];
                    save.read(buffer);
                    zip.write(buffer);
                    zip.closeEntry();
                    save.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                if (zip != null) {
                    zip.close();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile(String way, String exceptions) {
        File file = new File(way);
        if (file.isDirectory()) {
            for (File del : file.listFiles()) {
                if (del.isFile() && !del.getName().contains(exceptions)) {
                    if (del.delete()) {
                        System.out.println("Success");
                    }
                }
            }
        } else {
            System.out.println(file.getName() + " " + "Not a catalog");
        }
    }
}