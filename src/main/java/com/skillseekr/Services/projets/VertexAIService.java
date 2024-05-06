package com.skillseekr.Services.projets;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VertexAIService {
    public static Map<String, String> getSuggestions(String title , String skills) throws IOException, InterruptedException {
        String projectId = "javaapp-422216";
        String location = "us-central1";
        String modelName = "gemini-1.0-pro-vision";

        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
            GenerateContentResponse response;

            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
            // Create a chat session to be used for interactive conversation.
            ChatSession chatSession = new ChatSession(model);
            String prompt = "Can you give me a list of propositions " +
                    "for a project that is entitled " + title +
                    " and requires skills in " + skills + " " +
                    " , the result is a json and should be started with { " +
                    " and contains propositions , " +
                    " a proposition is composed by a title and a description " +
                    "the titles should be a short description of the proposition " +
                    "dont write anything before the { and after the }";
            response = chatSession.sendMessage(prompt);
            System.out.println(prompt);
            System.out.println(ResponseHandler.getText(response));
            Map<String, String> map = parseJsonToMap(ResponseHandler.getText(response));

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, String> parseJsonToMap(String jsonString) throws Exception {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray propositionsArray = jsonObject.getJSONArray("propositions");
        for (int i = 0; i < propositionsArray.length(); i++) {
            JSONObject proposition = propositionsArray.getJSONObject(i);
            String name = proposition.getString("title");
            String description = proposition.getString("description");
            map.put(name, description);
        }
        return map;
    }
}
