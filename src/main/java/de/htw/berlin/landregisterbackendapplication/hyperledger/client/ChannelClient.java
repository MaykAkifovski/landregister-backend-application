package de.htw.berlin.landregisterbackendapplication.hyperledger.client;

import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ChannelClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelClient.class);

    private String channelName;
    private Channel channel;
    private FabricClient fabricClient;

    public ChannelClient(String channelName, Channel channel, FabricClient fabricClient) {
        this.channelName = channelName;
        this.channel = channel;
        this.fabricClient = fabricClient;
    }

    public Channel getChannel() {
        return channel;
    }

    public Collection<ProposalResponse> queryByChainCode(String chaincodeName, String functionName, String[] args) throws ProposalException, InvalidArgumentException {
        LOGGER.info("Querying {} on channel {}", functionName, channel.getName());
        QueryByChaincodeRequest request = fabricClient.getInstance().newQueryProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName(chaincodeName).build();
        request.setChaincodeID(ccid);
        request.setFcn(functionName);
        if (args != null) {
            request.setArgs(args);
        }
        return channel.queryByChaincode(request);
    }
}
