package gameUtil;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class WinnerGetter {
    private static final int MAX_X = 3500;
    private static Queue<String> winners = new LinkedList<>();

    public static void checkWinners(CarPlayer localPlayer, String localId){
        if (!isWinner(localId))
            if (hasWon(localPlayer, false))
                addWinner(localId);
        for (String id:OtherCarPlayers.getOtherCars().keySet())
            if (!isWinner(id))
                if (hasWon(OtherCarPlayers.getOtherCars().get(id), true))
                    addWinner(id);
    }

    public static boolean allWon(String localId){
        for (String id:OtherCarPlayers.getOtherCars().keySet())
            if (!isWinner(id))
                return false;
        return isWinner(localId);
    }

    private static boolean hasWon(CarPlayer carPlayer, boolean other){
        if (other)
            return carPlayer.getPlayer().getPosition().x > MAX_X - 5;
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
        System.out.println(id);
    }

    public static Queue<String> getWinners() {
        return winners;
    }
}
