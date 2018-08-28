import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {

        try {
            List<String> files =
                    Files.list(Paths.get("D:\\Projects\\GeekBrains\\geek-cloud\\Cloud\\d9ae7e0b-0d0f-401a-83ca-dd2b0e80da6f"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            for (int i = 0; i < files.size(); i++) {
                System.out.println(files.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
