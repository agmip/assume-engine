package org.agmip.util.assume;

import java.util.ArrayList;
import java.util.HashMap;

import org.agmip.ace.util.AcePathfinderUtil;

import org.junit.Before;
import org.junit.Test;
//import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author frostbytten
 */
public class AssumptionEngineTest {
    public static final Logger LOG = LoggerFactory.getLogger(AssumptionEngineTest.class);
    private HashMap<String,Object> test;
    private HashMap<String,Object> live;
    private ArrayList<HashMap<String,String>> assume;
    
      
    @Before
    public void setup() {
        test = new HashMap<String,Object>();
        live = new HashMap<String,Object>();   
        assume = new ArrayList<HashMap<String,String>>();
    }
    
    @Test
    public void checkICH2O(){
        HashMap<String,String> rule1 = new HashMap<String,String>();
        HashMap<String,String> rule2 = new HashMap<String,String>();
        
        rule1.put("cmd", "ASSUME");
        rule1.put("variable", "ICDAT");
        rule1.put("args", "$PDATE");
        
        rule2.put("cmd", "CALC");
        rule2.put("variable", "ICH2O");
        rule2.put("args", "calculateich2o|0.50");

        assume.add(rule1);
        assume.add(rule2);
        
        AcePathfinderUtil.insertValue(test, "pdate", "19800101");
        AcePathfinderUtil.insertValue(test, "sllb", "30");
        AcePathfinderUtil.insertValue(test, "slll", "0.22");
        AcePathfinderUtil.insertValue(test, "sldul", "0.32");
        AcePathfinderUtil.insertValue(test, "sloc", "0.9");
        AcePathfinderUtil.insertValue(test, "sllb", "50");
        AcePathfinderUtil.insertValue(test, "slll", "0.15");
        AcePathfinderUtil.insertValue(test, "sldul", "0.24");
        AcePathfinderUtil.insertValue(test, "sloc", "0.01");


        LOG.info("Pre-assumption: "+test.toString());
        AssumptionEngine engine = new AssumptionEngine(assume);
        engine.apply(test);
        LOG.info("Post-assumption: "+test.toString());
    }
    
    @Test
    public void checkNO3(){
        HashMap<String,String> rule1 = new HashMap<String,String>();
        HashMap<String,String> rule2 = new HashMap<String,String>();
        
        rule1.put("cmd", "ASSUME");
        rule1.put("variable", "ICDAT");
        rule1.put("args", "$PDATE");
        
        rule2.put("cmd", "CALC");
        rule2.put("variable", "ICNO3");
        rule2.put("args", "calculateicno3|30|0.9|50|0.7|0.5");

        assume.add(rule1);
        assume.add(rule2);
        
        AcePathfinderUtil.insertValue(test, "pdate", "19800101");
        AcePathfinderUtil.insertValue(test, "sllb", "30");
        AcePathfinderUtil.insertValue(test, "slll", "0.22");
        AcePathfinderUtil.insertValue(test, "sldul", "0.32");
        AcePathfinderUtil.insertValue(test, "sloc", "0.9");
        AcePathfinderUtil.insertValue(test, "sllb", "50");
        AcePathfinderUtil.insertValue(test, "slll", "0.15");
        AcePathfinderUtil.insertValue(test, "sldul", "0.24");
        AcePathfinderUtil.insertValue(test, "sloc", "0.01");


        LOG.info("Pre-assumption: "+test.toString());
        AssumptionEngine engine = new AssumptionEngine(assume);
        engine.apply(test);
        LOG.info("Post-assumption: "+test.toString());
    }
}
