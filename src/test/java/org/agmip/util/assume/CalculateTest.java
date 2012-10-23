package org.agmip.util.assume;

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
public class CalculateTest {
    public static final Logger LOG = LoggerFactory.getLogger(CalculateTest.class);
    private HashMap<String,Object> test;
    private HashMap<String,Object> live;
    
      
    @Before
    public void setup() {
        test = new HashMap<String,Object>();
        live = new HashMap<String,Object>();   
    }
    
    @Test
    public void checkICH2O(){
        AcePathfinderUtil.insertValue(test, "sllb", "30");
        AcePathfinderUtil.insertValue(test, "slll", "0.22");
        AcePathfinderUtil.insertValue(test, "sldul", "0.32");
        AcePathfinderUtil.insertValue(test, "sloc", "0.9");
        AcePathfinderUtil.insertValue(test, "sllb", "50");
        AcePathfinderUtil.insertValue(test, "slll", "0.15");
        AcePathfinderUtil.insertValue(test, "sldul", "0.24");
        AcePathfinderUtil.insertValue(test, "sloc", "0.01");
        
        Calculate.calculateICH2O(test, "0.45");
        LOG.info("Post process - "+test.toString());
        
    }
    
    @Test
    public void checkICH2OInline() {
        AcePathfinderUtil.insertValue(test, "sllb", "30");
        AcePathfinderUtil.insertValue(test, "slll", "0.22");
        AcePathfinderUtil.insertValue(test, "sldul", "0.32");
        AcePathfinderUtil.insertValue(test, "sloc", "0.9");
        AcePathfinderUtil.insertValue(test, "sllb", "50");
        AcePathfinderUtil.insertValue(test, "slll", "0.15");
        AcePathfinderUtil.insertValue(test, "sldul", "0.24");
        AcePathfinderUtil.insertValue(test, "sloc", "0.01");
        AcePathfinderUtil.insertValue(test, "icbl", "30");
        AcePathfinderUtil.insertValue(test, "icbl", "50");
        
        Calculate.calculateICH2O(test, "0.45");
        LOG.info("Post process - "+test.toString());
    }
             
}
