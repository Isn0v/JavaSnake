package org.example.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameRules {
    public int mapSize;
    public int wallSize;
    public int allFoodCount;
    public int snakeSpawnSize;

    public int squareWallCountMax;
    public int straightWallCountMax;
    public int singleWallCountMax;

    GameRules(){
        Properties properties = new Properties();

        try (
                FileInputStream fileInputStream = new FileInputStream("gameRules.properties")) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.mapSize = Integer.parseInt(properties.getProperty("map_size"));
        this.wallSize = Integer.parseInt(properties.getProperty("wall_size"));
        this.allFoodCount = Integer.parseInt(properties.getProperty("all_food_count"));
        this.snakeSpawnSize = Integer.parseInt(properties.getProperty("snake_spawn_size"));

        this.squareWallCountMax = Integer.parseInt(properties.getProperty("square_wall_count_max"));
        this.straightWallCountMax = Integer.parseInt(properties.getProperty("straight_wall_count_max"));
        this.singleWallCountMax = Integer.parseInt(properties.getProperty("single_wall_count_max"));
    }
}
