package com.crio.jukebox.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.crio.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand {

    private final ISongService songService;

    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(tokens.get(1)));

            String line = reader.readLine();
            while (line != null) {
                String[] songDetails = line.split(",");
                String[] artists = songDetails[5].split("#");
                songService.addSongDetails(songDetails[0], songDetails[1], songDetails[2], songDetails[3], String.join(",", artists));
                line = reader.readLine();
            }
            reader.close();
            System.out.println("Songs Loaded successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
