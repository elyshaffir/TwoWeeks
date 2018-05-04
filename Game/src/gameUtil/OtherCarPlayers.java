package gameUtil;

import gameCom.Client;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import java.util.HashMap;
import java.util.Objects;

public class OtherCarPlayers {
    private static Client client;
    private static String lastDataToSend = "+";
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

        String[] lastDataToSendComponents = lastDataToSend.split("_");
        String[] dataToSendComponents = dataToSend.split("_");

        try{
            for (int index = 0; index < 22; index++)
                if (!Objects.equals(lastDataToSendComponents[index], lastDataToSendComponents[index]))
                    dataToSendComponents[index] = "x";
        } catch (IndexOutOfBoundsException ignored){}

        dataToSend = String.join("_", dataToSendComponents);

        if (!Objects.equals(dataToSend, lastDataToSend)) {
            client.setDataToSend(dataToSend);
            lastDataToSend = dataToSend;
        }
        else
            client.setDataToSend("X" + id);
    }

    public static void loadAllOtherCars(Loader loader){
        try{
            String[] allCars = client.getDataFromServer().substring(1, client.getDataFromServer().length() - 1).split("###");
            for (String currentCar:allCars){
                if (!Objects.equals(currentCar.charAt(0), 'X')){
                    String[] components = currentCar.split("_");
                    int carID = Integer.parseInt(components[22]);
                    if (otherCars.keySet().contains(carID)){ // if car already exists
                        CarPlayer carToModify = otherCars.get(carID);

                        // to modify car chasi, add adaptation for components[0] and components[1].

                        if (!Objects.equals(components[2], "x")){
                            float newX = Float.parseFloat(components[2]);
                            Vector3f newPosition = new Vector3f(newX, carToModify.getPlayer().getPosition().y, carToModify.getPlayer().getPosition().z);
                            carToModify.getPlayer().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[3], "x")){
                            float newY = Float.parseFloat(components[3]);
                            Vector3f newPosition = new Vector3f(carToModify.getPlayer().getPosition().x, newY, carToModify.getPlayer().getPosition().z);
                            carToModify.getPlayer().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[4], "x")){
                            float newZ = Float.parseFloat(components[4]);
                            Vector3f newPosition = new Vector3f(carToModify.getPlayer().getPosition().x, carToModify.getPlayer().getPosition().y, newZ);
                            carToModify.getPlayer().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[7], "x")){
                            float newX = Float.parseFloat(components[7]);
                            Vector3f newPosition = new Vector3f(newX, carToModify.getFrontWheels().getPosition().y, carToModify.getFrontWheels().getPosition().z);
                            carToModify.getFrontWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[8], "x")){
                            float newY = Float.parseFloat(components[8]);
                            Vector3f newPosition = new Vector3f(carToModify.getFrontWheels().getPosition().x, newY, carToModify.getFrontWheels().getPosition().z);
                            carToModify.getFrontWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[9], "x")){
                            float newZ = Float.parseFloat(components[9]);
                            Vector3f newPosition = new Vector3f(carToModify.getFrontWheels().getPosition().x, carToModify.getFrontWheels().getPosition().y, newZ);
                            carToModify.getFrontWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[10], "x")){
                            float newX = Float.parseFloat(components[10]);
                            Vector3f newPosition = new Vector3f(newX, carToModify.getBackWheels().getPosition().y, carToModify.getBackWheels().getPosition().z);
                            carToModify.getBackWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[11], "x")){
                            float newY = Float.parseFloat(components[11]);
                            Vector3f newPosition = new Vector3f(carToModify.getBackWheels().getPosition().x, newY, carToModify.getBackWheels().getPosition().z);
                            carToModify.getBackWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[12], "x")){
                            float newZ = Float.parseFloat(components[12]);
                            Vector3f newPosition = new Vector3f(carToModify.getBackWheels().getPosition().x, carToModify.getBackWheels().getPosition().y, newZ);
                            carToModify.getBackWheels().setPosition(newPosition);
                        }

                        if (!Objects.equals(components[13], "x")){
                            carToModify.getFrontWheels().setRotX(Float.parseFloat(components[13]));
                        }

                        if (!Objects.equals(components[14], "x")){
                            carToModify.getFrontWheels().setRotY(Float.parseFloat(components[14]));
                        }

                        if (!Objects.equals(components[15], "x")){
                            carToModify.getFrontWheels().setRotZ(Float.parseFloat(components[15]));
                        }

                        if (!Objects.equals(components[16], "x")){
                            carToModify.getBackWheels().setRotX(Float.parseFloat(components[16]));
                        }

                        if (!Objects.equals(components[17], "x")){
                            carToModify.getBackWheels().setRotY(Float.parseFloat(components[17]));
                        }

                        if (!Objects.equals(components[18], "x")){
                            carToModify.getBackWheels().setRotZ(Float.parseFloat(components[18]));
                        }

                        if (!Objects.equals(components[19], "x")){
                            carToModify.getPlayer().setRotX(Float.parseFloat(components[19]));
                        }

                        if (!Objects.equals(components[20], "x")){
                            carToModify.getPlayer().setRotY(Float.parseFloat(components[20]));
                        }

                        if (!Objects.equals(components[21], "x")){
                            carToModify.getPlayer().setRotZ(Float.parseFloat(components[21]));
                        }
                    } else{
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

                        otherCars.put(carID, newCar);
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored){}
    }

    public static void loadAllOtherCarsOld(Loader loader){
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
            else if (client.getDataFromServer().length() > 1){
                // When the message is corrupted it creates fuckups in the communication later, needs cleaning.
                System.out.println(client.getDataFromServer());
                client.resetDataFromServer();
                System.out.println(client.getDataFromServer());
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