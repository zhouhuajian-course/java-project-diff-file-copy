import java.io.*;
import java.util.*;

public class DiffFileCopy {

    public static void main(String[] args) throws IOException {

        // 项目目录和Diff文件要拷贝到的目录
        String projectDir = "E:\\java-project\\project-diff-file-copy\\some_project";
        String projectDiffDir = "E:\\java-project\\project-diff-file-copy\\some_project_diff";

        // 读取并解析diff内容
        // BufferedReader.readLine()方法返回的字符串，不包括末尾换行符
        BufferedReader bufferedReader = new BufferedReader(new FileReader("diff.txt"));
        String line;

        // 源文件路径、目标文件路径、要创建的目录路径
        List<String> srcFilePaths = new ArrayList<>();
        List<String> destFilePaths = new ArrayList<>();
        Set<String> neededDirs = new HashSet<>();

        while ((line = bufferedReader.readLine()) != null) {
            if (!line.startsWith("\tmodified")) {
                continue;
            }
            String fileRelativePath = line.substring("\tmodified:   ".length()).replace("/", "\\");
            srcFilePaths.add(projectDir + "\\" + fileRelativePath);
            String destFilePath = projectDiffDir + "\\" + fileRelativePath;
            destFilePaths.add(destFilePath);
            neededDirs.add(destFilePath.substring(0, destFilePath.lastIndexOf('\\')));
        }

        System.out.println(srcFilePaths);
        System.out.println(destFilePaths);
        System.out.println(neededDirs);

        // 先创建目录
        for (String neededDir : neededDirs) {
            String cmd = "cmd.exe /c mkdir " + neededDir;
            System.out.println(cmd);
            Runtime.getRuntime().exec(cmd);
        }

        // 拷贝文件
        for (int i = 0; i < srcFilePaths.size(); i++) {
            String cmd = "cmd.exe /c copy " + srcFilePaths.get(i) + " " + destFilePaths.get(i);
            System.out.println(cmd);
            Runtime.getRuntime().exec(cmd);
        }

    }

}
