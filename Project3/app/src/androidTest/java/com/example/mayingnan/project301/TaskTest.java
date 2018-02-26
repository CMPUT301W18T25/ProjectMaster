package com.example.mayingnan.project301;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by julianstys on 2018-02-25.
 */

public class TaskTest {

    @Test
    public void testTask(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task task = new Task();
        task.setTaskName("Fetch car");
        task.setTaskDetails("Fetch my car from lister hall");
        task.setTaskAddress("HUB mall");
        task.setTaskRequester("Julian");
        task.setTaskProvider(null);
        assertEquals("Julian", task.getTaskRequester());
        task.setTaskRequester("Chris");
        assertEquals("Chris", task.getTaskRequester());


    }
}
