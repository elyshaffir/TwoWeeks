package gameUtil;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class WinnerGetter {
    private static final int MAX_X = 3500;
    private static Queue<String> winners = new LinkedList<>(); // Sorted by ID

    public static void checkWinners(CarPlayer localPlayer, String localId){
        if (!isWinner(localId))
            if (hasWon(localPlayer))
                addWinner(localId);
        for (String id:OtherCarPlayers.getOtherCars().keySet())
            if (!isWinner(id))
                if (hasWon(OtherCarPlayers.getOtherCars().get(id)))
                    addWinner(id);
    }

    private static boolean hasWon(CarPlayer carPlayer){
        return carPlayer.getPlayer().getPosition().x > MAX_X;
    }

    private static boolean isWinner(String id){
        for (String winnerID:winners)
            if (Objects.equals(id, winnerID))
                return true;
        return false;
    }

    private static void addWinner(String id){
        winners.add(id);
    }

    public static Queue<String> getWinners() {
        return winners;
    }
}
