package by.patapovich.calculator.builder;

import by.patapovich.calculator.decorator.EncryptionDecorator;
import by.patapovich.calculator.decorator.JSONDecorator;
import by.patapovich.calculator.decorator.PlainDecorator;
import by.patapovich.calculator.decorator.XMLDecorator;
import by.patapovich.calculator.decorator.ZipDecorator;
import by.patapovich.calculator.persistence.DataSource;
import by.patapovich.calculator.persistence.FileDataSource;
import by.patapovich.calculator.persistence.FileType;
import by.patapovich.calculator.decorator.DataSourceDecorator;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileBuilder {

    private static FileBuilder instance;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern fileExtensionPattern = Pattern.compile("(?<=\\.)[^.]*$");

    private DataSourceDecorator fileDecorator;

    private FileBuilder() {}

    public static FileBuilder getInstance() {
        if (instance == null) {
            instance = new FileBuilder();
        }
        return instance;
    }

    public FileBuilder setDataSource(DataSource dataSource) {
        fileDecorator = new DataSourceDecorator(dataSource);
        return this;
    }

    public FileBuilder setFileName(String fileName) {
        fileDecorator = new DataSourceDecorator(new FileDataSource(fileName));
        return this;
    }

    public FileBuilder setFileType(FileType fileType) {
        switch (fileType) {
            case JSON -> fileDecorator = new JSONDecorator(fileDecorator);
            case XML -> fileDecorator = new XMLDecorator(fileDecorator);
            case PLAIN -> fileDecorator = new PlainDecorator(fileDecorator);
        }
        return this;
    }


    public FileBuilder setIsArchive() {
        fileDecorator = new ZipDecorator(fileDecorator);
        return this;
    }

    public FileBuilder setArchiveEntry(String entryName) {
        if (entryName != null) fileDecorator = new ZipDecorator(fileDecorator, entryName);
        return this;
    }

    public FileBuilder setEncrypt(boolean encrypt) {
        if (encrypt) fileDecorator = new EncryptionDecorator(fileDecorator);
        return this;
    }

    public DataSource build() {
        return fileDecorator;
    }

    private boolean archiveDialog() {
        while (true) {
            System.out.println("Does your file in archive(y/n)?");
            switch (scanner.nextLine()) {
                case "y" -> {
                    return true;
                }
                case "n" -> {
                    return false;
                }
            }
        }

    }


    private boolean fileEncryptionDialog() {
        while (true) {
            System.out.println("Does file is encrypted(y/n): ");
            switch (scanner.nextLine()) {
                case "y" -> {
                    return true;
                }
                case "n" -> {
                    return false;
                }
            }
        }
    }


    private boolean archiveEncryptionDialog() {
        while (true) {
            System.out.println("Does archive is encrypted(y/n): ");
            switch (scanner.nextLine()) {
                case "y" -> {
                    return true;
                }
                case "n" -> {
                    return false;
                }
            }
        }
    }

    public DataSource buildOutputFile() {
        FileType fileType = null;
        String fileName;
        while (true) {
            System.out.println("Enter file name: ");
            fileName = scanner.nextLine();
            Matcher matcher = fileExtensionPattern.matcher(fileName);
            if (matcher.find()) fileType = FileType.fromString(matcher.group(0));
            if (Objects.isNull(fileType)) System.out.println("Wrong file type");
            else break;
        }
        instance.setFileName(fileName);
        if (fileType == FileType.ZIP) {
            while (fileType == null || fileType == FileType.ZIP) {
                System.out.println("Enter file type in archive: ");
                fileType = FileType.fromString(scanner.nextLine());
                if (fileType == null || fileType == FileType.ZIP) System.out.println("Wrong file type");
            }
            instance.setEncrypt(archiveEncryptionDialog()).setArchiveEntry(fileExtensionPattern.matcher(fileName).replaceAll(fileType.getExtension()));
        } else if (archiveDialog()) {
            instance.setFileName(fileExtensionPattern.matcher(fileName).replaceAll(FileType.ZIP.getExtension()));
            instance.setEncrypt(archiveEncryptionDialog()).setArchiveEntry(fileName);
        }
        instance.setEncrypt(fileEncryptionDialog()).setFileType(fileType);

        return instance.build();
    }


    public DataSource buildInputFile() {
        FileType fileType = null;
        String fileName;
        while (true) {
            System.out.println("Enter file name: ");
            fileName = scanner.nextLine();
            Matcher matcher = fileExtensionPattern.matcher(fileName);
            if (matcher.find()) fileType = FileType.fromString(matcher.group(0));
            if (Objects.isNull(fileType)) System.out.println("Wrong file type");
            else break;
        }

        instance.setFileName(fileName);
        if (fileType == FileType.ZIP) {
            while (fileType == null || fileType == FileType.ZIP) {
                System.out.println("Enter file type in archive: ");
                fileType = FileType.fromString(scanner.nextLine());
                if (fileType == null || fileType == FileType.ZIP) System.out.println("Wrong file type");
            }
            instance.setEncrypt(archiveEncryptionDialog()).setIsArchive();
        }
        instance.setEncrypt(fileEncryptionDialog()).setFileType(fileType);

        return instance.build();
    }

}
