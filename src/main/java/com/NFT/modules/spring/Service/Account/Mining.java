package com.NFT.modules.spring.Service.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthMining;
import org.web3j.protocol.core.methods.response.MinerStartResponse;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.ipc.UnixIpcService;

import java.io.IOException;

@Service
public class Mining {
//    @Autowired
//    private Geth geth;

	Geth geth = Geth.build(new UnixIpcService("/Users/mac/eth_node/data/geth.ipc"));

	@Autowired
	private Web3j web3j;

	public boolean isMining() throws IOException {
		EthMining b = web3j.ethMining().send();
		return b.isMining();
	}

    public String minerStart() throws Exception {
		MinerStartResponse result = geth.minerStart(1).send();
		if(result.hasError()) {
			return result.getError().getMessage();
		}
		return result.toString();
	}


	public String minerStop() throws IOException {
		Response<Boolean> result = geth.minerStop().send();
		if(result.hasError()) {
			return result.getError().getMessage();
		}
		return result.toString();
	}

}

