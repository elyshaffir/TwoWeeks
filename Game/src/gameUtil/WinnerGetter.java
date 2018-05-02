package gameUtil;

import java.util.LinkedList;
import java.util.Queue;

public class WinnerGetter {
    private static final int MAX_X = 400;
    private static Queue<Integer> winners = new LinkedList<>(); // Sorted by ID

    public static void checkWinners(CarPlayer localPlayer, int localId){
        if (!isWinner(localId))
            if (hasWon(localPlayer))
                addWinner(localId);
        for (int id:OtherCarPlayers.getOtherCars().keySet())
            if (!isWinner(id))
                if (hasWon(OtherCarPlayers.getOtherCars().get(id)))
                    addWinner(id);
    }

    private static boolean hasWon(CarPlayer carPlayer){
        return carPlayer.getPlayer().getPosition().x > MAX_X;
    }

    private static boolean isWinner(int id){
        for (int winnerID:winners)
            if (id == winnerID)
                return true;
        return false;
    }

    private static void addWinner(int id){
        winners.add(id);
        System.out.println("Added " + id);
    }

    public static Queue<Integer> getWinners() {
        return winners;
    }
}
