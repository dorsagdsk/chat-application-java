package Model;

import Model.Person;

import java.io.*;

public class BlockUserFile {

    public int getFileSize(){
        int size =0;
        File file = new File("blockUsers.txt");
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

    public void blockFriend(Person user , Person friend){  // nvashtan id hai blocki
        File file= new File("blockUsers.txt");
        try (BufferedWriter writer= new BufferedWriter(new FileWriter(file,true))){

            writer.newLine();

            writer.write(user.getId()+"-"+friend.getId());

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isBlocked(Person user , Person friend){ // chat // block  this function show that if its block or not
        int size= getFileSize();
        File file = new File("blockUsers.txt");
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                String line = reader.readLine();
                String[] lineContent = line.split("-");
                if(user.getId()==Integer.parseInt(lineContent[0]) && friend.getId()==Integer.parseInt(lineContent[1])){
                    return true;             //1-2-------> in this condion show that if 1 block 2
                }
                else if(user.getId()==Integer.parseInt(lineContent[1]) && friend.getId()==Integer.parseInt
                        (lineContent[0])){   //2-1 ------> if this condition show if 2 block 1
                    return true;
                }
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
