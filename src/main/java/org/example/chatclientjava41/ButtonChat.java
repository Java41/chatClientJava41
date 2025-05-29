package org.example.chatclientjava41;

import javafx.scene.Node;
import javafx.scene.control.Button;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ButtonChat extends Button {
    private Map<Integer,Map<Date,String>> content=new HashMap<>();

    public ButtonChat(String s, Node node) {
        super(s, node);
    }
}
