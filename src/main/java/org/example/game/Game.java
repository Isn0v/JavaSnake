package org.example.game;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game {

    private Random random = new Random();
    private final int SNAKES_SPAWN_COLLISION = 4;
    private final double FRAME_TIME = 0.1;

    private ArrayList<Snake> snakes;

    //Not more than 4
    private int snakesCount;
    private GameRules gameRules = new GameRules();
    private Cell[][] map;


    Game(ArrayList<Snake> snakes, String mapPath){
        int mapSize = gameRules.mapSize;
        this.map = new Cell[mapSize][mapSize];
        try {
            this.parseMap(mapPath);
        } catch (Exception e){
            e.printStackTrace();
        }

        this.snakesCount = snakes.size();
    }

    public void parseMap(String mapPath) throws FileNotFoundException {
        File file = new File(mapPath);

        Scanner scanner = new Scanner(file);
        int row = 0;

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            for(int column = 0; column < line.length(); column++){
                switch (line.charAt(column)) {
                    case '#' -> this.map[row][column] = Cell.Wall;
                    case '\u00b7' -> this.map[row][column] = Cell.Empty;
                    case '0', '1' -> this.map[row][column] = Cell.Snake;
                    case 'A' -> this.map[row][column] = Cell.Food;
                    default -> throw new RuntimeException("Invalid symbol in map (text representation)");
                }
            }
            row++;
        }

    }

    public Cell getCell(Point point){
        return map[point.x][point.y];
    }

    public boolean isEmpty(Point point){
        return map[point.x][point.y] == Cell.Empty;
    }

    public void fillCell(Point point, Cell cell){
        map[point.x][point.y] = cell;
    }

    public  void deleteSnake(Snake snake){
        for(Point point: snake.body){
            this.fillCell(point, Cell.Empty);
        }
    }

    public void generateFood(int foodCount){
        ArrayList<Point> emptyCells = new ArrayList<Point>();
        for (int i = 0; i < gameRules.mapSize; i++){
            for (int j = 0; j < gameRules.mapSize; j++) {
                if (this.map[i][j] == Cell.Empty){
                    emptyCells.add(new Point(i, j));
                }
            }
        }
        for (int i = 0; i < foodCount; i++){
            int randIndex = random.nextInt(emptyCells.size());
            Point point = emptyCells.get(randIndex);
            this.fillCell(point, Cell.Food);
            emptyCells.remove(randIndex);
        }
    }

    public void spawnSnakes(){
        ArrayList<Point> spawnPositions = new ArrayList<Point>();

        for(int i = 0; i < gameRules.mapSize; i++){
            int count = 0;
            for (int j = 0; j < gameRules.mapSize; j++){
                if (this.isEmpty(new Point(i, j))){
                    count++;
                } else {
                    count = 0;
                }
                if (count == gameRules.snakeSpawnSize + SNAKES_SPAWN_COLLISION){
                    spawnPositions.add(new Point(i, j));
                }
                //надо доделать
            }
        }
    }

    public Point getNextPoint(Snake snake){
        Point head = snake.body.get(snake.body.size() - 1);
        Point retPoint = head;

        switch (snake.direction){
            case Up -> retPoint = new Point(head.x, (head.y - 1) % gameRules.mapSize);
            case Right -> retPoint = new Point((head.x + 1) % gameRules.mapSize, head.y);
            case Down -> retPoint = new Point(head.x, (head.y + 1) % gameRules.mapSize);
            case Left -> retPoint = new Point((head.x - 1) % gameRules.mapSize, head.y);
        }
        return retPoint;
    }

    public void moveSnake(Snake snake){
        Point nextPoint = getNextPoint(snake);
        switch (this.getCell(nextPoint)){
            case Empty:{
                snake.body.add(nextPoint);
                this.fillCell(nextPoint, Cell.Snake);
                this.fillCell(snake.body.get(0), Cell.Empty);
                snake.body.remove(0);
                break;
            }
            case Food:{
                snake.body.add(nextPoint);
                this.fillCell(nextPoint, Cell.Snake);
                this.generateFood(1);
            }
            default:{
                this.deleteSnake(snake);
                snake.kill();
            }
        }
    }
}
