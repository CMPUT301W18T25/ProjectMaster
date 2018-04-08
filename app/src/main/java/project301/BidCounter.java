package project301;

/**
 * Detail: BidCounter is used to record how many bids the requester have ever got.
 * @classname : BidCounter
 * @Date :   18/03/2018
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class BidCounter {
    private String requesterId;
    private int counter;
    private int previousCounter;
    private String ESid;

    /**
     * Construct of bidCounter
     * @param requesterId requesterId
     * @param counter counter
     */
    public BidCounter(String requesterId,int counter){
        this.requesterId = requesterId;
        this.counter = counter;
        this.ESid = "null";
        this.previousCounter = counter;

    }

    /**
     * Default construct of bidCounter
     */
    public BidCounter(){
        this.requesterId = null;
        this.counter = -1;
        this.ESid = "null";
        this.previousCounter = -1;
    }

    /**
     * Set the value of counter
     * @param counter counter
     */
    public void setCounter(int counter){
        this.counter = counter;
    }

    /**
     * Return bidCounter Id
     * @param id id in ES database
     */
    public void setESid(String id){
        this.ESid = id;
    }

    /**
     * Get requesterId
     * @return requesterId
     */
    public String getRequesterId(){
        return this.requesterId;
    }

    /**
     * Get the value of counter
     * @return counter
     */
    public int getCounter(){
        return this.counter;
    }

    /**
     * Get the bidCounter Id
     * @return ES id
     */
    public String getESid(){
        return this.ESid;
    }

    /**
     * Set previous counter
     * @param previousCounter previousCounter
     */
    public void setPreviousCounter(int previousCounter){this.previousCounter = previousCounter;}

    /**
     * Get previous counter
     * @return previous counter
     */
    public int getPreviousCounter(){return this.previousCounter;}

}
