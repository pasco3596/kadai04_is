import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        try {
            File target = new File("./sample");
            File sample2 = new File("./sample2");
            sample2.mkdir();
            System.out.println("crypt start!!");


            if (target.isDirectory()) {
                fileCheck(target.listFiles());
            }
            fileRename(sample2.listFiles());

            System.out.println("crypt finish!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fileCheck(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                String str = file.getAbsolutePath();
                str = str.replace("sample", "sample2");
                File dir = new File(str);
                dir.mkdir();
                fileCheck(file.listFiles());
            } else {
                copy(file);
            }
        }
    }

    private static void copy(final File file) {
        try {
            String str = file.getAbsolutePath();
            str = str.replace("sample", "sample2");
            FileOutputStream fos = new FileOutputStream(str);
            byte[] bytes = Files.readAllBytes(file.toPath());
            bytes = crypt(bytes);
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] crypt(byte[] bytes) {
        for (int i = 0; i < bytes.length - 1; i++) {
            bytes[i] = (byte) (bytes[i] ^ 0xff);
        }
        return bytes;
    }

    private static void fileRename(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                fileRename(file.listFiles());
            } else {
                if (!file.getName().endsWith(".ISCRY")) {
                    file.renameTo(new File(file.getAbsolutePath() + ".ISCRY"));
                } else {
                    file.renameTo(new File(file.getAbsolutePath().replace(".ISCRY", "")));
                }
            }
        }
    }
}
