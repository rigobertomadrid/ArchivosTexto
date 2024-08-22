package archivostexto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MiFile {

    private File mf = null;
    private static Scanner lea = new Scanner(System.in);

    public void setFile(String dir) {
        mf = new File(dir);
    }

    public void Info() {
        if (mf != null && mf.exists()) {
            System.out.println("\nSI EXISTE:\n-----------");
            System.out.println("Nombre: " + mf.getName());
            System.out.println("Path: " + mf.getPath());
            System.out.println("Absoluta: " + mf.getAbsolutePath());
            System.out.println("Padre: " + mf.getAbsoluteFile().getParentFile().getName());
            System.out.println("Bytes: " + mf.length());
            if (mf.isFile()) {
                System.out.println("ES UN ARCHIVO.");
            } else if (mf.isDirectory()) {
                System.out.println("ES UN FOLDER");
            }
            System.out.println("Ultima modificacion: " + mf.lastModified());
        } else {
            System.out.println("No se ha seleccionado un archivo o no existe.");
        }
    }

    public void crearFile() throws IOException {
        if (mf != null) {
            if (mf.createNewFile()) {
                System.out.println("Archivo creado exitosamente.");
            } else {
                System.out.println("No se pudo crear el archivo.");
            }
        } else {
            System.out.println("Por favor, seleccione un archivo primero.");
        }
    }

    public void crearFolder() {
        if (mf != null) {
            if (mf.mkdir()) {
                System.out.println("Carpeta creada exitosamente.");
            } else {
                System.out.println("No se pudo crear la carpeta.");
            }
        } else {
            System.out.println("Por favor, seleccione un archivo primero.");
        }
    }

    private boolean antidoto(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                antidoto(child);
            }
        }
        return file.delete();
    }

    public void borrar() {
        if (mf != null && antidoto(mf)) {
            System.out.println("El archivo/carpeta ha sido borrado.");
        } else {
            System.out.println("No se pudo borrar el archivo o no se ha seleccionado.");
        }
    }

    public void dir() {
        if (mf != null && mf.isDirectory()) {
            System.out.println("Directorio de: " + mf.getAbsolutePath());
            System.out.println("");
            int cfiles = 0, cdirs = 0, tbytes = 0;

            for (File child : mf.listFiles()) {
                if (!child.isHidden()) {
                    System.out.print(child.lastModified() + "\t");
                    if (child.isDirectory()) {
                        cdirs++;
                        System.out.print("<DIR>\t\t");
                    } else {
                        cfiles++;
                        tbytes += child.length();
                        System.out.print("      \t" + child.length() + "\t");
                    }
                    System.out.println(child.getName());
                }
            }
            System.out.println(cfiles + " archivos\t" + tbytes + " bytes" + "\n" + cdirs + " dirs");
        } else {
            System.out.println("No se ha seleccionado una carpeta válida.");
        }
    }

    public void tree() {
        if (mf != null) {
            tree(mf, "-");
        } else {
            System.out.println("No se ha seleccionado un archivo o carpeta.");
        }
    }

    private void tree(File dir, String tab) {
        if (dir.isDirectory()) {
            System.out.println(tab + dir.getName());
            for (File child : dir.listFiles()) {
                if (!child.isHidden()) {
                    tree(child, tab + "--");
                }
            }
        }
    }

    public void sobreEscribir() throws IOException {
        if (mf != null && mf.exists() && mf.isFile()) {
            System.out.println("Ingrese el nuevo contenido (ojo se sobrescribira todo el contenido anterior): ");
            lea.useDelimiter("\\n");
            String contenido = lea.next();
            try (FileWriter writer = new FileWriter(mf)) {
                writer.write(contenido + "\n");
                System.out.println("Contenido sobrescrito exitosamente.");
            }
        } else {
            System.out.println("El archivo no existe o no es valido.");
        }
    }

    public void escribir() throws IOException {
        if (mf != null && mf.exists() && mf.isFile()) {
            System.out.println("Ingrese el contenido a agregar:");
            lea.useDelimiter("\\n");
            String contenido = lea.next();
            try (FileWriter writer = new FileWriter(mf, true)) {
                writer.write(contenido + "\n");
                System.out.println("Contenido agregado exitosamente.");
            }
        } else {
            System.out.println("El archivo no existe o no es valido.");
        }
    }

    public void leerArchivo() throws IOException {
        if (mf != null && mf.exists() && mf.isFile()) {
            System.out.println("Contenido del archivo:\n");
            String contenido = new String(Files.readAllBytes(Paths.get(mf.getAbsolutePath())));
            System.out.println(contenido);
        } else {
            System.out.println("El archivo no existe o no es valido.");
        }
    }
}
