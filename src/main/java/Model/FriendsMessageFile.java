package Model;

import Model.Message;
import Model.Person;
import Model.UserFile;

import java.io.*;
import java.util.ArrayList;

public class FriendsMessageFile {

    private UserFile userFile;

    public void addMessage(String message , String sender , String receiver , String dateTime){
        File file = new File("messageFriends.txt");
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(file,true))){

            writer.newLine();
            writer.write(sender+"-"+receiver+"-"+message+"-"+dateTime);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void clearHistory(Person user , Person friend){ // az do taraf pak mikone
        ArrayList<Message> allMessage= new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Message> allMessageAfterClearHistory = new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] message = {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername() ,
            allMessage.get(i).getMessage(),allMessage.get(i).getDateTime()};

            if( ! ( ( message[0].equals(user.getUsername()) && message[1].equals(
                    friend.getUsername())  ) || ( message[0].equals(friend.getUsername()) && message[1].equals(
                    user.getUsername()))) ){
                allMessageAfterClearHistory.add(new Message(message[0],message[1],message[2],message[3]));
            }
        }

        writeInFileAfterEditing(allMessageAfterClearHistory);
    }

    public void deleteMessage(Message selectedMessage){
        ArrayList<Message> allMessage= new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Message> allMessageAfterDeleting= new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] message = {allMessage.get(i).getSenderUsername() , allMessage.get(i).getReceiverUsername() ,
                    allMessage.get(i).getMessage(),allMessage.get(i).getDateTime()};
            if( ! (message[0].equals(selectedMessage.getSenderUsername()) && message[1].equals(
                    selectedMessage.getReceiverUsername()) && message[2].equals(selectedMessage.getMessage())
            && message[3].equals(selectedMessage.getDateTime()))){
                allMessageAfterDeleting.add(new Message(message[0],message[1],message[2],message[3]));
            }
        }

        writeInFileAfterEditing(allMessageAfterDeleting);
    }

    public int getFileSize(){
        int size=0;
        File file = new File("messageFriends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            while (reader.readLine() != null){
                size++;
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return size;
    }

    public ArrayList<Message> getAllMessages(){
        int size = getFileSize();
        ArrayList<Message> messages= new ArrayList<>();
        userFile= new UserFile();
        File file = new File("messageFriends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                String line = reader.readLine();
                String[] lineContent = line.split("-");
                messages.add(new Message( lineContent[0],lineContent[1] ,lineContent[2],lineContent[3]));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public ArrayList<Message> getUserMessages(int senderId , int receiverId){
        ArrayList<Message> allMessage =new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Message> userMessages= new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] message= {allMessage.get(i).getSenderUsername(), allMessage.get(i).getReceiverUsername(),
                allMessage.get(i).getMessage(), allMessage.get(i).getDateTime()};
            if(userFile.getUserId(message[0]) == senderId && userFile.getUserId(message[1]) == receiverId){
                userMessages.add(new Message(message[0],message[1],message[2] ,message[3]));
            }
            else if( userFile.getUserId(message[0]) == receiverId && userFile.getUserId(message[1]) == senderId){
                userMessages.add(new Message(message[0],message[1],message[2],message[3]));
            }
        }
        return userMessages;
    }

    public void editMessage(Message selectedMessage, Message editedMessage){
        ArrayList<Message> allMessage= new ArrayList<>();
        allMessage.addAll(getAllMessages());
        ArrayList<Message> allMessageAfterEditing= new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            String[] messages= {allMessage.get(i).getSenderUsername(), allMessage.get(i).getReceiverUsername(),
                    allMessage.get(i).getMessage(), allMessage.get(i).getDateTime()};

            if(messages[0].equals(selectedMessage.getSenderUsername()) && messages[1].equals(selectedMessage.getReceiverUsername())
            && messages[2].equals(selectedMessage.getMessage())){

                allMessageAfterEditing.add(new Message(editedMessage.getSenderUsername()
                        ,editedMessage.getReceiverUsername() , editedMessage.getMessage() ,editedMessage.getDateTime()));

            }
            else {
                allMessageAfterEditing.add(new Message(messages[0],messages[1],messages[2] , messages[3]));

            }
        }
        writeInFileAfterEditing(allMessageAfterEditing);

    }

    public void writeInFileAfterEditing(ArrayList<Message> editedMessages){
        File file= new File("messageFriends.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

            for(int i=0 ; i < editedMessages.size() ; i++){
                writer.newLine();
                writer.write(editedMessages.get(i).getSenderUsername()+"-"+
                        editedMessages.get(i).getReceiverUsername()+"-"+editedMessages.get(i).getMessage()+"-"
                +editedMessages.get(i).getDateTime());
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
