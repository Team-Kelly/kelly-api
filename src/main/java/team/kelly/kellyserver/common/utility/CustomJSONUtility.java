package team.kelly.kellyserver.common.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomJSONUtility {

    public static JSONArray sortJsonArray(JSONArray array) {
        List<JSONObject> jsons = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            jsons.add(array.getJSONObject(i));
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lid = lhs.get("fcstDate").toString();
                String rid = rhs.get("fcstDate").toString();
                return lid.compareTo(rid);
            }
        });
        return new JSONArray(jsons);
    }

    public static JSONArray getCustomJSONArray(String tag, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        if (jsonObject.get(tag) instanceof JSONArray) {
            jsonArray = jsonObject.getJSONArray(tag);
        } else {
            jsonArray.put(jsonObject.getJSONObject(tag));
        }
        return jsonArray;
    }

}
