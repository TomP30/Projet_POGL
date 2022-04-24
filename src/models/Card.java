package models;

public enum Card {
    Wind,
    Wave,
    Fire,
    Stone,
    Flood;


    public static Card getTreasureCard(int id) {
        switch (id) {
            case 0:
                return Wind;
            case 1:
                return Stone;
            case 2:
                return Fire;
            case 3:
                return Wave;
            default:
                return null;
        }
    }
}
