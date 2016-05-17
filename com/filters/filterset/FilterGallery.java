package com.filters.filterset;

import com.filters.filterset.Matrix.AbstractFilterMatrix;
import com.filters.filterset.Matrix.FilterMatrix;
import com.filters.filterset.matrixfilter.AbstractFilter;
import com.filters.filterset.matrixfilter.MatrixFilter;
import com.sun.javafx.binding.ObjectConstant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FilterGallery implements Iterable<String>{

    private List<String> filterNames = new LinkedList<>();
    private JSONArray filters;

    public FilterGallery(String json) {
        try {
            filters = new JSONObject(json).getJSONArray("filters");
            for (Object name : filters) {
                filterNames.add(((JSONObject) name).getString("name"));
            }
        } catch (JSONException e) {e.printStackTrace();};
    }

    private AbstractFilterMatrix parseMatrix(JSONArray matrix, boolean normalize) {
        float[][] parsed;
        parsed = new float[matrix.length()][];
        for (int i = 0; i < parsed.length; i++) {
            JSONArray row = (JSONArray) matrix.get(i);
            parsed[i] = new float[row.length()];
            for (int j = 0; j < parsed[i].length; j++)
                parsed[i][j] = ((Number) row.get(j)).floatValue();
        }
        return normalize? new FilterMatrix(parsed).normalize() : new FilterMatrix(parsed);
    }

    private AbstractFilter parseFilter(JSONObject filter) {
        JSONArray jsonMatrices = filter.getJSONArray("matrices");
        AbstractFilterMatrix[] matrices = new AbstractFilterMatrix[jsonMatrices.length()];
        boolean normalize = false;
        try {
            normalize = filter.getBoolean("normalize");
        } catch (JSONException e) {}
        int i = 0;
        for (Object matrix : jsonMatrices)
            matrices[i++] = parseMatrix((JSONArray) matrix, normalize);

        return new MatrixFilter(matrices);
    }

    public AbstractFilter get(String name) {
        if (filters == null) return null;
        JSONObject filterJSON = (JSONObject) filters.get(filterNames.indexOf(name));
        return filterJSON == null? null : parseFilter(filterJSON);
    }

    @Override
    public Iterator<String> iterator() {
        return filterNames.iterator();
    }
}
