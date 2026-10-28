package com.hackathon.cocktailmod.client;

/**
 * Stores the client's current alcohol level (0-100 grams) received from the server.
 */
public class ClientAlcoholData {
    private static int alcoholLevel = 0;

    public static int getLevel() {
        return alcoholLevel;
    }

    public static void setLevel(int level) {
        alcoholLevel = Math.max(0, Math.min(100, level));
    }
}
