package de.htw.berlin.landregisterbackendapplication.hyperledger.client;

import de.htw.berlin.landregisterbackendapplication.hyperledger.user.UserContextDB;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import java.lang.reflect.InvocationTargetException;

public class FabricClient {

    private HFClient instance;

    public FabricClient() throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        this.instance = HFClient.createNewInstance();
        instance.setCryptoSuite(cryptoSuite);
        instance.setUserContext(UserContextDB.userContext);
    }

    public ChannelClient createChannelClient(String channelName) throws InvalidArgumentException {
        Channel channel = instance.newChannel(channelName);
        return new ChannelClient(channelName, channel, this);
    }

    public HFClient getInstance() {
        return instance;
    }
}