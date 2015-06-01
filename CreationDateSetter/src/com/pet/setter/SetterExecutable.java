package com.pet.setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Date;

public class SetterExecutable
{
    public void changeCretionByAlphOrder(File path) throws IOException
    {
        if (path != null)
        {
            File[] listFiles = path.listFiles();
            Arrays.sort(listFiles);
            long timestamp = System.currentTimeMillis() - 1000 * 1000;
            for (File file : listFiles)
            {
                setCretionTime(timestamp += 1000, file);
                System.out.println("Changing file: " + file.getAbsolutePath() + " to " + new Date(timestamp));
                if (file.isDirectory())
                {
                    changeCretionByAlphOrder(file);
                }
            }
        }
    }

    private void setCretionTime(long timestamp, File file) throws IOException
    {
        Path path = Paths.get(file.toURI());
        FileTime ft = FileTime.fromMillis(timestamp);
        Files.setAttribute(path, "basic:creationTime", ft);
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: SetterExecutable path_to_root_dir");
            return;
        }
        SetterExecutable main = new SetterExecutable();
        try
        {
            //main.changeCretionByAlphOrder(new File("/Users/petar/Dev/test"));
            main.changeCretionByAlphOrder(new File(args[0]));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
