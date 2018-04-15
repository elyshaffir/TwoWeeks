package gameUtil;

import gameCom.Client;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;

import java.util.ArrayList;
import java.util.List;

public class OtherCarPlayers {
    private static Client client;
    private static List<CarPlayer> otherCars = new ArrayList<>();

    public static void sendCar(CarPlayer car){ // TODO: Sends the car (with the protocol specified int the next method.
        String dataToSend = "";
        dataToSend += car.getChasiModelString() + "--";
        dataToSend += car.getChasiTextureString() + "--";

        dataToSend += car.getPlayer().getPosition().x + "--";
        dataToSend += car.getPlayer().getPosition().y + "--";
        dataToSend += car.getPlayer().getPosition().z + "--";

        dataToSend += car.getWheelsModelString() + "--";
        dataToSend += car.getWheelsTextureString() + "--";

        dataToSend += car.getFrontWheels().getPosition().x + "--";
        dataToSend += car.getFrontWheels().getPosition().y + "--";
        dataToSend += car.getFrontWheels().getPosition().z + "--";

        dataToSend += car.getBackWheels().getPosition().x + "--";
        dataToSend += car.getBackWheels().getPosition().y + "--";
        dataToSend += car.getBackWheels().getPosition().z + "--";

        dataToSend += car.getFrontWheels().getRotX() + "--";
        dataToSend += car.getFrontWheels().getRotY() + "--";
        dataToSend += car.getFrontWheels().getRotZ() + "--";

        dataToSend += car.getBackWheels().getRotX() + "--";
        dataToSend += car.getBackWheels().getRotY() + "--";
        dataToSend += car.getBackWheels().getRotZ() + "--";

        dataToSend += car.getPlayer().getRotX() + "--";
        dataToSend += car.getPlayer().getRotY() + "--";
        dataToSend += car.getPlayer().getRotZ();

        client.setDataToSend(dataToSend);
    }

    public static void loadAllOtherCars(Loader loader){
        try{
            String componentsLoader = client.getDataFromServer().substring(1); // TODO: adapt to more than 1 car
            String[] components = componentsLoader.split("--");
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

            otherCars = new ArrayList<>(); // Just so it won't explode.
            otherCars.add(newCar);
        } catch (IndexOutOfBoundsException ignored){}
    }

    public static void renderAllOtherCars(MasterRenderer renderer){
        for (CarPlayer otherCar:otherCars){
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
}
