package org.agmip.util.assume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import org.agmip.util.MapUtil;
import org.agmip.ace.util.AcePathfinderUtil;

public class Calculate extends Command {

    public static final Logger LOG = LoggerFactory.getLogger(Calculate.class);

    public static void run(HashMap m, String var, String[] args) {
        var = var.toLowerCase();
        String rawValue = Command.getRawValue(m, var);
        LOG.debug("FOUND: [" + var + "] " + rawValue);
        if (rawValue.equals("")) {
            // Load the assumptions (INCOMPLETE IMPLEMENTATION)
            String fun = args[0];
            String[] newArgs =  Arrays.copyOfRange(args, 1, args.length);
            LOG.debug("Calculate fun(): "+fun);
            if (fun.toLowerCase().equals("calculateich2o")) {
                calculateICH2O(m, newArgs[0]);
            } else if (fun.toLowerCase().equals("calculateLayerThreshold")) {
                calculateLayerThreshold(m, var, newArgs);
            } else {
                LOG.error("Calculation "+args[0]+" not yet implemented");
            }
        }
    }

    //  Hardwired functions
    /**
     * Calculate the initial conditions soil water per soil layer based on
     * soil information and a soil water ratio.
     * 
     * @param m      Complete dataset
     * @param icswp  Soil water ratio
     */
    public static void calculateICH2O(HashMap m, String icswp) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        // Get the soils
        ArrayList<HashMap<String, Object>> soils = Command.traverseAndGetSiblings(m, "sllb");

        if (soils.isEmpty()) {
            LOG.error("Missing required soil information to calculate ICH2O");
            return;
        }
        for (HashMap<String, Object> sl : soils) {
            if (!sl.containsKey("slll") || !sl.containsKey("sldul") || !sl.containsKey("sllb")) {
                LOG.error("Missing SLLL, SLDUL, or SLLB. Cannot calculate ICH2O");
                return;
            }

            double slll = Double.parseDouble((String) MapUtil.getObjectOr(sl, "slll", "0.01"));
            double sldul = Double.parseDouble((String) MapUtil.getObjectOr(sl, "sldul", "0.0"));
            double icswpd = Double.parseDouble((String) icswp);
            HashMap<String, String> results = new HashMap<String, String>();

            String icsw = Double.toString((((sldul - slll) * icswpd) + slll));
            results.put("icbl", (String) MapUtil.getObjectOr(sl, "sllb", "0"));
            results.put("ich2o", icsw);

            data.add(results);
        }

        ArrayList<HashMap<String, Object>> pointer = Command.traverseAndGetSiblings(m, "icbl");
        apply(m, pointer, data, "icbl");
    }
       
    public static void calculateLayerThreshold(HashMap m, String var, String[] args) {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        ArrayList<Integer>  index = new ArrayList<Integer>();
        ArrayList<String>   temp  = new ArrayList<String>();
        
        // Get the soils
        ArrayList<HashMap<String, Object>> soils = Command.traverseAndGetSiblings(m, "sllb");
        // Setup layering information
        double rest = -99.9;
        int l = args.length;
        if (args.length%2 == 1) {
            // Take the last value as a remainder value
            rest = Double.parseDouble(args[args.length-1]);
            l--;
        }
        
        // Deal with thresholds
        for( int i=0; i < l; i++) {
            index.add(Integer.parseInt(args[i]));
            i++;
            temp.add(args[i]);
        }
        if (soils.isEmpty()) {
            LOG.error("Missing required soil information to calculate layer thresholds for "+var);
            return;
        }
        
        for (HashMap<String,Object> sl : soils) {
            if (! sl.containsKey("sllb")) {
                LOG.error("Missing required SLLB to calculate layer thresholds for "+var);
                return;
            }
            HashMap<String, String> results = new HashMap<String, String>();
            int sllb = Integer.parseInt((String) MapUtil.getObjectOr(sl, "sllb", "0"));
            String sticky = "";
            for (int i=0; i < index.size(); i++) {
                if (sllb <= index.get(i)) {
                    sticky = temp.get(i);
                    break;
                }
            }
            if (sticky.equals("")) {
                if (rest != -99.9){
                    sticky = Double.toString(rest);
                } else {
                    sticky = temp.get(temp.size()-1);
                }
            }
            results.put("icbl", Integer.toString(sllb));
            results.put(var, sticky);
            
            data.add(results);
        }
        ArrayList<HashMap<String, Object>> pointer = Command.traverseAndGetSiblings(m, "icbl");
        apply(m, pointer, data, "icbl");
    }

    public static void apply(HashMap m, ArrayList<HashMap<String, Object>> dest, ArrayList<HashMap<String, String>> data, String indexKey) {
        boolean applyInline = (!dest.isEmpty());
        //int l = 0;
        int l = data.size();

        for (int i = 0; i < l; i++) {
            if (applyInline) {
                data.get(i).remove(indexKey);
                HashMap<String, Object> inlineDest = dest.get(i);
                Iterator it = data.get(i).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> item = (Map.Entry<String, String>) it.next();
                    inlineDest.put(item.getKey(), item.getValue());
                }
            } else {
                Iterator it = data.get(i).entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> item = (Map.Entry<String, String>) it.next();
                    AcePathfinderUtil.insertValue(m, item.getKey(), item.getValue());
                }
            }
        }
    }
}
