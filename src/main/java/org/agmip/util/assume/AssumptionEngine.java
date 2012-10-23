package org.agmip.util.assume;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssumptionEngine {
    private static final Logger LOG = LoggerFactory.getLogger(AssumptionEngine.class);
    private final ArrayList<HashMap<String, String>> rules;
    
    public AssumptionEngine(ArrayList<HashMap<String, String>> rules) {
        this.rules = rules;
    }
    
    public void apply(HashMap<String, Object> source) {
        
        for (HashMap<String, String> rule : rules) {
            String args[]  = rule.get("args").split("[|]");
            if (rule.get("cmd").equals("ASSUME")) {
                Assume.run(source, rule.get("variable"), args);
            } else if (rule.get("cmd").equals("CALC")) {
                Calculate.run(source, rule.get("variable"), args);
            } else {
                LOG.error("Rule type ["+rule.get("cmd")+"] not recognized");
            }
        }
    }
}
