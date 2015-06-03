package com.jaspersoft.jasperserver.jrsh.core.common;

import com.jaspersoft.jasperserver.jrsh.core.common.exception.CouldNotZipFileException;
import com.jaspersoft.jasperserver.jrsh.core.common.exception.DirectoryDoesNotExistException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.io.File.separator;
import static java.io.File.separatorChar;
import static org.apache.commons.lang3.StringUtils.chomp;

/**
 * @author Alex Krasnyanskiy
 */
public class ZipUtil {

    public static File pack(String directory) {
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new DirectoryDoesNotExistException(directory);
        }
        directory = chomp(directory, separator);
        String outputFileName = directory.concat(".zip");
        try {
            File arch = new File(outputFileName);
            FileOutputStream fos = null;
            fos = new FileOutputStream(arch);
            ZipOutputStream zos = new ZipOutputStream(fos);
            addFiles(zos, directory, directory);
            zos.close();
            return arch;
        } catch (Exception unimportant) {
            throw new CouldNotZipFileException();
        }
    }

    private static void addFiles(ZipOutputStream zos, String folder, String baseFolder) throws Exception {
        File file = new File(folder);
        if (file.exists()) {
            if (file.isDirectory()) {
                if (!folder.equalsIgnoreCase(baseFolder)) {
                    String entryName = folder.substring(baseFolder.length() + 1, folder.length())
                            + separatorChar;
                    ZipEntry zipEntry = new ZipEntry(entryName);
                    zos.putNextEntry(zipEntry);
                }
                File files[] = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        addFiles(zos, f.getAbsolutePath(), baseFolder);
                    }
                }
            } else {
                String entryName = folder.substring(baseFolder.length() + 1, folder.length());
                ZipEntry zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);
                try (FileInputStream in = new FileInputStream(folder)) {
                    int len;
                    byte buf[] = new byte[1024];
                    while ((len = in.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }
                    zos.closeEntry();
                }
            }
        }
    }
}


