package org.agmip.util.assume;

import java.util.ArrayList;
import java.util.HashMap;

import org.agmip.ace.AcePathfinder;
import org.agmip.ace.util.AcePathfinderUtil;
//import org.agmip.core.util.MapUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Command {

    public static final Logger LOG = LoggerFactory.getLogger(Command.class);

    public static String getRawValue(HashMap m, String var) {        
        if (var == null) {
            return "";
        }
        String path = AcePathfinder.INSTANCE.getPath(var);
        if (path == null) {
            return "";
        }
        if (path.equals("")) {
            if (m.containsKey(var)) {
                return (String) m.get(var);
            } else {
                return "";
            }
        }
        HashMap<String, Object> pointer = AcePathfinderUtil.traverseToPoint(m, path);
        if (pointer == null) {
            pointer = m;
        }
        
        if (path.contains("@")) {
            String[] temp = path.split("[!@]");
            boolean isEvent = false;
            if(path.contains("!")) {
                isEvent = true;
            }
            pointer = (HashMap<String,Object>) pointer.get(temp[0]);
            if (!pointer.containsKey(temp[1])) {
                return "";
            } else {
                // Loop through everything until you find it.
                ArrayList<HashMap<String, Object>> base = (ArrayList<HashMap<String, Object>>) pointer.get(temp[1]);
                for (HashMap<String, Object> item : base) {
                    if (isEvent && (var.equals("pdate") || var.equals("idate") || var.equals("fedate") | var.equals("omdat") || var.equals("mladat") || var.equals("mlrdat") || var.equals("cdate") || var.equals("tdate") || var.equals("hadat"))) {
                            var = "date";
                    }
                    if (item.containsKey(var)) {
                        return (String) item.get(var);
                    }
                }
                return "";
            }
        } else {
            if (pointer.containsKey(var)) {
                return (String) pointer.get(var);
            } else {
                return "";
            }
        }
    }
    
    public static ArrayList<HashMap<String, Object>> traverseAndGetSiblings(HashMap m, String var) {
        ArrayList<HashMap<String, Object>> def = new ArrayList<HashMap<String,Object>>();
        
        if(var == null) {
            return def;
        }
         String path = AcePathfinder.INSTANCE.getPath(var);
        if (path == null) {
            return def;
        }
        if (! path.contains("@")) {
            return def;
        }
        
        HashMap<String, Object> pointer = AcePathfinderUtil.traverseToPoint(m, path);
        if (pointer == null) {
            pointer = m;
        }
        
        String[] temp = path.split("[@!]");
        if (! pointer.containsKey(temp[0])) {
            return def;
        } else {
            pointer = (HashMap<String, Object>) pointer.get(temp[0]);
            if(! pointer.containsKey(temp[1])) {
                return def;
            } else {
                return (ArrayList<HashMap<String, Object>>) pointer.get(temp[1]);
            }
        }
    }
}
