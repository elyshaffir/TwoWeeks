package gameUtil;

import gameCom.Client;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;

public class OtherCarPlayers {
    private static Client client;
    private static String lastDataToSend = "";
    private static HashMap<Integer, CarPlayer> otherCars = new HashMap<>();

    public static void sendCar(CarPlayer car, int id){
        String dataToSend = "";
        dataToSend += car.getChasiModelString() + "_";
        dataToSend += car.getChasiTextureString() + "_";

        dataToSend += car.getPlayer().getPosition().x + "_";
        dataToSend += car.getPlayer().getPosition().y + "_";
        dataToSend += car.getPlayer().getPosition().z + "_";

        dataToSend += car.getWheelsModelString() + "_";
        dataToSend += car.getWheelsTextureString() + "_";

        dataToSend += car.getFrontWheels().getPosition().x + "_";
        dataToSend += car.getFrontWheels().getPosition().y + "_";
        dataToSend += car.getFrontWheels().getPosition().z + "_";

        dataToSend += car.getBackWheels().getPosition().x + "_";
        dataToSend += car.getBackWheels().getPosition().y + "_";
        dataToSend += car.getBackWheels().getPosition().z + "_";

        dataToSend += car.getFrontWheels().getRotX() + "_";
        dataToSend += car.getFrontWheels().getRotY() + "_";
        dataToSend += car.getFrontWheels().getRotZ() + "_";

        dataToSend += car.getBackWheels().getRotX() + "_";
        dataToSend += car.getBackWheels().getRotY() + "_";
        dataToSend += car.getBackWheels().getRotZ() + "_";

        dataToSend += car.getPlayer().getRotX() + "_";
        dataToSend += car.getPlayer().getRotY() + "_";
        dataToSend += car.getPlayer().getRotZ() + "_";

        dataToSend += id;

        if (!Objects.equals(dataToSend, lastDataToSend)) {
            client.setDataToSend(dataToSend);
            lastDataToSend = dataToSend;
        }
        else
            client.setDataToSend("X" + id);
    }

    public static void loadAllOtherCars(Loader loader){
        try{
            if (client.getDataFromServer().charAt(0) == '{' && client.getDataFromServer().charAt(client.getDataFromServer().length() - 1) == '}'){
                String[] allCars = client.getDataFromServer().substring(1, client.getDataFromServer().length() - 1).split("###");
                for (String currentCar:allCars){
                    if (!Objects.equals(currentCar.charAt(0), 'X')){
                        String[] components = currentCar.split("_");
                        CarPlayer newCar = new CarPlayer(
                                loader,
                                components[0],
                                components[1],
                                new Vector3f(Float.parseFloat(components[2]), Float.parseFloat(components[3]), Float.parseFloat(components[4])),
                                components[5],
                                components[6],
                                new Vector3f(Float.parseFloat(components[7]), Float.parseFloat(components[8]), Float.parseFloat(components[9])),
                                new Vector3f(Float.parseFloat(components[10]), Float.parseFloat(components[11]), Float.parseFloat(components[12]))
                        );

                        newCar.getFrontWheels().setRotX(Float.parseFloat(components[13]));
                        newCar.getFrontWheels().setRotY(Float.parseFloat(components[14]));
                        newCar.getFrontWheels().setRotZ(Float.parseFloat(components[15]));

                        newCar.getBackWheels().setRotX(Float.parseFloat(components[16]));
                        newCar.getBackWheels().setRotY(Float.parseFloat(components[17]));
                        newCar.getBackWheels().setRotZ(Float.parseFloat(components[18]));

                        newCar.getPlayer().setRotX(Float.parseFloat(components[19]));
                        newCar.getPlayer().setRotY(Float.parseFloat(components[20]));
                        newCar.getPlayer().setRotZ(Float.parseFloat(components[21]));

                        int newCarID = Integer.parseInt(components[22]);
                        otherCars.put(newCarID, newCar);
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored){}
    }

    public static void renderAllOtherCars(MasterRenderer renderer){
        for (CarPlayer otherCar:otherCars.values()){
            renderer.processEntity(otherCar.getPlayer());
            renderer.processEntity(otherCar.getFrontWheels());
            renderer.processEntity(otherCar.getBackWheels());
        }
    }

    public static void setClient(Client client) {
        OtherCarPlayers.client = client;
    }

    public static Client getClient() {
        return client;
    }

    public static HashMap<Integer, CarPlayer> getOtherCars() {
        return otherCars;
    }
}