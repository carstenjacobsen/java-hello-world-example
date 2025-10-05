package com.example;

import org.stellar.sdk.scval.Scv;
import org.stellar.sdk.responses.sorobanrpc.GetTransactionResponse;
import org.stellar.sdk.xdr.SCVal;
import org.stellar.sdk.xdr.TransactionMeta;
import org.stellar.sdk.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        
        SorobanServer server = new SorobanServer("https://soroban-testnet.stellar.org");
        try {
            GetTransactionResponse tx = server
                .getTransaction("0915d10a509e8c6282195d6b3fb71076deb9743f97fc04ba6513cd672fd7fa70");
            if (tx.getStatus() == GetTransactionResponse.GetTransactionStatus.SUCCESS) {
            List<String> output = new ArrayList<String>();
            String base64Xdr = tx.getResultMetaXdr();
            // convert the string to a result
            SCVal[] result = TransactionMeta.fromXdrBase64(base64Xdr).getV4()
                .getSorobanMeta().getReturnValue().getVec()
                .getSCVec();
            for (SCVal x : result) {
                output.add(x.getStr().getSCString().toString());
            }
            System.out.println("transaction result: " + output.toString());

            } else {
            System.out.println("Transaction failed: " + tx.getStatus());
            }
        } catch (Exception e) {
            System.err.println("An error has occurred:");
            e.printStackTrace();
        }

    }
}
