package project301;

/**
 * Created by a123456 on 2018-03-22.
 */

public class BidCounter {
    private String requesterId;
    private int counter;
    private String ESid;
    public BidCounter(String requesterId,int counter){
        this.requesterId = requesterId;
        this.counter = counter;
        this.ESid = "null";
    }
    public BidCounter(){
        this.requesterId = null;
        this.counter = -1;
        this.ESid = "null";
    }
    public void setRequesterId(String requesterId){
        this.requesterId = requesterId.toLowerCase();
    }
    public void setCounter(int counter){
        this.counter = counter;
    }
    public void setESid(String id){
        this.ESid = id;
    }
    public String getRequesterId(){
        return this.requesterId;
    }
    public int getCounter(){
        return this.counter;
    }
    public String getESid(){
        return this.ESid;
    }
}
