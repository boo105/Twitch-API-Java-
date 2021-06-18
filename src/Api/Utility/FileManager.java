package Api.Utility;

import Api.Twitch.Streamer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static FileManager instance;

    String filePath;
    FileWriter fw;
    BufferedWriter bw;

    private FileManager(String filePath)
    {
        this.filePath = filePath;
    }

    public static FileManager getInstance(String fileName)
    {
        if(instance == null)
            instance = new FileManager(fileName + ".txt");
        return instance;
    }

    public void setFileWriter()
    {
        try
        {
            fw = new FileWriter(filePath,true);
            bw = new BufferedWriter( fw );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void WriteStreamer(Streamer streamer)
    {
        String streamerInfo = streamer.to_string_forFile();
        try
        {
            System.out.println(streamerInfo);
            bw.write(streamerInfo);
            bw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isInfoExist()
    {
        File file = new File(filePath);
        if(file.isFile())
            return true;
        else
            return false;
    }

    public List<Streamer> getStreamers()
    {
        List<Streamer> streamers = new ArrayList<>();
        try
        {
            FileReader rw = new FileReader(filePath);
            BufferedReader br = new BufferedReader(rw);

            String readLine = null;
            while((readLine = br.readLine()) != null)
            {
                String[] info = readLine.split(",");
                Streamer streamer = new Streamer(info[0],info[1],info[2]);
                streamers.add(streamer);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return streamers;
    }
}
