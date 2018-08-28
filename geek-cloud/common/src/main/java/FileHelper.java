import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {
    private static FileHelper ourInstance = new FileHelper();


    public static FileHelper getInstance() {
        return ourInstance;
    }

    private FileHelper() {
    }

    public List<String> getListFile(String currPath) {
        List<String> files = new ArrayList<>();
        try {
            files.addAll(
                    Files.list(Paths.get(currPath))
                            .map(path -> path.toFile())
                            .filter(file -> file.isFile() && !file.isHidden())
                            .map(file -> file.getName())
                            .sorted()
                            .collect(Collectors.toList()));
            files.addAll(
                    Files.list(Paths.get(currPath))
                            .map(path -> path.toFile())
                            .filter(file -> file.isDirectory() && !file.isHidden())
                            .map(file -> file.getName())
                            .sorted()
                            .collect(Collectors.toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    public List<Path> getListRoot() {
        File[] files = File.listRoots();
        return Arrays.stream(File.listRoots())
                .map(file -> file.toPath())
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Path> getListLocalFile(String currPath) {
        List<Path> files = new ArrayList<>();
        try {
            files.addAll(
                    Files.list(Paths.get(currPath))
                            .filter(path -> !Files.isDirectory(path))
                            .sorted()
                            .collect(Collectors.toList()));
            files.addAll(
                    Files.list(Paths.get(currPath))
                            .filter(path -> Files.isDirectory(path))
                            .sorted()
                            .collect(Collectors.toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    public void deleteFiles(Path path)
    {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renameFile(String oldName, String newName) {
        try {
            Path oldFile = Paths.get(oldName);
            Path newFile = Paths.get(newName);
            if (Files.exists(oldFile) && !Files.exists(newFile)) {
                Files.move(oldFile, newFile, StandardCopyOption.ATOMIC_MOVE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placeFile(String name, byte[] file) {
        try {
            Path path = Paths.get(name);
            Files.write(path, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDirectory(String fullName){
        return Files.isDirectory(Paths.get(fullName));
    }

    public byte[] fileToByteArray(Path path) {
        try {
            if (Files.exists(path) && !Files.isDirectory(path)) {
                return Files.readAllBytes(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

