package com.example;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ModuloSolution {
    public static String debugCode;
    public static long probable = 1;
    public static ArrayList<Integer> straightPut = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<SingleModulo> modules = new ArrayList<>();
        GameMap gameMap = new GameMap();
        System.out.println("Modulo solution start");

        // read json string
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String rootString = "";
//        while (true) {
//            while (rootString.charAt(0) != '{' && !rootString.equals("quit")) {
        try {
            rootString = reader.readLine();
//            debugCode = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            }
        if (rootString.equals("quit")) {
            return;
        }

        // parse json string
        JSONObject root = JSONObject.parseObject(rootString);
        ModuloQuestion question = new ModuloQuestion();

        // set question
        question.setLevel(root.getInteger("level"));
        question.setModu(root.getString("modu"));

        ArrayList<String> arrayBuffer = new ArrayList<>();
        for (Object object : root.getJSONArray("map")) {
            arrayBuffer.add((String) object);
        }
        question.setMap(arrayBuffer);

        arrayBuffer.clear();
        for (Object object : root.getJSONArray("pieces")) {
            arrayBuffer.add((String) object);
        }
        question.setPieces(arrayBuffer);

        System.out.println("Level: " + question.getLevel());
        System.out.println("Modulo: " + question.getModu());

        // set map
        gameMap.setMap(question.getMap());

        // set modu
        gameMap.setModu(Integer.parseInt(question.getModu()));

        // set modulo
        for (String module : question.getPieces()) {
            SingleModulo singleModulo = new SingleModulo();
            singleModulo.setBlocks(module);
            if (singleModulo.getBlocks().length == gameMap.getMap().length &&
                singleModulo.getBlocks()[0].length == gameMap.getMap()[0].length) {
                gameMap.putModulo(0, 0, singleModulo);
                straightPut.add(question.getPieces().indexOf(module) * 2);
            } else {
                modules.add(singleModulo);
            }
        }
        gameMap.output();

        // solve problem
        GameMap currentMap = gameMap.cloneMap();

        for (SingleModulo modulo : modules) {
            modulo.output();
            System.out.println();
        }
        long time = System.currentTimeMillis();
        if (solve(gameMap, currentMap, modules)) {
//                continue;
        }
        time = System.currentTimeMillis() - time;
        printTime(time);
//        }
    }

    public static boolean solve(GameMap gameMap, GameMap currentMap, ArrayList<SingleModulo> modulo) {
        // init the result code
        ResultCode resultCode = new ResultCode(gameMap, modulo);
        do {
            if (resultCode.getCoverArea().equals(gameMap.getValueableArea())) {
                currentMap.setMap(gameMap.getMap());
                int[] code = resultCode.getResultCode();
//            printResult(code);
                int i = 0;
                for (SingleModulo singleModulo : modulo) {
//                System.out.println(code[i] + "," + code[i+1]);
//                singleModulo.output();
                    currentMap.putModulo(code[i++], code[i++], singleModulo);
                }
                if (checkResult(currentMap)) {
                    printResult(code);
                    printSolveSteps(gameMap, modulo, code);
                    return true;
                }
            }
        } while (!resultCode.inc());
        return false;
    }

    public static boolean checkResult(GameMap gameMap) {
        for (int i = 0; i < gameMap.getMap().length; i++) {
            for (int j = 0; j < gameMap.getMap()[i].length; j++) {
                if (gameMap.getMap()[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printResult(int[] result) {
        System.out.print("Result: ");
        int length = 0;
        for (int i : result) {
            System.out.print(i);
            if (straightPut.contains(length)) {
                System.out.print("00");
                length += 2;
            }
            length++;
        }
        if (straightPut.contains(length)) {
            System.out.print("00");
        }
        System.out.println();
    }

    public static void printSolveSteps(GameMap gameMap, ArrayList<SingleModulo> modulos, int[] result) {
        System.out.println("Solve steps: ");
        gameMap.output();
        for (int i = 0; i < modulos.size(); i++) {
            System.out.println("put at " + result[i * 2] + ',' + result[i * 2 + 1]);
            modulos.get(i).output();
            gameMap.putModulo(result[i * 2],
                    result[i * 2 + 1],
                    modulos.get(i));
            gameMap.output();
        }
    }

    public static void printTime(long time) {
        Date date = new Date(time);
        DateFormat formate = new SimpleDateFormat("HH:mm:ss:SSS", Locale.FRANCE);
        System.out.println("Compute time: " + formate.format(date));
    }

    public static void debug() {

    }
}
