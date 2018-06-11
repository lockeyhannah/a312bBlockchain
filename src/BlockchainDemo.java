import blockchain.BlockchainClient;
import blockchain.block.data_points.InsufficientFundsException;
import blockchain.block.data_points.StorageContract;
import blockchain.block.data_points.TokenTransaction;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class BlockchainDemo {
    private BlockchainClient chainClient;
    private static final String ledgerPath = "ledger.dat";
    private static final String minerID = "Miner";
    private Scanner scanner;

    public static void main(String[] args) {
        BlockchainDemo demo = new BlockchainDemo();
        while (demo.promptUser()){
            if(!demo.continuePrompt()) break;

        }
    }

    public BlockchainDemo() {
        chainClient = new BlockchainClient(Paths.get(ledgerPath));
        scanner = new Scanner(System.in);
    }

    // Prompts user to choose next action and returns true if the program should continue;
    public boolean promptUser() {
        System.out.println("Please choose an action : ");
        System.out.println("1. Add transaction");
        System.out.println("2. Add storage contract");
        System.out.println("3. Get user balance");
        System.out.println("4. Mine current block");
        System.out.println("5. Print chain");
        System.out.println("6. Check chain validity");
        System.out.println("0. Exit\n\n");

        int readNum = scanner.nextInt();

        switch (readNum) {
            case (0):
                // Return indication that the program should not continue
                return false;
            case (1):
                transactionPrompt();
                break;
            case (2):
                contractPrompt();
                break;
            case (3):
                balancePrompt();
                break;
            case (4):
                System.out.println("Please enter id of miner : ");
                String minerId = scanner.next();
                chainClient.mineCurrentBlock(minerId);
                break;
            case (5):
                chainClient.printBlockChain();
                break;
            case (6):
                if(chainClient.isChainValid()){
                    System.out.println("Chain is valid");
                }else{
                    System.out.println("Chain is invalid");
                }
                break;
            default:
                System.out.println("Invalid input. Please Try again");
                break;
        }

        // Return indication that the program should continue
        return true;
    }


    public void transactionPrompt() {
        System.out.println("\n Please enter ID of paying user :");
        String payerID = scanner.next();

        System.out.println("\n Please enter ID the recipient :");
        String recipientID = scanner.next();

        System.out.println("\n Please enter amount of tokens to transfer :");
        double transferAmount = scanner.nextDouble();

        try {
            TokenTransaction transaction = new TokenTransaction(payerID, recipientID, transferAmount);
            chainClient.addDataPoint(transaction);
            System.out.println("Transaction added : ");
            System.out.println(transaction.getFormattedDataString());
        } catch (InsufficientFundsException e) {
            printInsufficientFunds(payerID);
        }
    }

    public void contractPrompt() {
        System.out.println("\n Please enter ID of original file owner :");
        String fileOwnerID = scanner.next();

        System.out.println("\n Please enter ID of the storage unit :");
        String storageUnitID = scanner.next();

        System.out.println("\n Please enter ID of the file :");
        String fileID = scanner.next();

        System.out.println("\n Please enter payment in tokens :");
        double transferAmount = scanner.nextDouble();

        try {
            StorageContract contract = new StorageContract(fileID, fileOwnerID, storageUnitID,
                    System.currentTimeMillis() + 15000, transferAmount);
            chainClient.addDataPoint(contract);
            System.out.println("Added storage contract : ");
            System.out.println(contract.getFormattedDataString());
        } catch (InsufficientFundsException e) {
            printInsufficientFunds(fileOwnerID);

        }
    }

    public void balancePrompt() {
        System.out.println("\n Please enter user ID :");
        String userID = scanner.next();
        System.out.println("Current balance : " + chainClient.getUserBalance(userID) + "\n");
    }

    private boolean continuePrompt(){
        System.out.println("\nDo you wish to continue? (y/n) : ");
        String input = scanner.next();

        if(input.toLowerCase().contains("y") || input.toLowerCase().contains("yes"))
            return true;
        else if(input.toLowerCase().contains("n") || input.toLowerCase().contains("no"))
            return false;
        else
            return continuePrompt();

    }

    private static void printDottedLine(){
        System.out.println("----------------------------------------------");
    }

    private static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printInsufficientFunds(String userID){
        clearConsole();
        printDottedLine();
        System.out.println("Error");
        System.out.println("User \"" + userID + "\" does not have sufficient funds to complete the transaction.");
        System.out.println("Current balance : " + chainClient.getUserBalance(userID));
        printDottedLine();
        System.out.println("\n");
    }



}
