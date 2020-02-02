package rambox.smithandfletch.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.Identifier;
import rambox.smithandfletch.SmithAndFletch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

public class Config {
    public static final File configFile = new File("config/SmithAndFletch/SmithAndFletch.json/");
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private HashSet<Identifier> blacklist;

    public HashSet<Identifier> getBlacklist(){
        return blacklist;
    }

    Config(HashSet<Identifier> blacklist){
        this.blacklist = blacklist;
    }

    Config(){
        this(new HashSet<>());
    }

    public void addToBlacklist(Identifier id){
        this.blacklist.add(id);
        this.save();
    }

    public void save(){
        String json = gson.toJson(this.toConfigFile());
        try {
            FileOutputStream stream = new FileOutputStream(configFile);
            stream.write(json.getBytes(StandardCharsets.UTF_8));
            stream.close();
        } catch (IOException e) {
            SmithAndFletch.LOGGER.error("Error occurred while saving config file: " + e.getMessage());
        }
    }

    public ConfigFile toConfigFile(){
        return new ConfigFile(this.blacklist);
    }

    public static Config load(){
        if (configFile.exists()) {
            try {
                FileReader reader = new FileReader(configFile);
                return gson.fromJson(reader, ConfigFile.class).toConfig();
            } catch (IOException e) {
                SmithAndFletch.LOGGER.error("Error occurred while loading config file " + e.getMessage());
                return new Config();
            }
        } else {
            return create();
        }
    }

    public static Config create() {
        Config config = new Config();
        try {
            new File("config/SmithAndFletch/").mkdirs();
            FileOutputStream stream = new FileOutputStream(configFile);
            String json = gson.toJson(config.toConfigFile());
            stream.write(json.getBytes(StandardCharsets.UTF_8));
            stream.close();
        } catch (IOException e) {
            SmithAndFletch.LOGGER.error("Error occurred while creating config file " + e.getMessage());
        }
        return config;
    }

    private class ConfigFile {
        public HashSet<String> blacklist;

        ConfigFile(HashSet<Identifier> ids) {
            blacklist = new HashSet<>();

            ids.forEach(id -> {
                blacklist.add(id.toString());
            });
        }

        ConfigFile() {}

        private Config toConfig(){
            HashSet<Identifier> ids = new HashSet<>();
            this.blacklist.forEach(id -> {
                Identifier identifier = new Identifier(id);
                ids.add(identifier);
            });

            return new Config(ids);
        }
    }
}
