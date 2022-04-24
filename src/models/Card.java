package models;

public enum Card {
    Wind,
    Wave,
    Fire,
    Stone,
    Flood;

    public int getTempleID(Card card) {
        switch (card) {
            case Wind:
                return 0;
            case Stone:
                return 1;
            case Fire:
                return 2;
            case Wave:
                return 3;
            default:
                return -1;
        }
    }

    public static Card getCardTemple(int id) {
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
