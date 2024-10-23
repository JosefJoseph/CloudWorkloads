package org.mycompany.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Calculator {
    private HashMap<String, Long> executionTime = new HashMap<String, Long>();

    public void processCalculation(JSONObject json) {
        HashMap<String, Long> workLoadList = new HashMap<String, Long>();

        if (json != null) {
            if (json.has("events")) {
                // Extrahiere das JSONArray der Events
                JSONArray events = json.getJSONArray("events");

                // Erstelle eine Liste, um die JSONObject-Elemente zu sortieren
                List<JSONObject> eventList = new ArrayList<>();
                for (int i = 0; i < events.length(); i++) {
                    eventList.add(events.getJSONObject(i));
                };

                // Sortiere die Liste nach dem "timestamp"-Feld
                eventList.sort(Comparator.comparingLong(event -> event.getLong("timestamp")));

                // Verarbeitung der sortierten Events
                for (JSONObject event : eventList) {
                    String customerId = event.getString("customerId");
                    String workloadId = event.getString("workloadId");
                    String eventType = event.getString("eventType");
                    Long timestamp = event.getLong("timestamp");

                    if (!workLoadList.containsKey(workloadId) && eventType.equals("start")) {
                        workLoadList.put(workloadId, timestamp);
                    }

                    if (workLoadList.containsKey(workloadId) && eventType.equals("stop")) {
                        Long oldTimeStamp = workLoadList.get(workloadId);
                        Long difference = timestamp - oldTimeStamp;
                        
                        if(executionTime.containsKey(customerId)) {
                            Long oldDifference = executionTime.get(customerId);
                            difference = oldDifference + difference;
                        }
                        executionTime.put(customerId, difference);
                    }
                }
            }
        } else {
            System.out.println("JSON object is null.");
        }
    }

    public HashMap<String, Long> getExecutionTime() {
        return executionTime;
    }
}
