package simpleshop.bucket;

import simpleshop.device.Device;

import java.util.ArrayList;

public class ItemBucket {
    private static ItemBucket instance;
    private ArrayList<Device> devices;

    private ItemBucket() {
        this.devices = new ArrayList<>();
    }

    public void addItemToBucket(Device device){
        devices.add(device);
    }

    public void removeItemToBucket(Device device){
        devices.remove(device);
    }

    public ArrayList<Device> getUsersBucket(){
        return this.devices;
    }

    public static ItemBucket getInstance() {
        if (instance == null) {
            instance = new ItemBucket();
        }
        return instance;
    }

}
