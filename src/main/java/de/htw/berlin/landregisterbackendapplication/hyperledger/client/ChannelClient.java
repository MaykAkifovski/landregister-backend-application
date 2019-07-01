package de.htw.berlin.landregisterbackendapplication.hyperledger.client;

import org.hyperledger.fabric.sdk.*;
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

    public Collection<ProposalResponse> queryChainCode(String chaincodeName, String functionName, String[] args) throws ProposalException, InvalidArgumentException {
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

    public Collection<ProposalResponse> invokeChainCode(String chaincodeName, String functionName, String[] args) throws InvalidArgumentException, ProposalException {
        LOGGER.info("Invoking {} on channel {}", functionName, channel.getName());
        TransactionProposalRequest request = fabricClient.getInstance().newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName(chaincodeName).build();
        request.setChaincodeID(ccid);
        request.setFcn(functionName);
        request.setArgs(args);
        request.setProposalWaitTime(1000);

        Collection<ProposalResponse> response = channel.sendTransactionProposal(request, channel.getPeers());
        channel.sendTransaction(response);
        return response;
    }
}
